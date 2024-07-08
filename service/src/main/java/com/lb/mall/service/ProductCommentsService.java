package com.lb.mall.service;

import com.lb.mall.vo.ResultVo;
import org.springframework.stereotype.Service;


public interface ProductCommentsService {
    ResultVo listCommentsByProductId(String productId);
}
