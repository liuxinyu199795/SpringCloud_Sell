package com.lxy.server.repository;

import com.lxy.server.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {
    List<ProductInfo> findByProductStatus(Integer product);

    List<ProductInfo> findByProductIdIn(List<String> productIdList);
}
