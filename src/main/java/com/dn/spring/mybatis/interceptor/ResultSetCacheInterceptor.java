package com.dn.spring.mybatis.interceptor;

import com.alibaba.fastjson.JSONArray;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * 对 查询结果进行缓存
 */
@Intercepts(
        {
                @Signature(type = ResultSetHandler.class, method = "handleResultSets",
                        args = {Statement.class})
        })
public class ResultSetCacheInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        if (invocation.getTarget() instanceof DefaultResultSetHandler) {
            DefaultResultSetHandler rsh = (DefaultResultSetHandler) invocation.getTarget();
            Field boundSqlf = getField(rsh, "boundSql");
            boundSqlf.setAccessible(true);
            BoundSql boundSql = (BoundSql) boundSqlf.get(rsh);

            // 获取sql 的参数
            Map map = (Map) boundSql.getParameterObject();
            if (map.containsKey("isCache")) {
                //判断查询是否需要缓存

                // sql 查询结果
                List<Object> results = (List<Object>) invocation.proceed();
                String resultStr = JSONArray.toJSONString(results);
                // sql查询结果进行缓存
                ExectorInterceptor.cacheMap.put(map.get("cacheKey").toString(), resultStr);
                return results;
            }
        }

        return invocation.proceed();
    }

    private Field getField(Object obj, String name) {
        Field field = ReflectionUtils.findField(obj.getClass(), name);
        field.setAccessible(true);
        return field;
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof ResultSetHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
