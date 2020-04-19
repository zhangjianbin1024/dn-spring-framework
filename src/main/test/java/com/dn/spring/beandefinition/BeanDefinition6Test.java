package com.dn.spring.beandefinition;

import org.junit.Test;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 所有单例bean初始化完成后阶段
 *
 * @author: zh
 * @date: 2020/4/18/018 22:27
 */
public class BeanDefinition6Test {

    /**
     * 所有bean初始化完毕，容器会回调{@link SmartInitializingSingleton#afterSingletonsInstantiated()}
     */
    @Test
    public void test1() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MySmartInitializingSingleton.class);
        System.out.println("开始启动容器!");
        context.refresh();
        System.out.println("容器启动完毕!");
    }

    /**
     * 通过api的方式让DefaultListableBeanFactory去回调SmartInitializingSingleton
     */
    @Test
    public void test2() {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        factory.registerBeanDefinition("service1",
                BeanDefinitionBuilder.genericBeanDefinition(Service1.class)
                        .getBeanDefinition());
        factory.registerBeanDefinition("service2",
                BeanDefinitionBuilder.genericBeanDefinition(Service2.class)
                        .getBeanDefinition());

        factory.registerBeanDefinition("mySmartInitializingSingleton",
                BeanDefinitionBuilder.genericBeanDefinition(MySmartInitializingSingleton.class)
                        .getBeanDefinition());
        System.out.println("准备触发所有单例bean初始化");

        // factory.preInstantiateSingletons 触发所有非lazy单例bean初始化，
        // 所有bean装配完毕之后，会回调SmartInitializingSingleton接口。
        factory.preInstantiateSingletons();
    }
}
