package com.fwkily;

import com.fwkily.service.Person;
import com.fwkily.service.Son;
import com.fwkily.service.TestA;

import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args) {

//        long a = 10;
//        int b = (int) a;
//        System.out.println(b);
//        TestA test = new TestA();
//        test.take(new Son(){
//
//            @Override
//            public void sayHello() {
//                System.out.println("hello,我是sayHello");
//            }
//
//            @Override
//            public void say() {
//                System.out.println("hello,我是say");
//            }
//        });


        Map<String, String> map = new HashMap<>();
        map.put("user","123");
        map.clear();
        System.out.println("map = " + map);


    }

}
