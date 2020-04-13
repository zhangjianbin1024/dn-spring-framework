package com.dn.spring.cglib.simple;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

/**
 * 3
 * cglib 测试
 */
public class CglibProxyTest {

    public static void main(String[] args) {

        //设置将cglib生成的代理类字节码生成到指定位置
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "F:\\class");

        //Enhancer类是CGLib中的一个字节码增强器
        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(TargetClass.class);
        enhancer.setCallback(new TargetInterceptor());

        TargetClass cglibProxy = (TargetClass) enhancer.create();
        cglibProxy.add();

        System.out.println("代理类" + cglibProxy.getClass());

    }

}
