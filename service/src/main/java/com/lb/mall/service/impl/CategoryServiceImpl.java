package com.lb.mall.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lb.mall.dao.CategoryMapper;
import com.lb.mall.entity.CategoryVO;
import com.lb.mall.service.CategoryService;
import com.lb.mall.vo.RespStatus;
import com.lb.mall.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 查询分类列表（包含三个级别的分类）
     * @return
     */
    @Override
    public ResultVo listCategories() {
        List<CategoryVO> categoryVOS = null;

        try {
            String categories = stringRedisTemplate.boundValueOps("categories").get();

            if (categories != null){
                JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, CategoryVO.class);
                categoryVOS = objectMapper.readValue(categories, javaType);

            }else {
                categoryVOS =  categoryMapper.selectAllCategories();
                String str = objectMapper.writeValueAsString(categoryVOS);
                stringRedisTemplate.boundValueOps("categories").set(str,1, TimeUnit.DAYS);
            }

            return new ResultVo(RespStatus.OK, "success", categoryVOS);

        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

        return new ResultVo(RespStatus.NO,"fail",null);
    }

    /**
     * 查询所有一级分类，同时查询当前一级分类下销量最高的6个商品
     * @return
     */
    @Override
    public ResultVo listFirstLevelCategories() {
        List<CategoryVO> categoryVOS = categoryMapper.selectFirstLevelCategories();
        ResultVo resultVo = new ResultVo(RespStatus.OK, "success", categoryVOS);
        return resultVo;
    }
}
