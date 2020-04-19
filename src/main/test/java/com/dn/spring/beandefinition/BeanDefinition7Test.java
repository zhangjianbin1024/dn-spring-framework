package com.dn.spring.beandefinition;

import com.dn.spring.jdkproxy.service.ServiceA;
import com.dn.spring.jdkproxy.service.ServiceB;
import org.junit.Test;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;

/**
 * Bean销毁阶段
 *
 * @author: zh
 * @date: 2020/4/18/018 22:38
 */
public class BeanDefinition7Test {

    /**
     * 自定义DestructionAwareBeanPostProcessor
     */
    @Test
    public void test1() {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

        //添加自定义的DestructionAwareBeanPostProcessor
        factory.addBeanPostProcessor(new MyDestructionAwareBeanPostProcessor());

        //向容器中注入3个单例bean
        factory.registerBeanDefinition("serviceA1",
                BeanDefinitionBuilder.genericBeanDefinition(ServiceA.class)
                        .getBeanDefinition());
        factory.registerBeanDefinition("serviceA2",
                BeanDefinitionBuilder.genericBeanDefinition(ServiceA.class)
                        .getBeanDefinition());
        factory.registerBeanDefinition("serviceA3",
                BeanDefinitionBuilder.genericBeanDefinition(ServiceA.class)
                        .getBeanDefinition());

        //触发所有单例bean初始化
        factory.preInstantiateSingletons(); //@1

        System.out.println("销毁serviceA1");
        //销毁指定的bean
        factory.destroySingleton("serviceA1");//@2

        System.out.println("触发所有单例bean的销毁");
        factory.destroySingletons();
    }

    /**
     * 触发@PreDestroy标注的方法被调用
     */
    @Test
    public void test2() {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

        //添加自定义的DestructionAwareBeanPostProcessor
        factory.addBeanPostProcessor(new MyDestructionAwareBeanPostProcessor()); //@1
        //将CommonAnnotationBeanPostProcessor加入
        //这个会处理bean中标注@PreDestroy注解的方法
        factory.addBeanPostProcessor(new CommonAnnotationBeanPostProcessor()); //@2

        //向容器中注入bean
        factory.registerBeanDefinition("serviceB",
                BeanDefinitionBuilder.genericBeanDefinition(ServiceB.class)
                        .getBeanDefinition());

        //触发所有单例bean初始化
        factory.preInstantiateSingletons();

        System.out.println("销毁serviceB");
        //销毁指定的bean
        factory.destroySingleton("serviceB");
    }

    /**
     * 通过ApplicationContext来销毁bean
     * <p>
     * 可以看出销毁方法调用的顺序：
     *
     * @PreDestroy标注的所有方法 <p>
     * DisposableBean接口中的destroy()
     * <p>
     * 自定义的销毁方法
     */
    @Test
    public void test3() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(DestroyConfigurable.class);
        //启动容器
        System.out.println("准备启动容器");
        context.refresh();
        System.out.println("容器启动完毕");
        System.out.println("destroyBean：" + context.getBean(DestroyBean.class));
        //关闭容器
        System.out.println("准备关闭容器");
        //调用容器的close方法，会触发bean的销毁操作
        context.close(); //@2
        System.out.println("容器关闭完毕");
    }

}
