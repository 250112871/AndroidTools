package com.android.base_tools;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;

/**
 * 动画工具类
 */

public class AnimationUtils {
    private AnimationUtils() {
    }

    private static void zoom(View view, float a, float b) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "scaleY", a, b);
        animator.setDuration(200);
        animator.start();
        ObjectAnimator animators = ObjectAnimator.ofFloat(view, "scaleX", a, b);
        animators.setDuration(200);
        animators.start();
    }

    /**
     * 放大效果
     *
     * @param i 在原有基础上放大i个单位
     */
    public static void zoomIn(final View view, float i) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        int w = params.width;
        float j = (w + i) / w;
        zoom(view, 1.0f, j);
    }

    /**
     * 缩小效果
     * @param i 在原有基础上缩小i个单位
     */
    public static void zoomOn(final View view, float i) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        int w = params.width;
        float j = (w + i) / w;
        zoom(view, j, 1.0f);
    }
}
