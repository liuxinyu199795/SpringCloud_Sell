package com.lxy.server.service;



import com.lxy.commom.DecreaseStockInput;
import com.lxy.server.dataobject.ProductInfo;

import java.util.List;

public interface ProductService {
    //查询所有在架商品列表
    List<ProductInfo> findUpAll();

    /**
    * @Description:  查询商品列表
    * @Param: [productIdList]
    * @return: java.util.List<com.lxy.product.dataobject.ProductInfo>
    * @Author: XinyuLiu
    * @Date: 2019/5/7
    */
    List<ProductInfo> findList(List<String> productIdList);

    /**
    * @Description: 扣库存
    * @Param: [cartDTOList]
    * @return: void
    * @Author: XinyuLiu
    * @Date: 2019/5/8
    */
    void decreaseStock(List<DecreaseStockInput> decreaseStockInputList);
}
