package com.lb.mall.service;

import com.lb.mall.vo.BasicResultVO;
import com.lb.mall.vo.ResultVo;

public interface UserService {

    ResultVo userRegist(String name, String password);

    ResultVo checkLogin(String name, String password);
}
