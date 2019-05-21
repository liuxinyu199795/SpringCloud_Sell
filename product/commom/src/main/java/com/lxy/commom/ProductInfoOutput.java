package com.lxy.commom;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ProjectName: product
 * @Package: com.lxy.commom
 * @ClassName: ProductInfoOutput
 * @Author: XinyuLiu
 * @Date: 2019/5/13 18:09
 */
@Data
public class ProductInfoOutput {
    private  String productId;
    private  String productName;//名字
    private BigDecimal productPrice;//单价
    private  Integer productStock;//库存
    private  String productDescription;//描述
    private  String productIcon;//小图
    private  Integer productStatus;//状态
    private  Integer categoryType;//类目编号
}
