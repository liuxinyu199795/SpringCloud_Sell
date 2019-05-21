package com.lxy.server.dto;

import lombok.Data;

/**
 * @ProjectName: product
 * @Package: com.lxy.product.DTO
 * @ClassName: CartDTO
 * @Author: XinyuLiu
 * @Date: 2019/5/8 10:21
 */
@Data
public class CartDTO {
    /**
     * 商品Id
     */
    private String productId;
    /**
     * 商品数量
     */
    private Integer productQuantity;

    public CartDTO() {
    }

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
