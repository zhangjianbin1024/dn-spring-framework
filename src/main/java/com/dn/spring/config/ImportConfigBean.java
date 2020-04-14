package com.dn.spring.config;

import org.springframework.context.annotation.Import;

/**
 * @author: zh
 * @date: 2020/4/14/014 22:52
 */
//@Import(MyImportBeanDefinitionRegistrar.class)
@Import(MyImportSelector.class)
public class ImportConfigBean {
}
