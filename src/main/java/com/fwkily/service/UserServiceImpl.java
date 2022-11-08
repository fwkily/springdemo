//package com.fwkily.service;
//
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.fwkily.mapper.UserMapper;
//import com.fwkily.mapper.po.OrderPo;
//import com.fwkily.mapper.po.UserPo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.Serializable;
//import java.util.Collection;
//
//@Service
//public class UserServiceImpl extends ServiceImpl<UserMapper, UserPo> implements UserService{
//
//    @Autowired
//    OrderService orderService;
//
//    @Override
//    @Transactional
//    public boolean removeById(Serializable id) {
//        super.removeById(id);
//        OrderPo orderPo = new OrderPo();
//        orderPo.setHobby("张三");
//        orderService.save(orderPo);
////        if(true) {
////            throw new RuntimeException("手动异常！");
////        }
//
//        return true;
//    }
//
//    @Override
//    @Transactional
//    //必须要加@Transactional出现异常才会回滚
//    public boolean removeByIds(Collection<? extends Serializable> idList) {
//        idList.forEach(id -> {
//            removeById(id);
//            if(true) {
//                throw new RuntimeException("手动异常！");
//            }
//
//        });
//        return true;
//    }
//}
