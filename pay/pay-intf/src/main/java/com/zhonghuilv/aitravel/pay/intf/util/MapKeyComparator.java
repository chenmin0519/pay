package com.zhonghuilv.aitravel.pay.intf.util;

import java.util.Comparator;

public class MapKeyComparator implements Comparator<String> {

    public int compare(String arg0, String arg1) {
        return arg0.compareTo(arg1);
    }
}
