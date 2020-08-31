package com.xcmis.framework.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 功能
 *
 * @author
 * @see
 */
public class StringUtils {
    public static String strNullToEmpty(String str){
        if(str == null)
            return "";
        else
            return str;
    }


    public static String stateCodeStr(String str){
        DecimalFormat decimalFormat = new DecimalFormat("000");
        int i = Integer.parseInt(str) + 1;
        String k = decimalFormat.format(i);
        return k;
    }

    public static String returnStateCodeStr(String str){
        DecimalFormat decimalFormat = new DecimalFormat("000");
        int i = Integer.parseInt(str) - 1;
        String k = decimalFormat.format(i);
        return k;
    }


    public static String toMoneyStr(String str){
        if(str == null || str.equals("") || str.equals("0") || str.equals("0.0")){
            return "";
        }
        if(str.indexOf(".") == 0){
            return "0" + str;
        }

        java.text.DecimalFormat df = new java.text.DecimalFormat("########.00");

        str = df.format(Double.valueOf(str));
        return str;
    }


    public static String toMoneyStr1(String str){
        if(str == null || str.equals("") || str.equals("0") || str.equals("0.0")){
            return "";
        }
        if(str.indexOf(".") == 0){
            return "0" + str;
        }

        DecimalFormat df = new DecimalFormat("###,###,##0.00");
        BigDecimal bd = new BigDecimal(Double.valueOf(str));
        str = df.format(bd.setScale(2, BigDecimal.ROUND_DOWN));

        return str;
    }


    public static String getReqBillNo(String maxReqBillNo, String year, String region){
        if(maxReqBillNo == null){
            return region + year.substring(2,4) + "100000000011";
        }else {
            if (maxReqBillNo.equals("")) {
                return region + year.substring(2,4) + "100000000011";
            } else {
                String tmpString = new DecimalFormat("00000000").format(Integer.parseInt(maxReqBillNo.substring(10, maxReqBillNo.length() - 1)) + 1);
                tmpString = region + year.substring(2,4) + "100" + tmpString + "1";
                return tmpString;
            }
        }
    }
}
