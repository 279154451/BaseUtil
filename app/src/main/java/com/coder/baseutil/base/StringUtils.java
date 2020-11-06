package com.coder.baseutil.base;

import android.text.TextUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Horrarndoo on 2017/4/5.
 * 字符串工具类
 */
public class StringUtils {
    //用于匹配手机号码
    private final static String REGEX_MOBILEPHONE = "^0?1[3458]\\d{9}$";

    //用于匹配固定电话号码
    private final static String REGEX_FIXEDPHONE = "^(010|02\\d|0[3-9]\\d{2})?\\d{6,8}$";

    //用于获取固定电话中的区号
    private final static String REGEX_ZIPCODE = "^(010|02\\d|0[3-9]\\d{2})\\d{6,8}$";

    /**
     * 判断字符串是否有值，如果为null或者是空字符串或者只有空格或者为"null"字符串，则返回true，否则则返回false
     */
    private static boolean isEmpty(String value) {
        return !(value != null && !"".equalsIgnoreCase(value.trim())
                && !"null".equalsIgnoreCase(value.trim()));
    }

    /**
     * 判断字符串是否是邮箱
     *
     * @param email email
     * @return 字符串是否是邮箱
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(" +
                "([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 判断手机号字符串是否合法
     *
     * @param phoneNumber 手机号字符串
     * @return 手机号字符串是否合法
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
        String expression = "^1[3|4|5|7|8]\\d{9}$";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * 判断手机号字符串是否合法
     *
     * @param areaCode    区号
     * @param phoneNumber 手机号字符串
     * @return 手机号字符串是否合法
     */
    public static boolean isPhoneNumberValid(String areaCode, String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber)) {
            return false;
        }

        if (phoneNumber.length() < 5) {
            return false;
        }

        if (TextUtils.equals(areaCode, "+86") || TextUtils.equals(areaCode, "86")) {
            return isPhoneNumberValid(phoneNumber);
        }

        boolean isValid = false;
        String expression = "^[0-9]*$";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static String alertDelColon(String str) {
        if (isNullStr(str)) return "获取数据失败";
        String mStr = "";
        if (str.contains("：")) {
            str.replace("：", ":");
        }
        mStr = str.substring((str.lastIndexOf(":") + 1), str.length());
        return mStr;
    }

    /**
     * 判断字符串是否是手机号格式
     *
     * @param areaCode    区号
     * @param phoneNumber 手机号字符串
     * @return 字符串是否是手机号格式
     */
    public static boolean isPhoneFormat(String areaCode, String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber)) {
            return false;
        }

        if (phoneNumber.length() < 7) {
            return false;
        }

        boolean isValid = false;
        String expression = "^[0-9]*$";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * 判断字符串是否为纯数字
     *
     * @return 是否纯数字
     */
    public static boolean isNumber(String string) {
        Pattern p = Pattern.compile("^[0-9]*$");
        Matcher match = p.matcher(string);
        boolean flag = match.matches();
        return flag;
    }

    /**
     * 判断是不空字符串包括“null”
     */
    public static boolean isNullStr(String str) {
        if (TextUtils.isEmpty(str)) {
            return true;
        } else if ("null".equals(str.trim().toLowerCase())) {
            return true;
        } else if (str == null) {
            return true;
        }
        return false;
    }


    /**
     * 判断是否为中文
     */
    public static boolean isChineseCharacters(String str) {
        boolean flag = false;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
            Matcher match = p.matcher(ch + "");
            if (match.matches()) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 判断字符长度
     *
     * @param sourceStr
     * @return
     */
    public static int computeLength(String sourceStr) {
        int length = 0;
        for (int i = 0; i < sourceStr.length(); i++) {
            if (isChineseCharacters("" + sourceStr.charAt(i))) {
                length += 3;
            } else {
                length += 1;
            }
        }
        return length;
    }

    /**
     * 判断是否为数字
     *
     * @param string
     * @return
     */
    public static boolean isAllNumber(String string) {
        Pattern p = Pattern.compile("^[0-9]*$");
        Matcher match = p.matcher(string);
        boolean flag = match.matches();
        return flag;
    }

    /**
     * 判断是否为特殊字符
     *
     * @param mobiles
     * @return
     */
    public static boolean isTeSuChar(String mobiles) {
        String mS = mobiles;
        // 只允许字母和数字
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？ ]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(mobiles);
        return !mS.equals(m.replaceAll("").trim());
    }


    /**
     * 时间戳转日期
     *
     * @param ms
     * @return
     */
    public static String transForDate(String ms) {
        long times = 0;
        String str = "";
        if (!StringUtils.isNullStr(ms)) {
            times = Long.parseLong(ms);
        } else {
            times = 0;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (ms != null) {
            str = sdf.format(times);
        }
        return str;
    }

    /**
     * 判断文件是否存在
     */
    public static boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * 判断12个中文字符或24个英文字符
     */
    public static int strCOELength(String name) {
        int chinaCount = 0;
        Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
        int length = 0;
        int strLenth = 0;
        if (name != null) {
            strLenth = name.length();
            Matcher aMatcher = pattern.matcher(name);
            System.out.println("是否有中文：" + (aMatcher.find() ? "有" : "无"));

            char c[] = name.toCharArray();
            length = c.length;
            for (int i = 0; i < length; i++) {
                Matcher matcher = pattern.matcher(String.valueOf(c[i]));
                if (matcher.matches()) {
                    chinaCount++;
                    strLenth++;
                }
            }
        }
        return strLenth;
    }


    public static String getStrValue(String str) {
        if (isNullStr(str)) {
            return "";
        }
        return str;
    }

    public static Double getDoubleValue(Double value) {
        return value != null ? value : 0;
    }

    public static BigDecimal getBigDecimalValue(BigDecimal value) {
        return value != null ? value : BigDecimal.valueOf(0d);
    }

    public static String getDobuleString(Double d, String defaultStr) {
        DecimalFormat decimalFormat = new DecimalFormat("###0.00");//格式化设置
        return (d != null && d > 0) ? decimalFormat.format(d) : defaultStr;
    }

    public static Integer getIntegerValue(Integer value) {
        return value != null ? value : 0;
    }

    public static boolean getBooleanValue(Boolean value) {
        return value != null ? value : false;
    }


    /**
     * encoded in utf-8
     *
     * <pre>
     * utf8Encode(null) = null
     * utf8Encode("") = "";
     * utf8Encode("aa") = "aa";
     * utf8Encode("啊啊啊啊") = "%E5%95%8A%E5%95%8A%E5%95%8A%E5%95%8A";
     * </pre>
     */
    public static String utf8Encode(String str) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(
                        "UnsupportedEncodingException occurred. ", e);
            }
        }
        return str;
    }

    /**
     * 获取固定号码号码中的区号
     *
     * @param strNumber
     * @return
     */
    public static String getZipFromHomephone(String strNumber) {
        Pattern p = Pattern.compile(REGEX_ZIPCODE);
        Matcher matcher = p.matcher(strNumber);
        if (matcher.find()) {
            return matcher.group(1);
        }

        return "";
    }

    /**
     * 获取固定号码
     *
     * @param strNumber
     * @return
     */
    public static String getFixedHomePhone(String strNumber) {
        Pattern p = Pattern.compile(REGEX_FIXEDPHONE);
        Matcher matcher = p.matcher(strNumber);
        if (matcher.find()) {
            return matcher.group();
        }

        return "";
    }

    /**
     * 判断是否为固定电话号码
     *
     * @param number 固定电话号码
     * @return
     */
    public static boolean isFixedPhone(String number) {
        Pattern p = Pattern.compile(REGEX_FIXEDPHONE);
        Matcher match = p.matcher(number);
        return match.matches();
    }

    /**
     * 判断是否为手机号码
     *
     * @param number 手机号码
     * @return
     */
    public static boolean isCellPhone(String number) {
        Pattern p = Pattern.compile(REGEX_MOBILEPHONE);
        Matcher match = p.matcher(number);
        return match.matches();
    }

    public static String parseCallNum(String msg) {
        Pattern p = Pattern.compile("\\d+");
        String parse = "";
        Matcher m = p.matcher(msg);
        if (m.find()) {
            int index = msg.indexOf(m.group());
            parse = msg.substring(index, msg.length());
        }
        return parse;
    }

    /**
     * 字符串换成UTF-8 * *
     * <p>
     *  @paramstr *
     * <p>
     *  @return
     */

    public static String stringToUtf8(String str) {

        String result = null;
        try {
            result = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }
        return result;

    }

    /**
     * utf-8换成字符串 * *
     * <p>
     *  @paramstr * 
     *
     * @return
     */
    public static String utf8ToString(String str) {
        String result = null;

        try {
            result = URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {

        }

        return result;

    }

}