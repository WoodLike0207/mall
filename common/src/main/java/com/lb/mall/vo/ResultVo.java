package com.lb.mall.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "vo对象",description = "封装接口返回给前端的数据")
public class ResultVo {

    @ApiModelProperty(value = "响应状态码",dataType = "int")
    private int code;

    @ApiModelProperty("响应提示信息")
    private String msg;

    @ApiModelProperty("响应数据")
    private Object data;
}
