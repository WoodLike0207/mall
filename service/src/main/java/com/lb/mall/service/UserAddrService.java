package com.lb.mall.service;

import com.lb.mall.vo.ResultVo;

public interface UserAddrService {
     ResultVo listAddrsByUid(int userId);
}