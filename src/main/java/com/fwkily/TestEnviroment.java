package com.fwkily;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TestEnviroment {

    @Resource
    Environment environment;

    @Value("${fwkname:圣诞节佛啊说}")
    String name;

    public void test(){
        System.out.println("该环境是：" + environment.getActiveProfiles()[0]);
        System.out.println("名字是：" + name);
    }

}
