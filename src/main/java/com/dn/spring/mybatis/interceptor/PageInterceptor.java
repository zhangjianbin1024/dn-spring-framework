package com.dn.spring.mybatis.interceptor;

import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

/**
 * 要写分页逻辑
 * 1、拿到被代理对象RoutingStatementHandler
 * 2、拿到被代理对象的private final StatementHandler delegate;PreparedStatementHandler
 * 3、拿到PreparedStatementHandler里面的BoundSql属性
 * 4、修改BoundSql属性
 */
@Intercepts(
        {
                @Signature(type = StatementHandler.class,
                        method = "prepare",
                        args = {Connection.class, Integer.class})
        })
public class PageInterceptor implements Interceptor {


    /**
     * 我们的最终目的是修改BoundSql中的sql，所有就需要拿到被代理类中的BoundSql对象
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler stmtHandler = null;
        if (invocation.getTarget() instanceof StatementHandler) {
            stmtHandler = (StatementHandler) invocation.getTarget();
        }

        /**
         * 分析源码发现被代理对象RoutingStatementHandler中有一个StatementHandler delegate;属性
         * public Statement prepare(Connection connection, Integer transactionTimeout) throws SQLException {
         *      return delegate.prepare(connection, transactionTimeout);
         * }
         delegate对象就是PreparedStatementHandler对象，而PreparedStatementHandler对象中有一个BoundSql属性，
         所有我们第一步要获取PreparedStatementHandler
         StatementHandler statementHandler =
         new RoutingStatementHandler(executor, mappedStatement, parameterObject, rowBounds, resultHandler, boundSql);
         */
        //SystemMetaObject.forObject(stmtHandler);
        //通过反射获取 类 RoutingStatementHandler 中的属性 delegate 值
        Field delegateF = getField(stmtHandler, "delegate");
        StatementHandler preparedHandler = (StatementHandler) delegateF.get(stmtHandler);

        // preparedHandler 为 PreparedStatementHandler 类
        Field boundSqlF = getField(preparedHandler, "boundSql");
        BoundSql boundSql = (BoundSql) boundSqlF.get(preparedHandler);
        //获取sql 字符串
        String sql = boundSql.getSql();
        // 获取sql的入参
        Map paramObject = (Map) boundSql.getParameterObject();

        if (paramObject.containsKey("page")) {
            //获取 page 对象
            Page page = (Page) paramObject.get("page");
            if (!page.isNeedPage()) {
                //不需要分页时
                return invocation.proceed();
            }
            sql = pageSql(sql, page);
        }

        Field sqlF = getField(boundSql, "sql");
        sqlF.setAccessible(true);
        //将构建的新 sql 字符串 重新设置到对象中
        sqlF.set(boundSql, sql);
        return invocation.proceed();
    }

    private String pageSql(String sql, Page page) {
        StringBuffer sb = new StringBuffer();
        sb.append(sql);
        sb.append(" limit ");
        sb.append(page.getBeginPage());
        sb.append("," + page.getPageSize());
        return sb.toString();
    }

    private Field getField(Object obj, String name) {
        Field field = ReflectionUtils.findField(obj.getClass(), name);
        field.setAccessible(true);
        return field;
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof RoutingStatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
