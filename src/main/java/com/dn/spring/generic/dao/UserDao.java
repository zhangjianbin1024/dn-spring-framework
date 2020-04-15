package com.dn.spring.generic.dao;

import com.dn.spring.generic.domain.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserDao implements IDao<UserModel> { //@1
}