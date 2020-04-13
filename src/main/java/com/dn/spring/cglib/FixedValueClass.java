package com.dn.spring.cglib;

import net.sf.cglib.proxy.FixedValue;

/**
 * 拦截所有方法并返回固定值
 * <p>
 * 返回一个固定值，即不执行被代理的方法
 */
public class FixedValueClass implements FixedValue {

    @Override
    public Object loadObject() throws Exception {
        System.out.println("invoke fixedValue");
        return 123;
    }

}
