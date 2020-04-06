package com.dn.spring.mybatis.dao;

import com.dn.spring.mybatis.bean.UserDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DynamicSqlUserMapper {

    List<UserDo> getUserListByIds(@Param("userIds") List<Integer> userIds);

    List<UserDo> getUserList_choose(UserDo userDo);

    Integer updateUser_if_set(UserDo userDo);

    Integer updateUser_if_trim(UserDo userDo);

    List<UserDo> query_user_byId_2(@Param("id") Integer userId);
}
