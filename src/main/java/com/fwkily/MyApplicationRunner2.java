package com.fwkily;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @Classname MyApplicationRunner
 * @Description TODO
 * @Date 2022/11/3 9:29 AM
 * @Created by fuwk
 */
@Component
@Order(-1)
@Log4j2
public class MyApplicationRunner2 implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("我是自定义实现ApplicationRunner2，可用于springboot启动后自动执行的逻辑");
        log.info("args：" + Arrays.toString(args.getSourceArgs()));
    }
}
