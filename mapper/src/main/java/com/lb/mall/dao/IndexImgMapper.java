package com.lb.mall.dao;

import com.lb.mall.entity.IndexImg;
import com.lb.mall.general.GeneralDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndexImgMapper extends GeneralDAO<IndexImg> {

    // 1.查询轮播图信息：查询status = 1 且按照 seq 进行排序
    public List<IndexImg> listIndexImgs();
}