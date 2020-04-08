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
 * @author: zh
 * @date: 2020/4/3/003 21:39
 */
@Service
public class InsertUserService {

    private Logger logger = LoggerFactory.getLogger(InsertUserService.class);


    @Autowired
    private UserMapper userMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int save(UserDo userDo) {
        return userMapper.save(userDo);
    }

    /**
     * 前一个事务会挂起，并创建一个新的事务执行下面的代码
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void execute() {
        UserDo userDo = new UserDo();
        userDo.setAge(50);
        userDo.setName("50");

        int save = userMapper.save(userDo);
        logger.info("execute-2 事务执行");

        throw new RuntimeException("回滚");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void execute2() {
        UserDo userDo = new UserDo();
        userDo.setAge(50);
        userDo.setName("50");

        int save = userMapper.save(userDo);
        logger.info("execute-2 事务执行");


    }

    //@Transactional
    //@Transactional(propagation = Propagation.REQUIRES_NEW)
    public void execute3() {
        UserDo userDo = new UserDo();
        userDo.setAge(50);
        userDo.setName("50");

        int save = userMapper.save(userDo);
        logger.info("execute-2 事务执行");

        //事务二 抛出异常
        throw new RuntimeException("回滚");


    }
}
