package com.lb.mall.dao;

import com.lb.mall.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO {

    int insertUser(User user);

    User queryUserByName(String name);
}
