package com.lb.mall.service.impl;

import com.lb.mall.dao.UserAddrMapper;
import com.lb.mall.entity.UserAddr;
import com.lb.mall.service.UserAddrService;
import com.lb.mall.vo.RespStatus;
import com.lb.mall.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserAddrServiceImpl implements UserAddrService {

    @Autowired
    private UserAddrMapper userAddrMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo listAddrsByUid(int userId) {
        Example example = new Example(UserAddr.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        criteria.andEqualTo("status",1);
        List<UserAddr> userAddrs = userAddrMapper.selectByExample(example);
        ResultVo resultVo = new ResultVo(RespStatus.OK, "success", userAddrs);
        return resultVo;
    }
}
