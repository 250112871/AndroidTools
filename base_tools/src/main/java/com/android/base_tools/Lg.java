package com.android.base_tools;

import android.util.Log;

/**
 * @author Created by 25011 on 2018/7/19.
 * Log 管理类
 */
public class Lg {
    private Lg() {
    }

    public static boolean isDebug = true;
    private static String tag = "lf-->";

    public static void setTag(String tag) {
        Lg.tag = tag;
    }

    public static void i(String tag, String msg) {
        if (isDebug) {
            Log.i(tag, msg);
        }
    }

    public static void i(String msg) {
        i(tag, msg);
    }


    public static void ii(String msg) {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();

        int methodCount = 1;
        int stackOffset = getStackOffset(trace);

        if (methodCount + stackOffset > trace.length) {
            methodCount = trace.length - stackOffset - 1;
        }

        for (int i = methodCount; i > 0; i--) {
            int stackIndex = i + stackOffset;
            if (stackIndex >= trace.length) {
                continue;
            }
            StackTraceElement element = trace[stackIndex];

            StringBuilder builder = new StringBuilder();
            builder.append(element.getMethodName())
                    .append(" ")
                    .append(" (")
                    .append(element.getFileName())
                    .append(":")
                    .append(element.getLineNumber())
                    .append(")")
                    .append(" \n ----------->")
                    .append(msg).append("<-------------\n\n ");

            Log.i(getSimpleClassName(element.getClassName()), builder.toString());

        }
    }

    private static int getStackOffset(StackTraceElement[] trace) {
        for (int i = 2; i < trace.length; i++) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if (!name.equals(Lg.class.getName())) {
                return --i;
            }
        }
        return -1;
    }

    private static String getSimpleClassName(String name) {
        int lastIndex = name.lastIndexOf(".");
        return name.substring(lastIndex + 1);
    }
}
