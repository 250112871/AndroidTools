package demo.base.android.com.weight.utils;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by FLB on 2017/7/24.
 *
 */

public class AnimationUtils {

    /**
     * 属性动画
     *
     * @param view 作用控件
     * @param a    初始值
     * @param b    过渡值
     * @param c    最终值
     */
    private static void zoom(View view, float a, float b, float c) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "scaleY", a, b, c);
        animator.setDuration(400);
        animator.start();
        ObjectAnimator animators = ObjectAnimator.ofFloat(view, "scaleX", a, b, c);
        animators.setDuration(400);
        animators.start();
    }

    /**
     * 带弹性的放大效果
     *
     * @param i 在原有基础上放大i个单位
     */
    public static void zoomIn(final View view, float i) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        int w = params.width;
        float j = (w + i) / w;
        float z = (w + i + i / 3) / w;
        zoom(view, 1.0f, z, j);
    }

    /**
     * 带弹性的缩小效果
     *
     * @param i 在原有基础上缩小i个单位
     */
    public static void zoomOn(final View view, float i) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        int w = params.width;
        float j = (w + i) / w;
        float z = (w - i / 3) / w;
        zoom(view, j, z, 1.0f);
    }
}
