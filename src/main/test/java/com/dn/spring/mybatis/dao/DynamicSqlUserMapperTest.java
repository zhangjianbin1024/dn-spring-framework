package com.dn.spring.mybatis.dao;

import com.dn.spring.BaseXmlTest;
import com.dn.spring.mybatis.bean.UserDo;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

public class DynamicSqlUserMapperTest extends BaseXmlTest {

    private Logger logger = LoggerFactory.getLogger(DynamicSqlUserMapperTest.class);


    @Autowired
    DynamicSqlUserMapper dynamicSqlUserMapper;

    @Test
    public void getUserListByIds() {
        List<Integer> UserIds = Arrays.asList(1, 2);
        List<UserDo> userDos = dynamicSqlUserMapper.getUserListByIds(UserIds);
        logger.info("userDos:[{}]", userDos);

    }

    @Test
    public void getUserList_choose() {
        UserDo userDo = new UserDo();
        userDo.setAge(10);
        List<UserDo> userList_choose = dynamicSqlUserMapper.getUserList_choose(userDo);
        logger.info("userList_choose:[{}]", userList_choose);

    }

    @Test
    public void updateUser_if_set() {
        UserDo userDo = new UserDo();
        userDo.setId(1);
        userDo.setAge(10);
        Integer updateCount = dynamicSqlUserMapper.updateUser_if_set(userDo);
        logger.info("updateCount:[{}]", updateCount);
    }

    @Test
    public void updateUser_if_trim() {
        UserDo userDo = new UserDo();
        userDo.setId(1);
        userDo.setAge(20);
        Integer updateCount = dynamicSqlUserMapper.updateUser_if_trim(userDo);
        logger.info("updateCount:[{}]", updateCount);
    }


    @Test
    public void query_user_byId_2() {
        List<UserDo> userDos = dynamicSqlUserMapper.query_user_byId_2(1);
        logger.info("userDos:[{}]", userDos);
    }


}