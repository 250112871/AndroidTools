package demo.base.android.com.weight.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by fanlongbo on 2017/8/2.
 * 操作 View的工具类
 */

public class ViewUtils {
    private ViewUtils() {
    }

    /**
     * 获取屏幕的高
     */
    public static int getWindowHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 根据控件获取它在屏幕中的位置【宽，高】
     */
    public static int[] getDisplayHeight(View view) {
        int[] arr = new int[2];
        view.getLocationInWindow(arr);
        return arr;
    }

    /*
     * 为两个控件相互设置左右焦点
     */
    public static void setLeftRightFause(View leftView, View rightView) {
        leftView.setNextFocusRightId(rightView.getId());
        rightView.setNextFocusLeftId(leftView.getId());
    }
}
