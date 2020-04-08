package com.dn.spring.mybatis.service;

import com.dn.spring.mybatis.bean.UserDo;
import com.dn.spring.mybatis.dao.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * spring 事务测试
 *
 * @author: zh
 * @date: 2020/4/3/003 22:11
 */
@Service
public class UserManageService {

    private Logger logger = LoggerFactory.getLogger(UserManageService.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private InsertUserService insertUserService;

    /**
     * 只有一个事务时，事务传播机制是不启作用的
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void execut() {
        UserDo userDo = new UserDo();
        userDo.setName("10");
        userDo.setAge(10);
        userMapper.save(userDo);
        logger.info("execut-1 事务执行");

        //事务二 抛出异常
        insertUserService.execute();


    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void execut2() {
        UserDo userDo = new UserDo();
        userDo.setName("10");
        userDo.setAge(10);
        userMapper.save(userDo);
        logger.info("execut2-1 事务执行");

        //事务二 正常提交
        //当事务二为 Propagation.REQUIRES_NEW 时，事务二的数据提交，则事务一的数据回滚
        insertUserService.execute2();

        //事务一 抛出异常
        throw new RuntimeException("回滚");

    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void execut3() {
        UserDo userDo = new UserDo();
        userDo.setName("10");
        userDo.setAge(10);
        userMapper.save(userDo);
        logger.info("execut3-1 事务执行");

        try {
            //事务二 异常
            insertUserService.execute3();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
