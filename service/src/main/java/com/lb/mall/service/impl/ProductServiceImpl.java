package com.lb.mall.service.impl;

import com.lb.mall.dao.ProductImgMapper;
import com.lb.mall.dao.ProductMapper;
import com.lb.mall.dao.ProductParamsMapper;
import com.lb.mall.dao.ProductSkuMapper;
import com.lb.mall.entity.*;
import com.lb.mall.service.ProductService;
import com.lb.mall.utils.PageHelper;
import com.lb.mall.vo.RespStatus;
import com.lb.mall.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductImgMapper productImgMapper;
    @Autowired
    private ProductSkuMapper productSkuMapper;
    @Autowired
    private ProductParamsMapper productParamsMapper;

    @Override
    public ResultVo listRecommendProducts() {
        List<ProductVO> productVOS = productMapper.selectRecommendProducts();
        ResultVo resultVo = new ResultVo(RespStatus.OK,"success",productVOS);
        return resultVo;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo getProductBasicInfo(String productId) {
        // 1.商品基本信息
        Example example = new Example(Product.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId",productId);
        criteria.andEqualTo("productStatus",1);

        List<Product> products = productMapper.selectByExample(example);
        if (products.size() > 0){
            // 2.商品图片
            Example example1 = new Example(ProductImg.class);
            Example.Criteria criteria1 = example1.createCriteria();
            criteria1.andEqualTo("itemId",productId);
            List<ProductImg> productImgs = productImgMapper.selectByExample(example1);

            // 3.商品套餐
            Example example2 = new Example(ProductSku.class);
            Example.Criteria criteria2 = example2.createCriteria();
            criteria2.andEqualTo("productId",productId);
            criteria2.andEqualTo("status",1);
            List<ProductSku> productSkus = productSkuMapper.selectByExample(example2);

            HashMap<String,Object> basicInfo = new HashMap<>();
            basicInfo.put("product",products.get(0));
            basicInfo.put("productImgs",productImgs);
            basicInfo.put("productSkus",productSkus);

            return new ResultVo(RespStatus.OK,"success",basicInfo);
        }else {
            return new ResultVo(RespStatus.NO,"查询的商品不存在",null);
        }
    }

    @Override
    public ResultVo getProductParamsById(String productId) {
        Example example = new Example(ProductParams.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId",productId);
        List<ProductParams> productParams = productParamsMapper.selectByExample(example);

        if (productParams.size() > 0){
            return new ResultVo(RespStatus.OK,"success",productParams.get(0));
        }else {
            return new ResultVo(RespStatus.NO,"此商品可能为三无产品",null);
        }
    }

    @Override
    public ResultVo getProductByCategoryId(int categoryId, int pageNum, int limit) {
        // 1.查询分页数据
        int start = (pageNum-1) * limit;
        List<ProductVO> productVOS = productMapper.selectProductByCategoryId(categoryId, start, limit);

        // 2.查询当前类别下的商品的总记录数
        Example example = new Example(Product.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("categoryId",categoryId);
        int count = productMapper.selectCountByExample(example);

        // 3.计算总页数
        int pageCount = count%limit == 0 ? count/limit : count/limit + 1;

        // 4.封装返回数据
        PageHelper<ProductVO> pageHelper = new PageHelper<>(count,pageCount,productVOS);
        return new ResultVo(RespStatus.OK,"SUCCESS",pageHelper);
    }

    @Override
    public ResultVo listBrands(int categoryId) {
        List<String> brands = productMapper.selectBrandByCategoryId(categoryId);
        return new ResultVo(RespStatus.OK,"SUCCESS",brands);
    }

    @Override
    public ResultVo searchProduct(String kw, int pageNum, int limit) {
        // 1.查询搜索结果
        kw = "%"+kw+"%";
        int start = (pageNum - 1) * limit;
        List<ProductVO> productVOS = productMapper.selectProductByKeyword(kw, start, limit);

        // 2.查询总记录数
        Example example = new Example(Product.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("productName",kw);
        int count = productMapper.selectCountByExample(example);

        // 3.计算总页数
        int pageCount = count%limit==0 ? count/limit : count/limit+1;

        // 4.封装返回数据
        PageHelper<ProductVO> pageHelper = new PageHelper<>(count, pageCount, productVOS);
        ResultVo resultVo = new ResultVo(RespStatus.OK,"success",pageHelper);
        return resultVo;
    }
}
