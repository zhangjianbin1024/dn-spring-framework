package com.dn.spring.config;

import com.dn.spring.jdkproxy.service.ServiceA;
import com.dn.spring.jdkproxy.service.ServiceB;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 向spring 容器注册bean
 */
@Configuration
public class ConfigBean {

    @Bean
    public ServiceA serviceA() {
        System.out.println("调用serviceA()方法");
        return new ServiceA();
    }

    @Bean
    public ServiceB serviceB1() {
        System.out.println("调用serviceB1()方法");
        ServiceA serviceA = this.serviceA();
        return new ServiceB(serviceA);
    }
}