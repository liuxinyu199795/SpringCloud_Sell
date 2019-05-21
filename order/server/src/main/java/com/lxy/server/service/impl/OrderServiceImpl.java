package com.lxy.server.service.impl;


import com.lxy.client.ProductClient;
import com.lxy.commom.DecreaseStockInput;
import com.lxy.commom.ProductInfoOutput;
import com.lxy.server.dataobject.OrderDetail;
import com.lxy.server.dataobject.OrderMaster;
import com.lxy.server.dto.OrderDTO;
import com.lxy.server.enums.OrderStutusEnum;
import com.lxy.server.enums.PayStatusEnum;
import com.lxy.server.enums.ResultEnum;
import com.lxy.server.exception.OrderException;
import com.lxy.server.repository.OrderDetailRepository;
import com.lxy.server.repository.OrderMasterRepository;
import com.lxy.server.service.OrderService;
import com.lxy.server.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductClient productClient;


    @Override
    @Transactional
    public OrderDTO finish(String orderId) {
        //1.先查询订单
            Optional<OrderMaster> orderMasterOptional =orderMasterRepository.findById(orderId);
            if(!orderMasterOptional.isPresent()){
                throw new OrderException(ResultEnum.ORDER_NOT_EXIST);
            }
        //2.判断订单状态
            OrderMaster orderMaster = orderMasterOptional.get();
            if(OrderStutusEnum.NEW.getCode()!=orderMaster.getOrderStatus()){
                throw new OrderException(ResultEnum.ORDER_STATUS_ERROR);
            }
        //3.修改订单状态为完结
            orderMaster.setOrderStatus(OrderStutusEnum.FINSHED.getCode());
            orderMasterRepository.save(orderMaster);

        //查询订单详情
        List<OrderDetail> orderDetailList=orderDetailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new OrderException(ResultEnum.ORDER_DETAIL_NOT_EXIT);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }


    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();

        //1.查询商品信息(调用商品服务)
        List<String> productIdList =orderDTO.getOrderDetailList().stream()
                .map(OrderDetail::getProductId)
                .collect(Collectors.toList());
        List<ProductInfoOutput> productInfoList=productClient.listForOrder(productIdList);
        //2.计算总价
        BigDecimal orderAmout = new BigDecimal(BigInteger.ZERO);
        for(OrderDetail orderDetail:orderDTO.getOrderDetailList()){//数量
            //总价=单价*数量
            for(ProductInfoOutput productInfo:productInfoList){//单价
                if(productInfo.getProductId().equals(orderDetail.getProductId())){
                    orderAmout=productInfo.getProductPrice()
                            .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(orderAmout);
                    //遍历设置订单详情
                    BeanUtils.copyProperties(productInfo,orderDetail);
                    orderDetail.setOrderId(orderId);
                    orderDetail.setDetailId(KeyUtil.genUniqueKey());
                    //订单详情入库
                    orderDetailRepository.save(orderDetail);
                }
            }
        }

        //3.扣库存(调用商品服务)
        List<DecreaseStockInput> cartDTOList=orderDTO.getOrderDetailList().stream()
                .map(e -> new DecreaseStockInput(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productClient.decreaseStock(cartDTOList);

        //4.订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmout);
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaster.setOrderStatus(OrderStutusEnum.NEW.getCode());
        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }
}
