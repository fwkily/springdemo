package com.fwkily;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @Classname MyCommandLineRunner
 * @Description TODO
 * @Date 2022/11/3 9:34 AM
 * @Created by fuwk
 */
@Component
@Slf4j
public class MyCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("自定义实现CommandLineRunner，可用于springboot启动后自动执行的逻辑");
        log.info("Running default command line with: " + Arrays.asList(args));
    }
}
