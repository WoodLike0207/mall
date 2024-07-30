package com.lb.mall.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lb.mall.dao.IndexImgMapper;
import com.lb.mall.entity.IndexImg;
import com.lb.mall.entity.Product;
import com.lb.mall.entity.ProductImg;
import com.lb.mall.service.IndexImgService;
import com.lb.mall.vo.RespStatus;
import com.lb.mall.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class IndexImgServiceImpl implements IndexImgService {

    @Autowired
    private IndexImgMapper indexImgMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ResultVo listIndexImgs() {
        List<IndexImg> indexImgs = null;

        try {
            // string结构缓存轮播图信息
            String imgStr = stringRedisTemplate.boundValueOps("indexImgs").get();

            if (imgStr != null){
                // 从redis中获取到了轮播图信息
                JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, IndexImg.class);
                indexImgs = objectMapper.readValue(imgStr, javaType);

            }else {
                // 在redis没查到，则查询数据库
                indexImgs = indexImgMapper.listIndexImgs();

                // 写到redis
                stringRedisTemplate.boundValueOps("indexImgs").set(objectMapper.writeValueAsString(indexImgs));

                // 设置过期时间为一天
                stringRedisTemplate.boundValueOps("indexImgs").expire(1, TimeUnit.DAYS);

            }

        } catch (JsonProcessingException e) {

            throw new RuntimeException(e);

        }

        // 返回数据
        if (indexImgs != null){
            return new ResultVo(RespStatus.OK,"success",indexImgs);
        }else {
            return new ResultVo(RespStatus.NO,"fail",null);
        }
    }
}
