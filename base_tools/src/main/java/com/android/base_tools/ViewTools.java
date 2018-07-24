package com.android.base_tools;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;


/**
 * @author FLB
 * 操作 View的工具类
 */

public class ViewTools {

    /**
     * 1. 让View在HorizontalScrollView 中居中显示,前scrollview android:layout_width="match_parent"
     */
    public static void moveHorizontal(final HorizontalScrollView scrollView, final View view) {
        if (scrollView != null) {
            scrollView.smoothScrollBy(getHorizontalScrollSize(scrollView, view), 0);
        }
    }

    /**
     * 2. ScrollView 中居中显示,前scrollview android:layout_height="match_parent"
     */
    public static void moveVertical(ScrollView scrollView, View view) {
        if (scrollView != null) {
            scrollView.smoothScrollBy(0, getVerticalScrollSize(scrollView, view));
        }
    }

    /**
     * 计算如果view 居中显示需要移动的距离
     */
    private static int getHorizontalScrollSize(final HorizontalScrollView scrollView, final View view) {
        int result = 0;
        if (scrollView != null && view != null) {
            Context context = scrollView.getContext();
            int width = view.getWidth();
            int middle = (getWindowInfo(context).widthPixels - width) / 2;
            result = -(middle - getLocationOnScreen(view)[0]);
        }
        return result;
    }

    private static int getVerticalScrollSize(final ScrollView scrollView, final View view) {
        int offset = 0;
        if (scrollView != null && view != null) {
            Context context = scrollView.getContext();
            int height = view.getHeight();
            int middle = (getWindowInfo(context).heightPixels - height) / 2;
            offset = -(middle - getLocationOnScreen(view)[1]);
        }
        return offset;
    }

    /**
     * 3 让View在HorizontalScrollView 中居中显示，处理左边留小边的情况
     *
     * @param fitValue  修正范围（左侧控件显示的宽小于此值时做修正，让其完全隐藏）
     * @param omitValue 忽略值（如果移动的距离小于此值则不移动）
     */
    public static void leftFitMove(HorizontalScrollView scrollView, final View focusView, int fitValue, int omitValue) {
        int offsetValue = getHorizontalScrollSize(scrollView, focusView);
        int scrollX = scrollView.getScrollX();
        int scroll = offsetValue + scrollX;
        int maxScrollSize = getMaxScrollSize(scrollView);
        int leftFitValue = 0;

        if (scroll > maxScrollSize) {
            scroll = maxScrollSize;
            offsetValue = maxScrollSize - scrollX;
        }
        View firstVisibleView = findViewByLocalXY(scrollView, scroll, 50);
        if (firstVisibleView != null) {
            View scrollViewContent = scrollView.getChildAt(0);
            ViewGroup parentView = (ViewGroup) firstVisibleView.getParent();
            int parentLeftValue = 0;
            while (parentView != null) {
                parentLeftValue += parentView.getLeft();
                if (parentView == scrollViewContent) {
                    break;
                } else {
                    parentView = (ViewGroup) parentView.getParent();
                    if (parentView == null) {
                        parentLeftValue = 0;
                        break;
                    }
                }
            }
            leftFitValue = parentLeftValue + firstVisibleView.getLeft() + firstVisibleView.getMeasuredWidth() - scroll;
        }
        if (leftFitValue < fitValue && leftFitValue > 0) {
            offsetValue += leftFitValue;
        }
        if (Math.abs(offsetValue) > omitValue) {
            scrollView.smoothScrollBy(offsetValue, 0);
        }
    }

    /**
     * 4. 返回 HorizontalScrollView 可以滑动的最大距离
     */
    private static int getMaxScrollSize(HorizontalScrollView scrollView) {
        int maxSize = 0;
        if (scrollView != null && scrollView.getChildCount() != 0) {
            maxSize = scrollView.getChildAt(0).getMeasuredWidth() - scrollView.getMeasuredWidth();
        }
        if (maxSize < 0) {
            maxSize = 0;
        }
        return maxSize;
    }


    /**
     * 5. 获取屏幕的信息
     */
    private static DisplayMetrics getWindowInfo(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    /**
     * 6. 根据控件获取它在屏幕中的宽高
     */
    private static int[] getLocationOnScreen(View view) {
        int[] arr = new int[2];
        view.getLocationOnScreen(arr);
        return arr;
    }

    private static int[] getLocationInWindow(View view) {
        int[] arr = new int[2];
        view.getLocationInWindow(arr);
        return arr;
    }

    /**
     * 7 根据ViewGroup 的内部坐标查找View
     */
    private static View findViewByLocalXY(ViewGroup viewGroup, int getLeft, int getTop) {
        View resultView = null;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childView = viewGroup.getChildAt(i);
            if (childView instanceof ViewGroup) {
                resultView = findViewByLocalXY((ViewGroup) childView, getLeft - childView.getLeft(), getTop - childView.getTop());
            } else if (isPointInView(childView, getLeft, getTop)) {
                resultView = childView;
                break;
            }
        }
        return resultView;
    }

    /**
     * 8 判断坐标是否在控件内
     * x,y 原点为 View 的原点
     */
    private static boolean isPointInView(View view, int x, int y) {
        int left = view.getLeft();
        int top = view.getTop();
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        return view.isClickable() && y >= top && y <= bottom && x >= left && x <= right;
    }

    private static boolean isClickInView(View view, int x, int y) {
        int[] arr = getLocationInWindow(view);
        int left = arr[0];
        int top = arr[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        return view.isClickable() && y >= top && y <= bottom && x >= left && x <= right;
    }
}
