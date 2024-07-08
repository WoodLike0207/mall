package com.lb.mall.service.impl;

import com.lb.mall.dao.ProductCommentsMapper;
import com.lb.mall.entity.ProductComments;
import com.lb.mall.entity.ProductCommentsVO;
import com.lb.mall.service.ProductCommentsService;
import com.lb.mall.utils.PageHelper;
import com.lb.mall.vo.RespStatus;
import com.lb.mall.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ProductCommentsServiceImpl implements ProductCommentsService {
    @Autowired
    private ProductCommentsMapper productCommentsMapper;

    @Override
    public ResultVo listCommentsByProductId(String productId,int pageNum,int limit) {
        // 1. 根据商品Id查询总记录数
        Example example = new Example(ProductComments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId",productId);
        int count = productCommentsMapper.selectCountByExample(example);

        // 2.计算总页数（必须确定每页显示多少条，pageSize = limit）
        int pageCount = count%limit == 0 ? count/limit : count/limit + 1;

        // 3. 查询当前页的数据（因为评论中需要用户信息，所以需要连表查询---自定义）
        int start = (pageNum - 1) * limit;
        List<ProductCommentsVO> list = productCommentsMapper.selectCommentsByProductId(productId, start, limit);

        ResultVo resultVo = new ResultVo(RespStatus.OK, "success", new PageHelper<ProductCommentsVO>(count, pageCount, list));
        return resultVo;
    }
}
