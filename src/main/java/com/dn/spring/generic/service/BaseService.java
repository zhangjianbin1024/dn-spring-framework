package com.dn.spring.generic.service;

import com.dn.spring.generic.dao.IDao;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseService<T> {
    /**
     * 注入 IDao 的 实现类
     */
    @Autowired
    private IDao<T> dao; //@1

    public IDao<T> getDao() {
        return dao;
    }

    public void setDao(IDao<T> dao) {
        this.dao = dao;
    }
}