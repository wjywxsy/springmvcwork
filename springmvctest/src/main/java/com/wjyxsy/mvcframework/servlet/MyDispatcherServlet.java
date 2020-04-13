package com.wjyxsy.mvcframework.servlet;

import com.wjyxsy.mvcframework.annotations.*;
import com.wjyxsy.mvcframework.pojo.Handler;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.Encoder;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author 继洋
 */
public class MyDispatcherServlet extends HttpServlet {

    private Properties properties = new Properties();

    private List<String> classNameList = new ArrayList<>();

    private Map<String, Object> iocMap = new HashMap<>();

    private List<Handler> handlerMappingList = new ArrayList<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        // 加载配置文件
        String contextConfigLocation = config.getInitParameter("contextConfigLocation");
        doLoadPath(contextConfigLocation);

        // 扫描获取配置中的全限定类名 存储在 classNameList 中
        doScanClassName(properties.getProperty("scan.package"));

        // 循环 classNameList 获取ioc 装配@MyController @MyService
        doIoC();

        // @MyAutowired注入
        doAutowired();

        // @MyRequestMapping url 和 handler 绑定映射
        doHandlerMapping();

        // @MySecurity 权限配置
        doSecurityInit();

        // doPost 处理
    }

    private void doSecurityInit() {
        if (handlerMappingList.isEmpty()) {return;}
        for (Handler handler : handlerMappingList) {
            Class<?> aClass = handler.getObject().getClass();
            if (aClass.isAnnotationPresent(MySecurity.class)) {
                MySecurity annotation = aClass.getAnnotation(MySecurity.class);
                String[] value = annotation.value();
                List<String> securityList = Arrays.asList(value);
                handler.setSecurityList(securityList);
                continue;
            }
            Method method = handler.getMethod();
            if (!method.isAnnotationPresent(MySecurity.class)) { continue; }
            String[] methodSecurity = method.getAnnotation(MySecurity.class).value();
            List<String> securityList = Arrays.asList(methodSecurity);
            handler.setSecurityList(securityList);
        }
    }

    private void doHandlerMapping() {
        if (iocMap.isEmpty()) {return;}
        for (Map.Entry<String, Object> entry: iocMap.entrySet()) {
            Class<?> aClass = entry.getValue().getClass();
            if (!aClass.isAnnotationPresent(MyRequestMapping.class)) {continue;}

            String parentUrl = aClass.getAnnotation(MyRequestMapping.class).value();

            Method[] methods = aClass.getMethods();
            for (Method method : methods) {
                if (!method.isAnnotationPresent(MyRequestMapping.class)) {continue;}
                String url = method.getAnnotation(MyRequestMapping.class).value();
                Handler handler = new Handler(Pattern.compile(parentUrl+url), method, entry.getValue());

                Parameter[] parameters = method.getParameters();
                for (int i = 0; i < parameters.length; i++) {
                    Parameter parameter = parameters[i];
                    if (parameter.getType() == HttpServletRequest.class || parameter.getType() == HttpServletResponse.class) {
                        handler.getParameterMap().put(parameter.getType().getSimpleName(), i);
                    } else {
                        handler.getParameterMap().put(parameter.getName(), i);
                    }
                }
                handlerMappingList.add(handler);
            }
        }
    }

    private void doAutowired() {
        if (iocMap.isEmpty()) {return;}

        for (Map.Entry<String, Object> entry: iocMap.entrySet()) {
            Object value = entry.getValue();
            Field[] declaredFields = value.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                if (!declaredField.isAnnotationPresent(MyAutowired.class)) {continue;}

                String autowiredName = declaredField.getAnnotation(MyAutowired.class).value();
                if (StringUtils.isEmpty(autowiredName)) {
                    autowiredName = declaredField.getType().getName();
                }
                declaredField.setAccessible(true);
                try {
                    declaredField.set(value, iocMap.get(autowiredName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void doIoC() {
        if (classNameList.size() == 0) {return;}

        try {
            for (String className : classNameList) {
                Class<?> aClass = Class.forName(className);
                if (aClass.isAnnotationPresent(MyController.class)) {

                    iocMap.put(lowerPre(aClass.getSimpleName()), aClass.newInstance());

                } else if (aClass.isAnnotationPresent(MyService.class)) {
                    String serviceName = aClass.getAnnotation(MyService.class).value();
                    if (!"".equals(serviceName)) {

                        iocMap.put(serviceName, aClass.newInstance());
                    } else {

                        iocMap.put(aClass.getSimpleName(), aClass.newInstance());
                    }

                    Class<?>[] interfaces = aClass.getInterfaces();
                    for (Class<?> anInterface : interfaces) {
                        iocMap.put(anInterface.getName(), aClass.newInstance());
                    }
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    private void doScanClassName(String packageName) {
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath() + packageName.replaceAll("\\.", "/");
        File pathFile = new File(path);
        File[] files = pathFile.listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                doScanClassName(packageName + "." + file.getName());
            }
            if (file.getName().endsWith(".class")) {
                classNameList.add(packageName + "." +file.getName().replace(".class",""));
            }
        }
    }

    private void doLoadPath(String contextConfigLocation) {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String lowerPre(String str) {
        char[] chars = str.toCharArray();
        if ('A' <= chars[0] && 'Z' >= chars[0]) {
            chars[0] += 32;
        }
        return String.valueOf(chars);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //
        String requestURI = req.getRequestURI();
        for (Handler handler : handlerMappingList) {
            if (!handler.getPattern().matcher(requestURI).matches()) {continue;}

            Object[] args = new Object[handler.getParameterMap().size()];

            Map<String, String[]> parameterMap = req.getParameterMap();
            String username = null;
            for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                String value = StringUtils.join(entry.getValue(), ",");
                if (entry.getKey().equals("username")) {
                    username = value;
                }
                if (!handler.getParameterMap().containsKey(entry.getKey())){continue;}
                Integer index = handler.getParameterMap().get(entry.getKey());
                args[index] = value;
            }
            List<String> securityList = handler.getSecurityList();
            if (securityList != null && securityList.size() > 0) {
                if (username == null
                        || StringUtils.isEmpty(username)
                        || !handler.getSecurityList().contains(username)
                ) {
                    // 无权限访问
                    resp.getWriter().write("501 has no security");
                    return;
                }
            }

            Integer indexReq = handler.getParameterMap().get(HttpServletRequest.class.getSimpleName());
            args[indexReq] = req;

            Integer indexResp = handler.getParameterMap().get(HttpServletResponse.class.getSimpleName());
            args[indexResp] = resp;

            try {
                handler.getMethod().invoke(handler.getObject(),args);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }


}
