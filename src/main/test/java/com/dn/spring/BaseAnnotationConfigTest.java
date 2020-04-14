package com.dn.spring;

import com.dn.spring.annotationaop.AspectjInterceptor;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BaseAnnotationConfigTest {
    @Test
    public void t1() {
        // 通过 AnnotationConfigApplicationContext 创建spring 容器
        //通过注解方式向spring 容器中注册bean
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AspectjInterceptor.class);

        // 输出spring容器中定义的所有bean信息
        for (String beanName : applicationContext.getBeanDefinitionNames()) {
            System.out.println(beanName);
        }

    }
}
