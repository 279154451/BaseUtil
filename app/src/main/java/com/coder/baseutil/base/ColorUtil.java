package com.coder.baseutil.base;

import android.text.TextUtils;

/**
 * 创建时间：2020/5/21
 * 创建人：singleCode
 * 功能描述：
 **/
public class ColorUtil {
    /**
     *从后台获取颜色值再转换成渐变的颜色数组
     * @param startColor 颜色起始值
     * @param endColor  颜色结束值
     * @return
     */
    public static int[] getColors(String startColor,String endColor) {
        startColor = StringUtils.isNullStr(startColor) ? "#ff6fcd9d" :startColor;
        endColor = StringUtils.isNullStr(endColor) ? "#ff4eb9a0" : endColor;
        startColor = startColor.split("#").length > 1 ? startColor.split("#")[1] : startColor.split("#")[0];
        endColor = endColor.split("#").length > 1 ? endColor.split("#")[1] : endColor.split("#")[0];
        int start = getColor(startColor);
        int end = getColor(endColor);
        return new int[]{start, end};
    }
    private static int getColor(String str) {
        int length = str.length();
        if(length<=7){
            str = "ff"+str;
        }
        byte[] colorbyte = hexStringToBytes(str);
        return byteArrayToInt(colorbyte);
    }


    /*
     * 将16进制的字符串装换为对应的byte数组，例如"A5000C5A81000000000000000000010E90AA" 转换为对应的数组形式
     *
     * @param hexString
     * @return 转换后的数组
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (TextUtils.isEmpty(hexString)) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    //byte 数组与 int 的相互转换
    private static int byteArrayToInt(byte[] b) {
        if(b.length<4){//避免下表越界
            return  0xff6fcd9d;
        }
        return b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }
}
