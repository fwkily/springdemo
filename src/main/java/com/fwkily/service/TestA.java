package com.fwkily.service;

public class TestA implements Son,Person{

    @Override
    public void sayHello() {
        System.out.println("hello,我是TestA");
    }

    @Override
    public void say() {

    }

    public void take(Son son){
        son.sayHello();
    }

}
