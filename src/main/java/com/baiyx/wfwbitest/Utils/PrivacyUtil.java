package com.baiyx.wfwbitest.Utils;/**
 * @Author: 白宇鑫
 * @Date: 2023-1-12 10:36
 * @Description: 说明
 */

    public class PrivacyUtil {

    /**
     * 隐藏手机号中间四位
     */
    public static String hidePhone(String phone) {
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 隐藏邮箱
     */
    public static String hideEmail(String email) {
        return email.replaceAll("(\\w?)(\\w+)(\\w)(@\\w+\\.[a-z]+(\\.[a-z]+)?)", "$1****$3$4");
    }

    /**
     * 隐藏身份证
     */
    public static String hideIDCard(String idCard) {
        return idCard.replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1*****$2");
    }

    /**
     * 【中文姓名】只显示第一个汉字，其他隐藏为星号，比如：任**
     */
    public static String hideChineseName(String chineseName) {
        if (chineseName == null) {
            return null;
        }
        return desValue(chineseName, 1, 0, "*");
    }

    // 重载: 兼容两个字和多个字的名字,保留首尾两个字,中间全部脱敏
    public static String hideChineseName(String chineseName, String maskStr) {
        if (chineseName == null) {
            return null;
        }
        return desValue(chineseName, 1, 0, maskStr);
    }

//    /**
//     * 【身份证号】显示前4位, 后2位
//     */
//    public static String hideIdCard(String idCard) {
//        return desValue(idCard, 4, 2, "*");
//    }

//    /**
//     * 【手机号码】前三位，后四位，其他隐藏。
//     */
//    public static String hidePhone(String phone) {
//        return desValue(phone, 3, 4, "*");
//    }

    /**
     * 对字符串进行脱敏操作
     * @param origin          原始字符串
     * @param prefixNoMaskLen 左侧需要保留几位明文字段
     * @param suffixNoMaskLen 右侧需要保留几位明文字段
     * @param maskStr         用于遮罩的字符串, 如'*'
     * @return 脱敏后结果
     */
    public static String desValue(String origin, int prefixNoMaskLen, int suffixNoMaskLen, String maskStr) {
        if (origin == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        if(origin.length() == 2 && prefixNoMaskLen == 1 && suffixNoMaskLen == 1){
            return hideChineseName(origin,maskStr);
        }else{
            for (int i = 0, n = origin.length(); i < n; i++) {
                if (i < prefixNoMaskLen) {
                    sb.append(origin.charAt(i));
                    continue;
                }
                if (i > (n - suffixNoMaskLen - 1)) {
                    sb.append(origin.charAt(i));
                    continue;
                }
                sb.append(maskStr);
            }
        }
        return sb.toString();
    }
}