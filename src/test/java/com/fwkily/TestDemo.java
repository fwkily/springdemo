package com.fwkily;

import com.fwkily.controller.UserController;
import com.fwkily.service.OrderService;
import com.google.common.collect.Lists;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * @Classname com.fwkily.Test
 * @Description TODO
 * @Date 2022/9/26 9:31 AM
 * @Created by fuwk
 */
//@SpringBootTest
public class TestDemo {

    @Test
    public void test(){
        System.out.println(123);
    }


    @Test
    public void testCascade2007() {
        // 查询所有的省名称
        List<String> provNameList = new ArrayList<String>();
        provNameList.add("安徽省");
        provNameList.add("浙江省");

        // 整理数据，放入一个Map中，mapkey存放父地点，value 存放该地点下的子区域
        Map<String, List<String>> siteMap = new HashMap<String, List<String>>();
        siteMap.put("浙江省", Lists.newArrayList("杭州市", "宁波市"));
        siteMap.put("安徽省", Lists.newArrayList("芜湖市", "滁州市"));
        siteMap.put("芜湖市", Lists.newArrayList("戈江区", "三山区"));
        siteMap.put("滁州市", Lists.newArrayList("来安县", "凤阳县"));

        // 创建一个excel
        Workbook book = new XSSFWorkbook();

        // 创建需要用户填写的数据页
        // 设计表头
        Sheet sheet1 = book.createSheet("sheet1");
        Row row0 = sheet1.createRow(0);
        row0.createCell(0).setCellValue("省");
        row0.createCell(1).setCellValue("市");
        row0.createCell(2).setCellValue("区");

        //创建一个专门用来存放地区信息的隐藏sheet页
        //因此也不能在现实页之前创建，否则无法隐藏。
        Sheet hideSheet = book.createSheet("site");
        book.setSheetHidden(book.getSheetIndex(hideSheet), false);

        int rowId = 0;
        // 设置第一行，存省的信息
        Row proviRow = hideSheet.createRow(rowId++);
        proviRow.createCell(0).setCellValue("省列表");
        for(int i = 0; i < provNameList.size(); i ++){
            Cell proviCell = proviRow.createCell(i + 1);
            proviCell.setCellValue(provNameList.get(i));
        }
        // 将具体的数据写入到每一行中，行开头为父级区域，后面是子区域。
        Iterator<String> keyIterator = siteMap.keySet().iterator();
        while(keyIterator.hasNext()){
            String key = keyIterator.next();
            List<String> son = siteMap.get(key);

            Row row = hideSheet.createRow(rowId++);
            row.createCell(0).setCellValue(key);
            for(int i = 0; i < son.size(); i ++){
                Cell cell = row.createCell(i + 1);
                cell.setCellValue(son.get(i));
            }

            // 添加名称管理器
            String range = getRange(1, rowId, son.size());
            Name name = book.createName();
            name.setNameName(key);
            String formula = "site!" + range;
            name.setRefersToFormula(formula);
        }

        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet)sheet1);

        // 省规则
        DataValidationConstraint provConstraint = dvHelper.createExplicitListConstraint(provNameList.toArray(new String[]{}));
        CellRangeAddressList provRangeAddressList = new CellRangeAddressList(1, 10, 0, 0);
        DataValidation provinceDataValidation = dvHelper.createValidation(provConstraint, provRangeAddressList);
        provinceDataValidation.createErrorBox("error", "请选择正确的省份");
        provinceDataValidation.setShowErrorBox(true);
        provinceDataValidation.setSuppressDropDownArrow(true);
        sheet1.addValidationData(provinceDataValidation);


        // 市以规则，此处仅作一个示例
        // "INDIRECT($A$" + 2 + ")" 表示规则数据会从名称管理器中获取key与单元格 A2 值相同的数据，如果A2是浙江省，那么此处就是
        // 浙江省下的区域信息。
        DataValidationConstraint formula = dvHelper.createFormulaListConstraint("INDIRECT($A$" + 2 + ")");
        CellRangeAddressList rangeAddressList = new CellRangeAddressList(1,10,1,1);
        DataValidation cacse = dvHelper.createValidation(formula, rangeAddressList);
        cacse.createErrorBox("error", "请选择正确的市");
        sheet1.addValidationData(cacse);

        // 区规则
        formula = dvHelper.createFormulaListConstraint("INDIRECT($B$" + 2 + ")");
        rangeAddressList = new CellRangeAddressList(1,10,2,2);
        cacse = dvHelper.createValidation(formula, rangeAddressList);
        cacse.createErrorBox("error", "请选择正确的区");
        sheet1.addValidationData(cacse);


        FileOutputStream os = null;
        try {
            os = new FileOutputStream("/Users/mac/Downloads/test33.xlsx");
            book.write(os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(os);
        }
    }

    /**
     *
     * @param offset 偏移量，如果给0，表示从A列开始，1，就是从B列
     * @param rowId 第几行
     * @param colCount 一共多少列
     * @return 如果给入参 1,1,10. 表示从B1-K1。最终返回 $B$1:$K$1
     *
     * @author denggonghai 2016年8月31日 下午5:17:49
     */
    public String getRange(int offset, int rowId, int colCount) {
        char start = (char)('A' + offset);
        if (colCount <= 25) {
            char end = (char)(start + colCount - 1);
            return "$" + start + "$" + rowId + ":$" + end + "$" + rowId;
        } else {
            char endPrefix = 'A';
            char endSuffix = 'A';
            if ((colCount - 25) / 26 == 0 || colCount == 51) {// 26-51之间，包括边界（仅两次字母表计算）
                if ((colCount - 25) % 26 == 0) {// 边界值
                    endSuffix = (char)('A' + 25);
                } else {
                    endSuffix = (char)('A' + (colCount - 25) % 26 - 1);
                }
            } else {// 51以上
                if ((colCount - 25) % 26 == 0) {
                    endSuffix = (char)('A' + 25);
                    endPrefix = (char)(endPrefix + (colCount - 25) / 26 - 1);
                } else {
                    endSuffix = (char)('A' + (colCount - 25) % 26 - 1);
                    endPrefix = (char)(endPrefix + (colCount - 25) / 26);
                }
            }
            return "$" + start + "$" + rowId + ":$" + endPrefix + endSuffix + "$" + rowId;
        }
    }

    @Test
    public void t(){
        System.out.println("getAllClassByInterface(OrderService.class) = " + getAllClassByInterface(OrderService.class));
    }


    public static List<Class> getAllClassByInterface(Class c) {
        // 返回结果
        List<Class> returnClassList = new ArrayList<Class>();
        // 如果不是一个接口，则不做处理
        if (c.isInterface()) {
            // 获得当前的包名
            String packageName = c.getPackage().getName();
            try {
                // 获得当前包下以及子包下的所有类
                List<Class> allClass = getClasses(packageName);
                // 判断是否是同一个接口
                for (int i = 0; i < allClass.size(); i++) {
                    // 判断是不是一个接口
                    if (c.isAssignableFrom(allClass.get(i))) {
                        // 本身不加进去
                        if (!c.equals(allClass.get(i))) {
                            returnClassList.add(allClass.get(i));
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return returnClassList;
    }

    // 从一个包中查找出所有的类，在jar包中不能查找
    private static List<Class> getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread()
                .getContextClassLoader();
        // 用'/'代替'.'路径
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }

    private static List<Class> findClasses(File directory, String packageName)
            throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file,
                        packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                // 去掉'.class'
                classes.add(Class.forName(packageName
                        + '.'
                        + file.getName().substring(0,
                        file.getName().length() - 6)));

            }
        }
        return classes;
    }








}
