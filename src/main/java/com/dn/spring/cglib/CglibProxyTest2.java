package com.dn.spring.cglib;

import com.dn.spring.cglib.simple.TargetClass;
import net.sf.cglib.proxy.*;

import java.lang.reflect.Method;

/**
 * spring 中采用 回调过滤器的方式
 */
public class CglibProxyTest2 {

    public static void main(String[] args) {
        //创建 Callback
        Callback costTimeCallback = (MethodInterceptor) (Object o, Method method, Object[] objects, MethodProxy methodProxy) -> {
            long starTime = System.nanoTime();
            Object result = methodProxy.invokeSuper(o, objects);
            long endTime = System.nanoTime();
            System.out.println(method + "，耗时(纳秒):" + (endTime - starTime));
            return result;
        };
        Callback fixdValueCallback = (FixedValue) () -> "Java";

        //CallbackHelper类相当于对一些代码进行了封装
        CallbackHelper callbackHelper = new CallbackHelper(TargetClass.class, null) {
            @Override
            protected Object getCallback(Method method) {
                //用来拦截所有and开头的方法，返回固定值的
                return method.getName().startsWith("add") ? costTimeCallback : fixdValueCallback;
            }
        };


        //Enhancer类是CGLib中的一个字节码增强器
        //使用Enhancer来给某个类创建代理类
        //1.创建Enhancer对象
        Enhancer enhancer = new Enhancer();

        //2.通过setSuperclass来设置父类型，即需要给哪个类创建代理类
        enhancer.setSuperclass(TargetClass.class);

        //设置CallbackFilter,用来判断某个方法具体走哪个Callback
        enhancer.setCallbackFilter(callbackHelper);


        //3.设置回调，需实现org.springframework.cglib.proxy.Callback接口
        //此处我们使用的是org.springframework.cglib.proxy.MethodInterceptor，也是一个接口，实现了Callback接口，
        //当调用代理对象的任何方法的时候，都会被 MethodInterceptor接口的invoke方法处理
        enhancer.setCallbacks(callbackHelper.getCallbacks());

        //4.获取代理对象,调用enhancer.create方法获取代理对象，这个方法返回的是Object类型的，所以需要强转一下
        TargetClass cglibProxy = (TargetClass) enhancer.create();

        //5.调用代理对象的方法
        cglibProxy.add();
        System.out.println(cglibProxy.del());
        cglibProxy.update();
    }

}
