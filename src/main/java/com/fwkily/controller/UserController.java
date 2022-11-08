package com.fwkily.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fwkily.mapper.UserMapper;
import com.fwkily.mapper.po.UserPo;
import com.fwkily.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class UserController {

//    @Autowired
//    UserService userService;

    @Autowired
    UserMapper userMapper;

    @GetMapping(value = "/test/{id}")
    public void test1(@PathVariable("id") String id){
        userMapper.deleteById(id);
    }

    @GetMapping(value = "/test2")
    public void test2(String ids){
        userMapper.deleteBatchIds(Arrays.asList(ids.split(",")));
    }

    @PostMapping(value = "update")
    public String update(@RequestBody UserPo user){
        userMapper.updateById(user);
        return "success";
    }

    @PostMapping(value = "insert")
    public String insert(@RequestBody UserPo user){
        userMapper.insert(user);
        return "success";
    }

    @GetMapping(value = "select")
    public UserPo select(@RequestParam Long id){
        UserPo userPo = userMapper.selectById(id);
        return userPo;
    }

    @GetMapping(value = "page")
    public String page(@RequestParam Integer age,@RequestParam Integer pageNo,@RequestParam Integer pageSize){
        IPage<UserPo> page = new Page<>(pageNo, pageSize);

        QueryWrapper<UserPo> wrapper = new QueryWrapper<>();
        wrapper.eq("age",age);
        IPage<UserPo> userPoIPage = userMapper.selectPage(page,wrapper);
        return userPoIPage.toString();
    }

    @PostMapping(value = "/selectByNames")
    public String selectByNames(@RequestBody List<String> names){
        List<UserPo> userPos = userMapper.selectByNames(names);
        return JSON.toJSONString(userPos);
    }

    @GetMapping(value = "/testException")
    public String testException(int s){
        try {
            int a = 10 / s;
            System.out.println("执行成功！");
        }catch (ArithmeticException e){
            System.out.println("我是小异常");
            throw e;
        }catch (RuntimeException e){
            System.out.println("我是RuntimeException");
            throw e;
        }catch (Exception e){
            System.out.println("我是exception");
            throw e;
        } catch (Throwable e){
            System.out.println("我是大异常");
            e.printStackTrace();
        }finally {
            System.out.println("finally执行！");
            return "执行finally";
        }
    }

//    @GetMapping(value = "/testClass")
//    public String testException(){
//        List<Class> classes = getAllClassByInterface(OrderService.class);
//        System.out.println(classes.toString());
//        return classes.toString();
//    }
//
//    public static List<Class> getAllClassByInterface(Class c) {
//        // 返回结果
//        List<Class> returnClassList = new ArrayList<Class>();
//        // 如果不是一个接口，则不做处理
//        if (c.isInterface()) {
//            // 获得当前的包名
//            String packageName = c.getPackage().getName();
//            try {
//                // 获得当前包下以及子包下的所有类
//                List<Class> allClass = getClasses(packageName);
//                // 判断是否是同一个接口
//                for (int i = 0; i < allClass.size(); i++) {
//                    // 判断是不是一个接口
//                    if (c.isAssignableFrom(allClass.get(i))) {
//                        // 本身不加进去
//                        if (!c.equals(allClass.get(i))) {
//                            returnClassList.add(allClass.get(i));
//                        }
//                    }
//                }
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//        return returnClassList;
//    }
//
//    // 从一个包中查找出所有的类，在jar包中不能查找
//    private static List<Class> getClasses(String packageName)
//            throws ClassNotFoundException, IOException {
//        ClassLoader classLoader = Thread.currentThread()
//                .getContextClassLoader();
//        // 用'/'代替'.'路径
//        String path = packageName.replace('.', '/');
//        Enumeration<URL> resources = classLoader.getResources(path);
//        List<File> dirs = new ArrayList<File>();
//        while (resources.hasMoreElements()) {
//            URL resource = resources.nextElement();
//            dirs.add(new File(resource.getFile()));
//        }
//        log.info("dirs:{}",dirs);
//        ArrayList<Class> classes = new ArrayList<Class>();
//        for (File directory : dirs) {
//            classes.addAll(findClasses(directory, packageName));
//        }
//        return classes;
//    }
//
//    private static List<Class> findClasses(File directory, String packageName)
//            throws ClassNotFoundException {
//        List<Class> classes = new ArrayList<Class>();
//        if (!directory.exists()) {
//            return classes;
//        }
//        File[] files = directory.listFiles();
//        log.info("files:{}",files);
//        for (File file : files) {
//            if (file.isDirectory()) {
//                assert !file.getName().contains(".");
//                classes.addAll(findClasses(file,
//                        packageName + "." + file.getName()));
//            } else if (file.getName().endsWith(".class")) {
//                // 去掉'.class'
//                classes.add(Class.forName(packageName
//                        + '.'
//                        + file.getName().substring(0,
//                        file.getName().length() - 6)));
//
//            }
//        }
//        return classes;
//    }

    @GetMapping(value = "/testClass")
    public String testException(){
        ArrayList<String> list = new ArrayList<>();
        List<String> classNameFrom = getClassNameFrom("SpringDemo-1.0-SNAPSHOT.jar");
        log.info("classNameFrom:{}",classNameFrom);
        classNameFrom.forEach(it -> {
            if(isChildClass(it, OrderService.class)){
                list.add(it);
            }
        });
        log.info("result:{}",list);
        return list.toString();
    }



    /**
     * 从jar包读取所有的class文件名
     */
    private static List<String> getClassNameFrom(String jarName) {
        List<String> fileList = new ArrayList<String>();
        try {
            JarFile jarFile = new JarFile(new File(jarName));
            Enumeration<JarEntry> en = jarFile.entries(); // 枚举获得JAR文件内的实体,即相对路径
            while (en.hasMoreElements()) {
                String name1 = en.nextElement().getName();
                if (!name1.endsWith(".class")) {//不是class文件
                    continue;
                }
                String name2 = name1.substring(0, name1.lastIndexOf(".class"));
                String name3 = name2.replaceAll("/", ".");
//                if(name3.contains("BOOT-INF.classes.")){
//                    String replace = name3.replace("BOOT-INF.classes.", "");
//                    fileList.add(replace);
//                }else {
                    fileList.add(name3);
//                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileList;
    }

    /**
     * 判断一个类是否继承某个父类或实现某个接口
     */
    public static boolean isChildClass(String className, Class parentClazz) {
        if (className == null) return false;

        Class clazz = null;
        try {
            clazz = Class.forName(className);
            if (Modifier.isAbstract(clazz.getModifiers())) {//抽象类忽略
                return false;
            }
            if (Modifier.isInterface(clazz.getModifiers())) {//接口忽略
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return parentClazz.isAssignableFrom(clazz);

    }

}
