package com.dn.spring;

import com.dn.spring.aop.IntroductionIntf;
import com.dn.spring.listener.MyEvent;
import com.dn.spring.postprocess.Student;
import com.dn.spring.schema.People;
import com.dn.spring.service.MyService;
import com.dn.spring.service.MyServiceImp2;
import com.dn.spring.transaction.AnnotationTransactionService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class SpringTest extends BaseXmlTest implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringTest.class);

    //ApplicationContextAware 实现这个接口获取  ApplicationContext 上下文
    public ApplicationContext context;

    /**
     * 对beanDifinition的修改
     */
    @Test
    public void test1() {
        Student s = (Student) context.getBean("jackstudent");
        LOGGER.info(s.getUsername());
        LOGGER.info(s.getPassword());

        //启动过程中添加bean的属性值的结果
        LOGGER.info(s.getSchool());
    }

    /**
     * 自定义标签
     */
    @Test
    public void test2() {
        People p = (People) context.getBean("cutesource");
        LOGGER.info(p.toString());
    }


    /**
     * service 实现化
     * <p>
     * 通过debug,执行service方法时，会调用 org.springframework.aop.framework.JdkDynamicAopProxy#invoke
     */
    @Test
    public void test3() {
        MyService service = (MyService) context.getBean("xmlService");
        LOGGER.info(service.getClass().toString());
        service.execute();

    }

    /**
     * 注解 aop 测试
     */
    @Test
    public void test3_2() {
        MyServiceImp2 xmlService2 = (MyServiceImp2) context.getBean("xmlService2");
        xmlService2.execute();
    }

    /**
     * 通过aop动态注入方法 到 myservice bean 中
     */
    @Test
    public void test4() {
        IntroductionIntf service = (IntroductionIntf) context.getBean("myservice");
        service.extend();
    }


    /**
     * 监听器测试
     */
    @Test
    public void test5() {
        MyEvent myEvent = new MyEvent("自定义监听器");
        // 发布事件，给所有的监听者
        context.publishEvent(myEvent);

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 注解事物测试
     * <p>
     * SpringTransactionAnnotationParser#parseTransactionAnnotation(java.lang.reflect.AnnotatedElement)
     */
    @Test
    public void test6() {
        AnnotationTransactionService bean = context.getBean(AnnotationTransactionService.class);
        String save = bean.save();
        System.out.println(save);
    }

    /**
     * 获取 ApplicationContext 上下文
     *
     * @param arg0
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext arg0)
            throws BeansException {
        this.context = arg0;
    }
}
