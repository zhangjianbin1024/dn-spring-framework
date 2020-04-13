package com.dn.spring.cglib.simple;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 2
 * 相当于切面，增加类
 */
public class TargetInterceptor implements MethodInterceptor {

    private void before() {
        System.out.println("cglib前置增强");
    }

    /**
     * 代理对象方法拦截器
     *
     * @param obj    代理对象
     * @param method 被代理的类的方法
     * @param args   调用方法传递的参数
     * @param proxy  方法代理对象
     *
     * @return
     *
     * @throws Throwable
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args,
                            MethodProxy proxy) throws Throwable {
        System.out.println(method.getName());
        before();
        //可以调用被代理类中的方法
        //可以调用MethodProxy的invokeSuper调用被代理类的方法
        proxy.invokeSuper(obj, args);
        after();
        return null;
    }

    private void after() {
        System.out.println("cglib后置增强");
    }

}
