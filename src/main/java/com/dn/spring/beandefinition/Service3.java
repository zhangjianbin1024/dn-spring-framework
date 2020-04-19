package com.dn.spring.beandefinition;

import org.springframework.beans.factory.InitializingBean;

public class Service3 implements InitializingBean {
    public void init() {
        System.out.println("调用init()方法");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("调用afterPropertiesSet()");
    }
}