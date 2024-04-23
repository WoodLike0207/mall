package com.lb.mall.service.impl;

import com.lb.mall.dao.UserDAO;
import com.lb.mall.entity.User;
import com.lb.mall.service.UserService;
import com.lb.mall.utils.MD5Utils;
import com.lb.mall.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDAO userDAO;

    @Transactional
    public ResultVo userRegist(String name, String password) {
        User user = userDAO.queryUserByName(name);

        if (user != null){  // 账户已存在
            return new ResultVo(10001,"用户名已经被注册",null);
        }

        String md5Pwd = MD5Utils.md5(password);
        user = new User();
        user.setUsername(name);
        user.setPassword(md5Pwd);
        user.setUserImg("img/default.png");
        user.setUserRegtime(new Date());
        user.setUserModtime(new Date());

        int i = userDAO.insertUser(user);
        if (i > 0){
            return new ResultVo(10000,"注册成功",null);
        }else {
            return new ResultVo(10002,"注册失败",null);
        }
    }

    @Override
    public ResultVo checkLogin(String name, String password) {
        User user = userDAO.queryUserByName(name);

        if (user == null){
            return new ResultVo(10001,"用户名不存在",null);
        }else {
            String md5Pwd = MD5Utils.md5(password);
            if (user.getPassword().equals(md5Pwd)){
                return new ResultVo(10000,"登录成功",user);
            }else {
                return new ResultVo(10001,"密码错误",null);
            }
        }
    }
}
