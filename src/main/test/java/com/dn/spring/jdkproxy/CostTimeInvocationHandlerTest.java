package com.dn.spring.jdkproxy;

import com.dn.spring.jdkproxy.service.IService;
import com.dn.spring.jdkproxy.service.ServiceA;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class CostTimeInvocationHandlerTest {

    @Test
    public void costTimeProxy() {

        IService serviceA = ProxyUtils.createProxy(new ServiceA(), IService.class);
        System.out.println("判断指定的类是否是一个代理类:" + Proxy.isProxyClass(serviceA.getClass()) + ":" + serviceA.getClass());

        InvocationHandler invocationHandler = Proxy.getInvocationHandler(serviceA);
        System.out.println("获取代理对象的InvocationHandler对象:" + invocationHandler);

        //ProxyClassFile.createProxyClassFile(serviceA.getClass());

        serviceA.m1();
        serviceA.m2();
        serviceA.m3();

    }
}