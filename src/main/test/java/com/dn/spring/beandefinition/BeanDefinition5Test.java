package com.dn.spring.beandefinition;

import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.beans.PropertyDescriptor;

/**
 * Bean实例化阶段
 */
public class BeanDefinition5Test {

    /**
     * bean初始化前阶段，会调用：{@link org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor#postProcessBeforeInitialization(Object, String)}
     * <p>
     * <p>
     * bean定义的时候，名称为：奥迪，最后输出的为：保时捷
     * 定义和输出不一致的原因是因为我们在
     * InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation方法
     * 中手动创建了一个实例直接返回了，而不是依靠spring内部去创建这个实例。
     */
    @Test
    public void test1() {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

        //添加一个BeanPostProcessor：InstantiationAwareBeanPostProcessor
        factory.addBeanPostProcessor(new InstantiationAwareBeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                return bean;
            }

            @Override
            public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
                //System.out.println("调用postProcessBeforeInstantiation()");
                ////发现类型是Car类型的时候，硬编码创建一个Car对象返回
                if (beanClass == Car.class) {
                    Car car = new Car();
                    car.setName("保时捷");
                    return car;
                }
                return null;
            }

            @Override
            public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
                return true;
            }

            @Override
            public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
                return pvs;
            }
        });

        //定义一个car bean,车名为：奥迪
        AbstractBeanDefinition carBeanDefinition = BeanDefinitionBuilder.
                genericBeanDefinition(Car.class).
                addPropertyValue("name", "奥迪").  //@2
                getBeanDefinition();
        factory.registerBeanDefinition("car", carBeanDefinition);
        //从容器中获取car这个bean的实例，输出
        System.out.println(factory.getBean("car"));

    }

    /**
     * 通过构造器对bean中的属性进行赋值
     */
    @Test
    public void test2() {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

        //创建一个SmartInstantiationAwareBeanPostProcessor,将其添加到容器中
        factory.addBeanPostProcessor(new MySmartInstantiationAwareBeanPostProcessor());

        factory.registerBeanDefinition("name",
                BeanDefinitionBuilder.
                        genericBeanDefinition(String.class).
                        addConstructorArgValue("路人甲Java").
                        getBeanDefinition());

        factory.registerBeanDefinition("age",
                BeanDefinitionBuilder.
                        genericBeanDefinition(Integer.class).
                        addConstructorArgValue(30).
                        getBeanDefinition());

        factory.registerBeanDefinition("person",
                BeanDefinitionBuilder.
                        genericBeanDefinition(Person.class).
                        getBeanDefinition());

        //通过上面返回的构造器对bean中的属性进行赋值
        //从输出中可以看出调用了Person中标注@MyAutowired标注的构造器。
        Person person = factory.getBean("person", Person.class);
        System.out.println(person);
    }


    ////////////////////////////// Bean属性设置阶段 /////////////////////////////////////////

    /**
     * postProcessAfterInstantiation
     * <p>
     * 返回false的时候，后续的Bean属性赋值前处理、Bean属性赋值都会被跳过
     */
    @Test
    public void test3() {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

        factory.addBeanPostProcessor(new InstantiationAwareBeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                return bean;
            }

            @Override
            public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
                return null;
            }

            /**
             * postProcessAfterInstantiation
             *
             * 返回false的时候，后续的Bean属性赋值前处理、Bean属性赋值都会被跳过
             *
             * 阻止 bean 名字为 user1的属性赋值
             * @param bean
             * @param beanName
             * @return
             * @throws BeansException
             */
            @Override
            public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
                if ("user1".equals(beanName)) {
                    return false;
                } else {
                    return true;
                }
            }

            /**
             * 这个不返回时，则spring 通过 BeanDefinition 创建的对象属性是没有值的
             * @param pvs
             * @param pds
             * @param bean
             * @param beanName
             * @return
             * @throws BeansException
             */
            @Override
            public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
                return pvs;
            }
        });

        factory.registerBeanDefinition("user1", BeanDefinitionBuilder.
                genericBeanDefinition(UserModel.class).
                addPropertyValue("name", "路人甲Java").
                addPropertyValue("age", 30).
                getBeanDefinition());

        factory.registerBeanDefinition("user2", BeanDefinitionBuilder.
                genericBeanDefinition(UserModel.class).
                addPropertyValue("name", "刘德华").
                addPropertyValue("age", 50).
                getBeanDefinition());

        for (String beanName : factory.getBeanDefinitionNames()) {
            System.out.println(String.format("%s->%s", beanName, factory.getBean(beanName)));
        }
    }

    /**
     * 对 bean进行属性值信息进行修改。
     */
    @Test
    public void test4() {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

        factory.addBeanPostProcessor(new InstantiationAwareBeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                return bean;
            }

            @Override
            public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
                return null;
            }

            @Override
            public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
                return true;
            }

            /**
             * 对 bean 名称 user1 这个 bean 进行属性值信息进行修改
             *
             * @param pvs
             * @param pds
             * @param bean
             * @param beanName
             * @return
             * @throws BeansException
             */
            @Override
            public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
                if ("user1".equals(beanName)) {
                    if (pvs == null) {
                        pvs = new MutablePropertyValues();
                    }
                    if (pvs instanceof MutablePropertyValues) {
                        MutablePropertyValues mpvs = (MutablePropertyValues) pvs;
                        //将姓名设置为：路人
                        mpvs.add("name", "路人");
                        //将年龄属性的值修改为18
                        mpvs.add("age", 18);
                    }
                }
                return pvs;
            }
        });

        //注意 user1 这个没有给属性设置值,通过上面的处理器对bean进行赋值
        factory.registerBeanDefinition("user1", BeanDefinitionBuilder.
                genericBeanDefinition(UserModel.class).
                getBeanDefinition());

        //注意 user2 这个有给属性设置值
        factory.registerBeanDefinition("user2", BeanDefinitionBuilder.
                genericBeanDefinition(UserModel.class).
                addPropertyValue("name", "刘德华").
                addPropertyValue("age", 50).
                getBeanDefinition());

        for (String beanName : factory.getBeanDefinitionNames()) {
            System.out.println(String.format("%s->%s", beanName, factory.getBean(beanName)));
        }
    }

    ////////////////////////////// Bean初始化阶段 ////////////////////////////////////////


    /**
     * Bean Aware接口回调
     */
    @Test
    public void test5() {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        factory.registerBeanDefinition("awareBean",
                BeanDefinitionBuilder.genericBeanDefinition(AwareBean.class)
                        .getBeanDefinition());

        //调用getBean方法获取bean，将触发bean的初始化
        factory.getBean("awareBean");
    }

    /**
     * Bean初始化前操作
     * <p>
     * 调用@PostConstruct标注的方法
     */
    @Test
    public void test6() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Bean1.class);

        //spring 容器启动
        context.refresh();
    }

    /**
     * Bean初始化阶段
     * <p>
     * 调用顺序：InitializingBean中的afterPropertiesSet、然后在调用自定义的初始化方法
     */
    @Test
    public void test7() {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        BeanDefinition service = BeanDefinitionBuilder.genericBeanDefinition(Service3.class).
                setInitMethodName("init"). //@1：指定自定义初始化方法
                getBeanDefinition();

        factory.registerBeanDefinition("Service3", service);

        System.out.println(factory.getBean("Service3"));
    }

    /**
     * bean初始化后置操作
     */
    @Test
    public void test8() {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

        //加入bean初始化后置处理器方法实现
        factory.addBeanPostProcessor(new BeanPostProcessor() {
            /**
             * bean初始化前置操作
             * @param bean
             * @param beanName
             * @return
             * @throws BeansException
             */
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                System.out.println("postProcessBeforeInitialization：" + beanName);

                return bean;
            }

            /**
             * bean初始化后置操作
             *
             * @param bean
             * @param beanName
             * @return
             * @throws BeansException
             */
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                System.out.println("postProcessAfterInitialization：" + beanName);
                return bean;
            }
        });

        //下面注册2个String类型的bean
        factory.registerBeanDefinition("name",
                BeanDefinitionBuilder.
                        genericBeanDefinition(String.class).
                        addConstructorArgValue("公众号：【路人甲Java】").
                        getBeanDefinition());

        factory.registerBeanDefinition("personInformation",
                BeanDefinitionBuilder.genericBeanDefinition(String.class).
                        addConstructorArgValue("带领大家成为java高手！").
                        getBeanDefinition());

        System.out.println("-------输出bean信息---------");

        for (String beanName : factory.getBeanDefinitionNames()) {
            System.out.println(String.format("%s->%s", beanName, factory.getBean(beanName)));
        }
    }
}