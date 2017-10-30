package com.sychan.shaka.support.utils;

import java.util.regex.Pattern;

public class Passwd {
    //8~16位数字、字母
    private static String reg = "(?=^.{8,16}$)(?=(?:.*?\\d))(?=.*[a-z])(?=(?:.*?[A-Z]))";

    /**
     * 正则表达式匹配。
     *
     * @param reg 正则表达式
     * @param str 需要匹配的字符串
     * @return 如果匹配成功返回true，否则返回false。
     */
    public static boolean getBoolMatcher(String reg, String str) {
        return Pattern.compile(reg).matcher(str).matches();
    }

    public static boolean getBoolMatcher(String str) {
        return Pattern.compile(reg).matcher(str).matches();
    }

    public static void main(String[] args) {
//        System.out.println(getBoolMatcher("^\\d+|\\S{0,5}$", "123456"));
        System.out.println(getBoolMatcher(reg, "123456"));
    }

    /**
     * 这个正则要求密码长度最少12位，包含至少1个特殊字符，2个数字，2个大写字母和一些小写字母。
     (?=^.{12,25}$)(?=(?:.*?\d){2})(?=.*[a-z])(?=(?:.*?[A-Z]){2})(?=(?:.*?[!@#$%*()_+^&}{:;?.]){1})(?!.*\s)[0-9a-zA-Z!@#$%*()_+^&]*$

     分解：
     (?=^.{12,25}$) -- 密码长度12-25，自己改变数字可以调节
     (?=(?:.*?[!@#$%*()_+^&}{:;?.]){1}) -- 至少一个特殊字母，
     (?=(?:.*?\d){2}) -- 至少2个数字，
     (?=.*[a-z]) -- a-z的小写字母
     (?=(?:.*?[A-Z]){2}) -- 至少2个大写字母，
     */
}
