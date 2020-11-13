package com.lengyue.frame_databinding.utils;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 字符串工具类
 *
 * @author linbin
 */
public class StringUtil {

    /**
     * 数字格式化
     *
     * @param num 待格式化数字
     * @param group 是否","分组
     * @param digitsNum 小数位数
     * @return 格式化后字符串
     */
    public static String getNumber(double num, boolean group, int digitsNum) {
        String result = "0";
        try {
            NumberFormat format = NumberFormat.getNumberInstance();
            format.setGroupingUsed(group);
            format.setMaximumFractionDigits(digitsNum);
            result = format.format(num);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return result;
    }

    /**
     * 时间转字符串
     *
     * @param date 时间
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String getDateString(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    /**
     * 字符串转时间
     *
     * @param date 时间字符串
     * @param format 时间格式
     * @return 时间
     */
    public static Date getDate(String date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        try {
            return simpleDateFormat.parse(date);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }


}
