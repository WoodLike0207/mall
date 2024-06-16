package com.lb.mall.service.impl;

import com.lb.mall.dao.ProductMapper;
import com.lb.mall.entity.ProductVO;
import com.lb.mall.service.ProductService;
import com.lb.mall.vo.RespStatus;
import com.lb.mall.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ResultVo listRecommendProducts() {
        List<ProductVO> productVOS = productMapper.selectRecommendProducts();
        ResultVo resultVo = new ResultVo(RespStatus.OK,"success",productVOS);
        return resultVo;
    }
}
