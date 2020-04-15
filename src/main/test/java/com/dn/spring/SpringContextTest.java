package com.dn.spring;

import com.dn.spring.annotationaop.AspectjInterceptor;
import com.dn.spring.config.ConfigBean;
import com.dn.spring.config.EnableConfigBean;
import com.dn.spring.config.ImportConfigBean;
import com.dn.spring.jdkproxy.service.ServiceA;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringContextTest {


    /**
     * 测试 注解方式的spring 容器 AnnotationConfigApplicationContext
     */
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

    /**
     * 对于 ClassPathXmlApplicationContext 的使用：
     * <p>
     * classpath: 前缀是可加可不加的 , 默认就是指项目的 classpath 路径下面。
     * 如果要使用绝对路径 , 需要加上 file: , 前缀表示这是绝对路径。
     * 对于 FileSystemXmlApplicationContext 的使用：
     * <p>
     * 没有盘符的是项目工作路径 , 即项目的根目录。
     * 有盘符表示的是文件绝对路径 ，file: 可加可不加。
     * 如果要使用 classpath 路径 , 需要前缀 classpath:
     */
    @Test
    public void t2() {

        // 用classpath路径
        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:config/spring/applicationContext-*.xml");
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String bean : beanDefinitionNames) {
            System.out.println(bean);
        }
    }

    /**
     * 获取 ConfigBean 配置类中的bean
     */
    @Test
    public void t3() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigBean.class);
        for (String beanName : context.getBeanDefinitionNames()) {
            System.out.println(String.format("bean名称:%s,bean对象:%s",
                    beanName,
                    context.getBean(beanName)));
        }
    }

    /**
     * 测试 @Import(MyImportBeanDefinitionRegistrar.class) 注解
     */
    @Test
    public void test4() {
        //1.通过AnnotationConfigApplicationContext创建spring容器，参数为@Import标注的类
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ImportConfigBean.class);
        //2.输出容器中定义的所有bean信息
        for (String beanName : context.getBeanDefinitionNames()) {
            System.out.println(String.format("%s->%s", beanName, context.getBean(beanName)));
        }
    }

    /**
     * 测试 @Import(MyImportSelector.class)
     */
    @Test
    public void test5() {
        //1.通过AnnotationConfigApplicationContext创建spring容器，参数为@Import标注的类
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ImportConfigBean.class);
        //2.输出容器中定义的所有bean信息
        for (String beanName : context.getBeanDefinitionNames()) {
            System.out.println(String.format("%s->%s", beanName, context.getBean(beanName)));
        }
    }

    /**
     * 测试  @EnableMethodCostTime
     */
    @Test
    public void test6() {
        //1.通过AnnotationConfigApplicationContext创建spring容器，参数为@Import标注的类
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(EnableConfigBean.class);
        //2.输出容器中定义的所有bean信息
        for (String beanName : context.getBeanDefinitionNames()) {
            System.out.println(String.format("%s->%s", beanName, context.getBean(beanName)));
        }

        ServiceA service1 = context.getBean(ServiceA.class);
        service1.m1();
    }
}
