package com.dn.spring.transactional;

import com.dn.spring.BaseTest;
import com.dn.spring.mybatis.bean.UserDo;
import com.dn.spring.mybatis.service.InsertUserService;
import com.dn.spring.mybatis.service.QueryUserService;
import com.dn.spring.mybatis.service.UpdateUserService;
import com.dn.spring.mybatis.service.UserManageService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: zh
 * @date: 2020/4/3/003 21:46
 */
public class UserServiceTest extends BaseTest {
    @Autowired
    private InsertUserService insertUserService;

    @Autowired
    private UpdateUserService updateUserService;

    @Autowired
    private QueryUserService queryUserService;

    @Autowired
    private UserManageService userManageService;

    @Test
    public void save() {
        UserDo userDo = new UserDo();
        userDo.setAge(11);
        userDo.setName("keke");
        int save = insertUserService.save(userDo);
        System.out.println(save);
    }

    /**
     * 1.第一个 execut 事务保存
     * 2.第二个 execut 事务保存，并报异常
     * <p>
     * 结论：
     * 1.  两个事务是不一样，两个事务都会回滚，不论第二个事务的传播机制是啥，都不管用，都回滚
     * 2. 事务传播机制，在第一次是不起作用的。
     * 3. 事务传播机制执行逻辑：AbstractPlatformTransactionManager#getTransaction(org.springframework.transaction.TransactionDefinition)
     */
    @Test
    public void t1() {
        userManageService.execut();
    }

    /**
     * 事务一抛异常
     * 事务二 为 propagation = Propagation.REQUIRES_NEW 时，则事务二正常提交，事务一数据回滚
     */
    @Test
    public void t2() {
        userManageService.execut2();
    }

    /**
     * 场景一：
     * 当事务一 catch 事务二异常时，并且事务二为  @Transactional 时，会产生事务异常如下所示 ：
     * <p>
     * org.springframework.transaction.UnexpectedRollbackException:
     * Transaction rolled back because it has been marked as rollback-only
     * <p>
     * 场景二：
     * 当事务一 catch 事务二异常时，并且事务二为  @Transactional(propagation = Propagation.REQUIRES_NEW) 时
     * 事务二回滚，事务一则正常提交
     * <p>
     * 场景三：
     * 当事务一 catch 事务二异常时，并且事务二为没有被标记为 @Transactional时，则事务一和事务二都会提交
     */
    @Test
    public void t3() {
        userManageService.execut3();
    }
}
