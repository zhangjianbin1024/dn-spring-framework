package com.dn.spring.beandefinition;

import org.springframework.beans.factory.DisposableBean;

import javax.annotation.PreDestroy;

/**
 * @author: zh
 * @date: 2020/4/18/018 22:47
 */
public class DestroyBean implements DisposableBean {

    public DestroyBean() {
        System.out.println("创建DestroyBean实例");
    }

    @PreDestroy
    public void preDestroy1() {
        System.out.println("preDestroy1()");
    }

    @PreDestroy
    public void preDestroy2() {
        System.out.println("preDestroy2()");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean接口中的destroy()");
    }

    //自定义的销毁方法
    public void customDestroyMethod() { //@1
        System.out.println("我是自定义的销毁方法:customDestroyMethod()");
    }
}