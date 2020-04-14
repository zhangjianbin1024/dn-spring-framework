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
 * @author: zh
 * @date: 2020/4/4/004 11:57
 */
public class UserMapperTest extends BaseXmlTest {
    private Logger logger = LoggerFactory.getLogger(UserMapperTest.class);

    @Autowired
    private UserMapper userMapper;

    @Test
    public void save() {
    }

    @Test
    public void update() {
    }

    @Test
    public void oneUser() {
    }

    @Test
    public void list() {
    }

    @Test
    public void count() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void countProvider() {
        int count = userMapper.countProvider(1);
        logger.info("countProvider:[{}]", count);
    }

    @Test
    public void selectUserById() {
        UserDo userDo = userMapper.selectUserById(1);
        logger.info("selectUserById:[{}]", userDo);
    }

    @Test
    public void queryPage() {
        HashMap<String, Object> map = new HashMap<>();

        Page page = new Page();
        page.setBeginPage(1);
        page.setPageSize(15);
        //需要分页
        page.setNeedPage(true);
        map.put("page", page);
        map.put("isCache", true);
        map.put("cacheKey", "queryUser");
        map.put("type", 1);
        List<UserDo> userDos = userMapper.queryPage(map);
        System.out.println(userDos);
    }
}