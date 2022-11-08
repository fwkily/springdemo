package com.fwkily.controller;

import com.fwkily.mapper.MytableMapper;
import com.fwkily.mapper.po.MytablePo;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


@RestController
//@Scope("prototype")
public class MytableController {

//    public int i = 1;
//    public static int j = 1;

    public String s1 = "a";
    public static String s2 = "b";

    @Autowired
    private MytableMapper  mytableMapper;

    @GetMapping(value = "/mytable/test")
    public String getContent(@RequestParam String id) throws IOException {
        MytablePo mytablePo = mytableMapper.selectById(id);
        String content = mytablePo.getContent();
        ModelData modelData = new ModelData("11噢噢噢噢", "222哈哈", 3);
        String basePath = "src/main/resources";
        File file = new File(basePath + "/pom.xml");
        System.out.println("file.getCanonicalFile() = " + file.getCanonicalPath());
        if(!file.exists()){
            file.createNewFile();
        }
        try(FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(content);
            fileWriter.flush();
        } catch (IOException e) {

        }
        try(FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(content);
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
            configuration.setDefaultEncoding("UTF-8");
            configuration.setClassForTemplateLoading(MytableController.class,"/");
//            configuration.setDirectoryForTemplateLoading(new File(basePath));
            configuration.getTemplate("pom.xml").process(modelData,fileWriter);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
        return content;
    }

    @GetMapping("/test1")
    public void test1(){
        s1 = s1+"实例";
        s2 = s2+"静态";
        System.out.println("实例属性：" + s1);
        System.out.println("静态属性：" + s2);
    }

}
