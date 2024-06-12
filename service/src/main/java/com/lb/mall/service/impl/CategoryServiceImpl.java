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

    @Override
    public ResultVo listCategories() {
        List<CategoryVO> categoryVOS = categoryMapper.selectAllCategories();
        ResultVo resultVo = new ResultVo(RespStatus.OK, "success", categoryVOS);
        return resultVo;
    }
}
