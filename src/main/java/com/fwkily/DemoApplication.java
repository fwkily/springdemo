package com.fwkily;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@MapperScan("com.fwkily.mapper.**")
public class DemoApplication {



  public static void main(String[] args) {
    args = new String[]{"我","是","fwk"};
    List<String> list = Arrays.stream(args).collect(Collectors.toList());
    String[] objects = list.toArray(new String[0]);
    ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, objects);
//    String[] names = context.getBeanNamesForType(UserInfoService.class);
//    System.out.println("user:"+names[0]);
//    String[] type = context.getBeanNamesForType(RAWerInfoService.class);
//    System.out.println("type:"+type[0]);
//    String[] type2 = context.getBeanNamesForType(GTerInfoService.class);
//    System.out.println("type2:"+type2[0]);
//    TestEnviroment enviroment = context.getBean(TestEnviroment.class);
//    enviroment.test();

  }



}
