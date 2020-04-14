package com.dn.spring.config;

import com.dn.spring.jdkproxy.service.ServiceA;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: zh
 * @date: 2020/4/14/014 22:52
 */
//@EnableMethodCostTime
@Configuration
public class EnableConfigBean {
    @Bean
    public ServiceA serviceA() {
        return new ServiceA();
    }
}
