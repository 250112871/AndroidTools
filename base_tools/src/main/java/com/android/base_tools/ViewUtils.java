package com.android.base_tools;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

/**
 * @author Created by 25011 on 2018/7/19.
 */
public class ViewUtils {
    private ViewUtils() {
    }

    /**
     * 给TextView 设置内容非空检测
     */
    public static void setText(@NonNull TextView tv, String msg) {
        setText(tv, msg, "");
    }

    /**
     * 给TextView 设置内容非空检测,如果为空设置默认值
     */
    public static void setText(@NonNull TextView tv, String msg, String defaultMsg) {
        if (!TextUtils.isEmpty(msg)) {
            tv.setText(msg);
        } else if (defaultMsg != null) {
            tv.setText(defaultMsg);
        }
    }

    public static int dp2px(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

}
