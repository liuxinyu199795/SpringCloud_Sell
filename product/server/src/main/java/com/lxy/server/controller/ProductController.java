package com.lxy.server.controller;

import com.lxy.commom.DecreaseStockInput;
import com.lxy.server.VO.ProductInfoVO;
import com.lxy.server.VO.ProductVO;
import com.lxy.server.VO.ResultVO;
import com.lxy.server.dataobject.ProductCategory;
import com.lxy.server.dataobject.ProductInfo;
import com.lxy.server.service.CategoryService;
import com.lxy.server.service.ProductService;
import com.lxy.server.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;
        //1.查询所有在架的商品
        //2.获取类目type列表
        //3.从数据库查询类目
        //4.构造数据
    @GetMapping("/list")
    public ResultVO<ProductVO> list(){
        //1.查询所有在架的商品
        List<ProductInfo> productInfoList = productService.findUpAll();
        //2.获取类目type列表
        List<Integer> categoryTypeList = productInfoList.stream().map(ProductInfo::getCategoryType).collect(Collectors.toList());
        //3.从数据库查询类目
        List<ProductCategory> categoryList = categoryService.findByCategoryTypeIn(categoryTypeList);
        //4.构造数据
        List<ProductVO> productVOList = new ArrayList<>();
        for(ProductCategory productCategory:categoryList){
            ProductVO productVO =new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());
            List<ProductInfoVO> productInfoVOList =new ArrayList<>();
            for(ProductInfo productInfo:productInfoList){
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }
        return ResultVOUtil.success(productVOList);
    }
    /**
    * @Description:  获取商品列表（给订单服务用的）
    * @Param: [productIdList]
    * @return: java.util.List<com.lxy.product.dataobject.ProductInfo>
    * @Author: XinyuLiu
    * @Date: 2019/5/7
    */
    @PostMapping("/listForOrder")
    public List<ProductInfo> listForOrder(@RequestBody List<String> productIdList){
        return productService.findList(productIdList);
    }
    @PostMapping("/decreaseStock")
    public void decreaseStock(@RequestBody List<DecreaseStockInput> decreaseStockInputList) {
        productService.decreaseStock(decreaseStockInputList);
    }
}
