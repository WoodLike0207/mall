package com.lb.mall.utils;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageHelper<T> {
      //总记录数
      private int count;
      //总⻚数
      private int pageCount;
      //分⻚数据
      private List<T> list;
}