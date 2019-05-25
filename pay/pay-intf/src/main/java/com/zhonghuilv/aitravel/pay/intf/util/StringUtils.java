package com.zhonghuilv.aitravel.pay.intf.util;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
public class StringUtils {

    /**
     * Convert a column name with underscores to the corresponding property name using "camel case".  A name
     * like "customer_number" would match a "customerNumber" property name.
     *
     * @param underscoreName the column name to be converted
     * @return the name using "camel case"
     */
    public static String convertUnderscoreNameToPropertyName(String underscoreName) {
        StringBuilder result = new StringBuilder();
        boolean nextIsUpper = false;
        if (underscoreName != null && underscoreName.length() > 0) {
            if (underscoreName.length() > 1 && underscoreName.substring(1, 2).equals("_")) {
                result.append(underscoreName.substring(0, 1).toUpperCase());
            } else {
                result.append(underscoreName.substring(0, 1).toLowerCase());
            }
            for (int i = 1; i < underscoreName.length(); i++) {
                String s = underscoreName.substring(i, i + 1);
                if (s.equals("_")) {
                    nextIsUpper = true;
                } else {
                    if (nextIsUpper) {
                        result.append(s.toUpperCase());
                        nextIsUpper = false;
                    } else {
                        result.append(s.toLowerCase());
                    }
                }
            }
        }
        return result.toString();
    }

}
