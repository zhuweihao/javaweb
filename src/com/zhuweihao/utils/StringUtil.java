package com.zhuweihao.utils;

/**
 * @Author zhuweihao
 * @Date 2023/2/16 16:08
 * @Description com.zhuweihao.utils
 */
public class StringUtil {
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    public static boolean idNotEmpty(String str) {
        return !isEmpty(str);
    }
}
