package com.dn.spring.cglib;

import com.dn.spring.cglib.simple.TargetClass;
import com.dn.spring.cglib.simple.TargetInterceptor;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

/**
 * spring 中采用 回调过滤器的方式
 */
public class CglibProxyTest1 {

    public static void main(String[] args) {
        //Enhancer类是CGLib中的一个字节码增强器
        //使用Enhancer来给某个类创建代理类
        //1.创建Enhancer对象
        Enhancer enhancer = new Enhancer();

        //2.通过setSuperclass来设置父类型，即需要给哪个类创建代理类
        enhancer.setSuperclass(TargetClass.class);

        //不同的方法使用不同的拦截器（CallbackFilter）
        //设置过滤器CallbackFilter
        //CallbackFilter用来判断调用方法的时候使用callbacks数组中的哪个Callback来处理当前方法
        //返回的是callbacks数组的下标
        enhancer.setCallbackFilter(new MethodCallBackFilter());
        //这里定义了3个增强，3个回调
        Callback c1 = new TargetInterceptor();
        //直接放行，不做任何操作（NoOp.INSTANCE）
        //不做任何操作，即不增强
        Callback c2 = NoOp.INSTANCE;
        //调用增强的方法，并返回增加的方法值
        Callback c3 = new FixedValueClass();

        Callback[] callbacks = new Callback[]{c1, c2, c3};
        //3.设置回调，需实现org.springframework.cglib.proxy.Callback接口
        //此处我们使用的是org.springframework.cglib.proxy.MethodInterceptor，也是一个接口，实现了Callback接口，
        //当调用代理对象的任何方法的时候，都会被 MethodInterceptor接口的invoke方法处理
        enhancer.setCallbacks(callbacks);

        //4.获取代理对象,调用enhancer.create方法获取代理对象，这个方法返回的是Object类型的，所以需要强转一下
        TargetClass cglibProxy = (TargetClass) enhancer.create();

        //5.调用代理对象的方法
        cglibProxy.add();
        cglibProxy.del();
        cglibProxy.update();
    }

}
