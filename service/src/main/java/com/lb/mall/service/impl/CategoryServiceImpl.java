package com.lb.mall.service.impl;

import com.lb.mall.dao.CategoryMapper;
import com.lb.mall.entity.CategoryVO;
import com.lb.mall.service.CategoryService;
import com.lb.mall.vo.RespStatus;
import com.lb.mall.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 查询分类列表（包含三个级别的分类）
     * @return
     */
    @Override
    public ResultVo listCategories() {
        List<CategoryVO> categoryVOS = categoryMapper.selectAllCategories();
        ResultVo resultVo = new ResultVo(RespStatus.OK, "success", categoryVOS);
        return resultVo;
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
