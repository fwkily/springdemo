package com.fwkily.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fwkily.mapper.OrderMapper;
import com.fwkily.mapper.UserMapper;
import com.fwkily.mapper.po.OrderPo;
import com.fwkily.mapper.po.UserPo;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderPo> implements OrderService {



}
