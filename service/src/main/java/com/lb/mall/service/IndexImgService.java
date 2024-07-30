package com.lb.mall.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lb.mall.vo.ResultVo;

public interface IndexImgService {

    public ResultVo listIndexImgs() throws JsonProcessingException;
}
