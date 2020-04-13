package com.wjyxsy.mvcframework.pojo;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author 继洋
 */
public class Handler {

    private Pattern pattern;

    private Method method;

    private Object object;

    private Map<String, Integer> parameterMap;

    private List<String> securityList;

    public Handler(Pattern pattern, Method method, Object object) {
        this.pattern = pattern;
        this.method = method;
        this.object = object;
        this.parameterMap = new HashMap<>();
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Map<String, Integer> getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(Map<String, Integer> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public List<String> getSecurityList() {
        return securityList;
    }

    public void setSecurityList(List<String> securityList) {
        this.securityList = securityList;
    }
}
