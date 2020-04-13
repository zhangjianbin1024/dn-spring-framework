package com.dn.spring.cglib;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

public class MethodCallBackFilter implements CallbackFilter {

    /**
     * 当调用代理对象的方法的时候，具体会走哪个Callback呢，
     * 此时会通过CallbackFilter中的 accept 来判断，这个方法返回 callbacks 数组的索引。
     * <p>
     */
    @Override
    public int accept(Method method) {
        if ("add".equals(method.getName())) {
            return 0;
        } else if ("del".equals(method.getName())) {
            return 1;
        } else if ("update".equals(method.getName())) {
            return 2;
        }
        return 0;
    }

}
