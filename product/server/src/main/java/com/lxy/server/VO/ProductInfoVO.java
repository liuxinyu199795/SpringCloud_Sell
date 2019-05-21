package com.lxy.server.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductInfoVO {
    @JsonProperty("id")
    private  String productId;

    @JsonProperty("name")
    private  String productName;//名字

    @JsonProperty("price")
    private BigDecimal productPrice;//单价

    @JsonProperty("description")
    private  String productDescription;//描述

    @JsonProperty("icon")
    private  String productIcon;//小图
}
