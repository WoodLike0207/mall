package com.lb.mall.service;

import com.lb.mall.entity.Orders;
import com.lb.mall.vo.ResultVo;

import java.util.Map;

public interface OrderService {
    Map<String,String> addOrder(String cids, Orders order) throws InterruptedException;

    int updateOrderStatus(String orderId,String status);

    ResultVo getOrderById(String orderId);

    void closeOrder(String orderId);

    ResultVo listOrders(String userId,String status,int pageNum,int limit);
}
