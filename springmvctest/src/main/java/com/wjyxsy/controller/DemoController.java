package com.wjyxsy.controller;

import com.wjyxsy.mvcframework.annotations.MyAutowired;
import com.wjyxsy.mvcframework.annotations.MyController;
import com.wjyxsy.mvcframework.annotations.MyRequestMapping;
import com.wjyxsy.mvcframework.annotations.MySecurity;
import com.wjyxsy.service.IDemoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 继洋
 */
@MyController
@MyRequestMapping("/demo")
public class DemoController {

    @MyAutowired
    private IDemoService demoService;

    @MySecurity({"zhangsan"})
    @MyRequestMapping("/query")
    public String query(HttpServletRequest request, HttpServletResponse response, String name) {
        this.demoService.getName(name);
        return name;
    }

    @MySecurity({"lisi"})
    @MyRequestMapping("/get")
    public String get(HttpServletRequest request, HttpServletResponse response, String name) {
        this.demoService.getName(name);
        return name;
    }

    @MySecurity({"wangwu"})
    @MyRequestMapping("/select")
    public String select(HttpServletRequest request, HttpServletResponse response, String name) {
        this.demoService.getName(name);
        return name;
    }
}
