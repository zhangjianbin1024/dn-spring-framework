package com.dn.spring.jdkproxy.service;

import javax.annotation.PreDestroy;

public class ServiceB implements IService {

    private ServiceA serviceA;

    public ServiceA getServiceA() {
        return serviceA;
    }

    public void setServiceA(ServiceA serviceA) {
        this.serviceA = serviceA;
    }

    public ServiceB(ServiceA serviceA) {
        this.serviceA = serviceA;
    }

    public ServiceB() {
    }

    @PreDestroy
    public void preDestroy() { //@1
        System.out.println("preDestroy()");
    }

    @Override
    public void m1() {
        System.out.println("我是ServiceB中的m1方法!");
    }

    @Override
    public void m2() {
        System.out.println("我是ServiceB中的m2方法!");
    }

    @Override
    public void m3() {
        System.out.println("我是ServiceB中的m3方法!");
    }
}