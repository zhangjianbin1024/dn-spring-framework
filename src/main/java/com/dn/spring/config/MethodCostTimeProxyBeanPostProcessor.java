package com.dn.spring.config;

import com.dn.spring.cglib.CostTimeProxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MethodCostTimeProxyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().getName().toLowerCase().contains("service")) {
            //为符合的bean 创建代理类
            return CostTimeProxy.createProxy(bean);
        } else {
            return bean;
        }
    }
}