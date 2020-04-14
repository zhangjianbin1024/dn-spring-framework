package com.dn.spring.config;

import com.dn.spring.jdkproxy.service.ServiceA;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 向spring 容器注册bean
 */
public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        // 注册 bean ServiceA
        return new String[]{
                ServiceA.class.getName()
        };
    }
}