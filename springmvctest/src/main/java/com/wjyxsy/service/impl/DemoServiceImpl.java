package com.wjyxsy.service.impl;

import com.wjyxsy.mvcframework.annotations.MyService;
import com.wjyxsy.service.IDemoService;

@MyService("demoService")
public class DemoServiceImpl implements IDemoService {
    @Override
    public String getName(String name) {
        System.out.println("getName" + name);
        return name;
    }
}
