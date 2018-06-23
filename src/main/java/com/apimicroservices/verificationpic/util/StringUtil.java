package com.apimicroservices.verificationpic.util;

public class StringUtil {
    public static boolean isEmpty(String s) {
        if (s == null || "".equals(s)) {
            return true;
        } else {
            return false;
        }
    }
}
