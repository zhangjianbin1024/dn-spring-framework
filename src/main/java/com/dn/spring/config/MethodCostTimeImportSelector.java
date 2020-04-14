package com.dn.spring.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MethodCostTimeImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        // 注册 bean MethodCostTimeProxyBeanPostProcessor
        return new String[]{MethodCostTimeProxyBeanPostProcessor.class.getName()};
    }
}