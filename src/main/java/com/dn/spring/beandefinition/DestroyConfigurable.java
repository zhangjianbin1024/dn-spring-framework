package com.dn.spring.beandefinition;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

@Configurable
public class DestroyConfigurable {

    @Bean(destroyMethod = "customDestroyMethod") //@1
    public DestroyBean destroyBean() {
        return new DestroyBean();
    }

}