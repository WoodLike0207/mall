package com.lb.mall.service.impl;

import com.lb.mall.dao.UserDAO;
import com.lb.mall.entity.User;
import com.lb.mall.service.UserService;
import com.lb.mall.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Transactional
    public ResultVo userRegist(String name, String password) {
        User user = userDAO.queryUserByName(name);

        if (user != null){  // 账户已存在
            return new ResultVo(10001,"用户名已经被注册",null);
        }



        return null;
    }

    @Override
    public ResultVo checkLogin(String name, String password) {
        User user = userDAO.queryUserByName(name);

        if (user == null){
            return new ResultVo(10001,"用户名不存在",null);
        }else {
            if (user.getPassword().equals(password)){
                return new ResultVo(10000,"登录成功",user);
            }else {
                return new ResultVo(10001,"密码错误",null);
            }
        }
    }
}
