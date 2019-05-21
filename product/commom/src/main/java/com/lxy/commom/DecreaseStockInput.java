package com.lxy.commom;

import lombok.Data;

/**
 * @ProjectName: product
 * @Package: com.lxy.commom
 * @ClassName: DecreaseStockInput
 * @Author: XinyuLiu
 * @Date: 2019/5/13 18:50
 */
@Data
public class DecreaseStockInput {
    private String productId;

    private Integer productQuantity;

    public DecreaseStockInput() {
    }

    public DecreaseStockInput(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
