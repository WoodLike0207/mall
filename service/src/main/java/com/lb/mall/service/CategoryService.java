package com.lb.mall.service;

import com.lb.mall.vo.ResultVo;

public interface CategoryService {
    ResultVo listCategories();

    ResultVo listFirstLevelCategories();
}
