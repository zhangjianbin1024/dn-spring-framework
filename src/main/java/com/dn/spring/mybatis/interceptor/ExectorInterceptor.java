package com.dn.spring.mybatis.interceptor;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dn.spring.mybatis.bean.UserDo;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * sql查询时，先查询缓存
 */
@Intercepts(
        //每配置一个 @Signature 则就可以拦截一个方法
        // 这里指要拦截 Executor 类中的 query 方法
        {@Signature(method = "query", type = Executor.class,
                //根据参数的类型找对应的方法
                args = {
                        MappedStatement.class, Object.class, RowBounds.class,
                        ResultHandler.class}
        )}
)
public class ExectorInterceptor implements Interceptor {

    public static final Map<String, String> cacheMap = new ConcurrentHashMap();

    /**
     * 代理类调用方法时，会进入该方法
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (invocation.getTarget() instanceof Executor) {
            Map map = (Map) invocation.getArgs()[1];
            // 执行sql的参数中有 cacheKey时
            String cacheKey = map.containsKey("cacheKey") ? map.get("cacheKey")
                    .toString() : "";
            // 从缓存容器器中获取缓存的结果并返回，不再查询数据库
            String cacheStr = cacheMap.get(cacheKey);
            if (cacheStr != null && !"".equals(cacheStr)) {
                JSONArray ja = JSONArray.parseArray(cacheStr);
                List<UserDo> list = new ArrayList<UserDo>();
                for (Object o : ja) {
                    list.add(JSONObject.parseObject(o.toString(),
                            UserDo.class));
                }
                return list;
            }
        }
        return invocation.proceed();
    }

    /**
     * 这个方法会被 InterceptorChain.pluginAll 调用到
     *
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        //必须判断是否是拦截的类型
        if (target instanceof Executor) {
            //返回当前插件的代理类
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
    }

}
