package com.lb.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lb.mall.dao.ProductImgMapper;
import com.lb.mall.dao.ProductMapper;
import com.lb.mall.dao.ProductParamsMapper;
import com.lb.mall.dao.ProductSkuMapper;
import com.lb.mall.entity.*;
import com.lb.mall.service.ProductService;
import com.lb.mall.utils.PageHelper;
import com.lb.mall.vo.RespStatus;
import com.lb.mall.vo.ResultVo;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.util.*;

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
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RestHighLevelClient restHighLevelClient;


    @Override
    public ResultVo listRecommendProducts() {
        List<ProductVO> productVOS = productMapper.selectRecommendProducts();
        ResultVo resultVo = new ResultVo(RespStatus.OK,"success",productVOS);
        return resultVo;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo getProductBasicInfo(String productId){
        try{
            // 根据商品id查询redis
            String productsInfo = (String) stringRedisTemplate.boundHashOps("products").get(productId);

            // redis查到了就直接返回给控制器
            if (productsInfo != null){
                Product product = objectMapper.readValue(productsInfo, Product.class);

                String imgsStr = (String) stringRedisTemplate.boundHashOps("productImgs").get(productId);
                JavaType javaType1 = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, ProductImg.class);
                List<ProductImg> productImgs = objectMapper.readValue(imgsStr, javaType1);

                String skusStr = (String) stringRedisTemplate.boundHashOps("productSkus").get(productId);
                JavaType javaType2 = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, ProductSku.class);
                List<ProductSku> productSkus = objectMapper.readValue(skusStr,javaType2);

                HashMap<String,Object> basicInfo = new HashMap<>();
                basicInfo.put("product",product);
                basicInfo.put("productImgs",productImgs);
                basicInfo.put("productSkus",productSkus);

                return new ResultVo(RespStatus.OK,"success",basicInfo);
            }else {
                // redis没查到，则查询数据库

                // 1.商品基本信息
                Example example = new Example(Product.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("productId",productId);
                criteria.andEqualTo("productStatus",1);


                List<Product> products = productMapper.selectByExample(example);
                if (products.size() > 0){
                    // 将从数据库查询到的数据写入redis中
                    Product product = products.get(0);
                    String jsonStr = objectMapper.writeValueAsString(product);
                    stringRedisTemplate.boundHashOps("products").put(productId,jsonStr);


                    // 2.商品图片
                    Example example1 = new Example(ProductImg.class);
                    Example.Criteria criteria1 = example1.createCriteria();
                    criteria1.andEqualTo("itemId",productId);
                    List<ProductImg> productImgs = productImgMapper.selectByExample(example1);
                    stringRedisTemplate.boundHashOps("productImgs").put(productId,objectMapper.writeValueAsString(productImgs));

                    // 3.商品套餐
                    Example example2 = new Example(ProductSku.class);
                    Example.Criteria criteria2 = example2.createCriteria();
                    criteria2.andEqualTo("productId",productId);
                    criteria2.andEqualTo("status",1);
                    List<ProductSku> productSkus = productSkuMapper.selectByExample(example2);
                    stringRedisTemplate.boundHashOps("productSkus").put(productId,objectMapper.writeValueAsString(productSkus));


                    HashMap<String,Object> basicInfo = new HashMap<>();
                    basicInfo.put("product",products.get(0));
                    basicInfo.put("productImgs",productImgs);
                    basicInfo.put("productSkus",productSkus);

                    return new ResultVo(RespStatus.OK,"success",basicInfo);
                }
            }
        }catch (Exception e){
        }
        return new ResultVo(RespStatus.NO,"fail",null);
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
        ResultVo resultVo = null;
        try {

            // 1.查询搜索结果
            int start = (pageNum - 1) * limit;
            // 从ES查询数据
            SearchRequest searchRequest = new SearchRequest("mall_product_index");

            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

            searchSourceBuilder.query(QueryBuilders.multiMatchQuery(kw,"productName","productSkuName"));

            searchSourceBuilder.from(start);
            searchSourceBuilder.size(limit);

            // 高亮显示
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            HighlightBuilder.Field field1 = new HighlightBuilder.Field("productName");
            HighlightBuilder.Field field2 = new HighlightBuilder.Field("productSkuName");
            highlightBuilder.field(field1);
            highlightBuilder.field(field2);
            highlightBuilder.preTags("<label style='color:red'>");
            highlightBuilder.postTags("</label>");
            searchSourceBuilder.highlighter(highlightBuilder);
            searchRequest.source(searchSourceBuilder);

            // 执行检索
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

            // 封装查询结果
            SearchHits hits = searchResponse.getHits();
            int count = (int) hits.getTotalHits().value;

            // 计算总页数
            int pageCount = count%limit==0 ? count/limit : count/limit+1;
            List<Product4ES> products = new ArrayList<>();
            Iterator<SearchHit> iterator = hits.iterator();
            while (iterator.hasNext()){
                SearchHit searchHit = iterator.next();
                Product4ES product4ES = JSON.parseObject(searchHit.getSourceAsString(), Product4ES.class);

                Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
                HighlightField highlightField1 = highlightFields.get("productName");
                if (highlightField1 != null){
                    String highLightProductName = Arrays.toString(highlightField1.fragments());
                    product4ES.setProductName(highLightProductName);
                }

                products.add(product4ES);
            }

            // 4.封装返回数据
            PageHelper<Product4ES> pageHelper = new PageHelper<>(count, pageCount, products);
            resultVo = new ResultVo(RespStatus.OK,"success",pageHelper);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return resultVo;
    }

    @Override
    public ResultVo listBrands(String kw) {
        kw = '%'+kw+'%';
        List<String> brands = productMapper.selectBrandByKeyword(kw);
        return new ResultVo(RespStatus.OK,"success",brands);
    }
}
