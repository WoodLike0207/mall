package com.lb.mall.service.impl;

import com.lb.mall.dao.ProductCommentsMapper;
import com.lb.mall.entity.ProductCommentsVO;
import com.lb.mall.service.ProductCommentsService;
import com.lb.mall.vo.RespStatus;
import com.lb.mall.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCommentsServiceImpl implements ProductCommentsService {
    @Autowired
    private ProductCommentsMapper productCommentsMapper;

    @Override
    public ResultVo listCommentsByProductId(String productId) {
        List<ProductCommentsVO> productCommentsVOS = productCommentsMapper.selectCommentsByProductId(productId);
        ResultVo resultVo = new ResultVo(RespStatus.OK, "success", productCommentsVOS);
        return resultVo;
    }
}
