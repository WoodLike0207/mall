package com.lb.mall.service.impl;

import com.lb.mall.dao.IndexImgMapper;
import com.lb.mall.entity.IndexImg;
import com.lb.mall.service.IndexImgService;
import com.lb.mall.vo.RespStatus;
import com.lb.mall.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexImgServiceImpl implements IndexImgService {

    @Autowired
    private IndexImgMapper indexImgMapper;

    @Override
    public ResultVo listIndexImgs() {
        List<IndexImg> indexImgs = indexImgMapper.listIndexImgs();
        if (indexImgs.size() == 0){
            return new ResultVo(RespStatus.NO,"fail",null);
        }else {
            return new ResultVo(RespStatus.OK,"success",indexImgs);
        }
    }
}
