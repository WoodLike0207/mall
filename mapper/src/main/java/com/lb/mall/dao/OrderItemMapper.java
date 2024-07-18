package com.lb.mall.dao;

import com.lb.mall.entity.OrderItem;
import com.lb.mall.general.GeneralDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemMapper extends GeneralDAO<OrderItem> {
    List<OrderItem> listOrderItemsByOrderId(String orderId);
}