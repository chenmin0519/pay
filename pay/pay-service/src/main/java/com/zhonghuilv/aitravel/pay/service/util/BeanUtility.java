package com.zhonghuilv.aitravel.pay.service.util;

import com.zhonghuilv.aitravel.pay.intf.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
public class BeanUtility {

    private BeanUtility() {
        throw new IllegalStateException("Utility class");
    }


    /**
     * 将map中的下划线key转换成驼峰然后赋值给javabean
     *
     * @return
     */
    public static <T> T map2Bean(Map<String,?> map, Class<T> beanClass) {
        try {
            T t = beanClass.newInstance();
            map.forEach((key, value) -> {
                String fieldName = StringUtils.convertUnderscoreNameToPropertyName(key);

                Field field = null;
                try {
                    field = beanClass.getDeclaredField(fieldName);
                    if (Modifier.isStatic(field.getModifiers())) {
                        return;
                    }
                    if (!Modifier.isPublic(field.getModifiers())) {
                        field.setAccessible(true);
                    }
                    field.set(t, value);
                } catch (Exception e) {
                    //skip
                }

            });
            return t;
        } catch (Exception e) {
            //skip
        }
        return null;
    }

}
