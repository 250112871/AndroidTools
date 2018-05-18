package com.android.base_tools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtil {

    /**
     * 获取机顶盒系统属性
     * @param properName 属性名称
     */
    public static String getReflectValue(String properName) {
        String resValue = null;
        try {
            Class cls = Class.forName("android.os.SystemProperties");
            Object obj = cls.newInstance();
            Class[] param = new Class[1];
            param[0] = String.class;
            Method med = cls.getMethod("get", param);
            Object o = med.invoke(obj, properName);
            resValue = String.valueOf(o);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resValue;
    }
}