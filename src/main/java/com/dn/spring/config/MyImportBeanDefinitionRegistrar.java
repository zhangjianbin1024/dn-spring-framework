package com.dn.spring.config;

import com.dn.spring.jdkproxy.service.ServiceA;
import com.dn.spring.jdkproxy.service.ServiceB;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 向spring 容器注册bean
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //定义一个bean：Service1
        BeanDefinition service1BeanDinition = BeanDefinitionBuilder.genericBeanDefinition(ServiceA.class).getBeanDefinition();
        //注册bean ServiceA
        registry.registerBeanDefinition("serviceA", service1BeanDinition);

        //定义一个bean：ServiceB，通过addPropertyReference注入serviceA
        BeanDefinition service2BeanDinition = BeanDefinitionBuilder.genericBeanDefinition(ServiceB.class).
                addPropertyReference("serviceA", "serviceA").
                getBeanDefinition();
        //注册bean ServiceB
        registry.registerBeanDefinition("serviceB", service2BeanDinition);
    }
}