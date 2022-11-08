package com.fwkily.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegexp2 {

    public static void main(String[] args) {
        String a = "42342kh34i2h34i234sefase3swe";
//        String reg = "\\d\\d\\d";
        String reg = "[A-Z]";
        Pattern pattern = Pattern.compile(reg);

        Matcher matcher = pattern.matcher(a);
        while (matcher.find()){
            System.out.println("找到" + matcher.group(0));
        }

    }

}
