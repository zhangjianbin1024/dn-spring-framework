package com.dn.spring.mybatis.dao;

import com.dn.spring.BaseXmlTest;
import com.dn.spring.mybatis.bean.UserDo;
import com.dn.spring.mybatis.interceptor.Page;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

/**
 * mybatis 测试
 *
 * @author: zh
 * @date: 2020/4/4/004 11:57
 */
public class UserMapperTest extends BaseXmlTest {
    private Logger logger = LoggerFactory.getLogger(UserMapperTest.class);

    @Autowired
    private UserMapper userMapper;

    @Test
    public void save(){
        UserDo userDo = new UserDo();
        //userDo.setAge(11);
        userDo.setName("zhang");
        userMapper.save(userDo);
    }

    /**
     * 对 @SelectProvider 注解测试
     */
    @Test
    public void countProvider() {
        int count = userMapper.countProvider(1);
        logger.info("countProvider:[{}]", count);
    }

    /**
     * 动态sql 测试
     */
    @Test
    public void selectUserById() {
        UserDo userDo = userMapper.selectUserById(1);
        logger.info("selectUserById:[{}]", userDo);
    }

    /**
     * 分页插件测试
     */
    @Test
    public void queryPage() {
        HashMap<String, Object> map = new HashMap<>();

        Page page = new Page();
        page.setBeginPage(1);
        page.setPageSize(15);
        //需要分页
        page.setNeedPage(true);
        map.put("page", page);
        List<UserDo> userDos = userMapper.queryPage(map);
        System.out.println(userDos);
    }

    /**
     * sql 查询结果缓存，
     * <p>
     * sql 查询时，查询先查询缓存
     */
    @Test
    public void queryCache() {
        HashMap<String, Object> map = new HashMap<>();
        //查询时，需要缓存
        map.put("isCache", true);
        //缓存的 key 名
        map.put("cacheKey", "queryUser");

        //查询时，进行缓存
        List<UserDo> userDos = userMapper.queryPage(map);
        System.out.println(userDos);

        // 查询时，先查询缓存，缓存中存在时，直接返回缓存结果
        userDos = userMapper.queryPage(map);
        System.out.println(userDos);
    }
}