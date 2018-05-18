package com.android.base_tools;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v4.view.ViewCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 机顶盒移动到边界后抖动效果工具类
 */
public class ShakeAnimateUtil {
    private static final int HORIZONTAL_FLAG = 0;
    private static final int VERTICAL_FLAG = 1;
    private static final int DURATION = 200;
    private static final int SHAKE_DURATION = 300;
    private ObjectAnimator translationAnimatorX;
    private ObjectAnimator translationAnimatorY;
    private View oldViewX, oldViewY;

    /**
     * 缩放动画
     */
    public static void scaleUp(View v, float scale, boolean isShrink) {
        if (isShrink) {
            AnimatorSet set = new AnimatorSet();
            set.playTogether(
                    ObjectAnimator.ofFloat(v, "scaleX", 1, scale + 0.1f),
                    ObjectAnimator.ofFloat(v, "scaleY", 1, scale + 0.1f),
                    ObjectAnimator.ofFloat(v, "scaleX", scale + 0.1f, scale),
                    ObjectAnimator.ofFloat(v, "scaleY", scale + 0.1f, scale)
            );
            set.setDuration(DURATION).start();
        } else {
            ViewCompat.animate(v)
                    .setDuration(DURATION)
                    .scaleX(scale)
                    .scaleY(scale)
                    .start();
        }
    }

    /**
     * 结束当前动画
     */
    public void completeOneShakeCycle() {
        if (translationAnimatorX != null && translationAnimatorX.isRunning()) {
            translationAnimatorX.setRepeatCount(0);
        }
        if (translationAnimatorY != null && translationAnimatorY.isRunning()) {
            translationAnimatorY.setRepeatCount(0);
        }
    }

    /**
     * 执行抖动动画，不掉停止，会一直抖动下去
     * @param newView
     * @param keyEvent
     * @param parent
     * @param shake
     */
    public void bindShakeAnimator(View newView, KeyEvent keyEvent, View parent, boolean shake) {
        if (newView != null && isNeedShake(newView, keyEvent, shake)) {  //判断是否可以抖动
            if (getDirectorFlag(keyEvent) == HORIZONTAL_FLAG) {  //抖动方向应为横向
                if (oldViewX == null || (oldViewX != null && oldViewX != newView)) {
                    setHorizontalShakeAnimator(newView);
                }
                startHorizontalShakeAnimator();
                oldViewX = newView;
            } else if (getDirectorFlag(keyEvent) == VERTICAL_FLAG) {  //纵向同上
                if (parent == null) {
                    if (oldViewY == null || (oldViewY != null && oldViewY != newView)) {
                        setVerticalShakeAnimator(newView);
                    }
                    oldViewY = newView;
                } else {
                    if (oldViewY == null || (oldViewY != null && oldViewY != parent)) {
                        setVerticalShakeAnimator(parent);
                    }
                    oldViewY = parent;
                }
                startVerticalShakeAnimator();

            }
        }
    }

    private boolean isNeedShake(View itemView, KeyEvent event, boolean shake) {
        if (shake) {
            return true;
        }
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                View viewLeft = itemView.focusSearch(View.FOCUS_LEFT);
                if (null == viewLeft || viewLeft == itemView) {
                    return true;
                } else
                    break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                View viewRight = itemView.focusSearch(View.FOCUS_RIGHT);
                if (null == viewRight || viewRight == itemView) {
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                View viewTop = itemView.focusSearch(View.FOCUS_UP);
                if (null == viewTop || viewTop == itemView) {
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                View viewBottom = itemView.focusSearch(View.FOCUS_DOWN);
                if (null == viewBottom || viewBottom == itemView) {
                    return true;
                }
                break;
        }
        return false;
    }

    private int getDirectorFlag(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                return HORIZONTAL_FLAG;
            case KeyEvent.KEYCODE_DPAD_UP:
            case KeyEvent.KEYCODE_DPAD_DOWN:
                return VERTICAL_FLAG;
        }
        return -1;
    }

    private void setHorizontalShakeAnimator(View newView) {
        translationAnimatorX = ObjectAnimator.ofFloat(newView, "translationX", 0f, 15f, 0f, -15f, 0f, 15f, 0f, -15f, 0f);
        translationAnimatorX.setInterpolator(new LinearInterpolator());
        translationAnimatorX.setRepeatCount(ValueAnimator.INFINITE);
        translationAnimatorX.setDuration(SHAKE_DURATION);
    }

    private void startHorizontalShakeAnimator() {
        if (translationAnimatorX != null && !translationAnimatorX.isRunning()) {
            endVerticalAnimator();
            translationAnimatorX.setRepeatCount(ValueAnimator.INFINITE);
            translationAnimatorX.start();
        }
    }

    private void setVerticalShakeAnimator(View newView) {
        translationAnimatorY = ObjectAnimator.ofFloat(newView, "translationY", 0f, 15f, 0f, -15f, 0f, 15f, 0f, -15f, 0f);
        translationAnimatorY.setInterpolator(new LinearInterpolator());
        translationAnimatorY.setRepeatCount(ValueAnimator.INFINITE);
        translationAnimatorY.setDuration(SHAKE_DURATION);
    }

    private void startVerticalShakeAnimator() {
        if (translationAnimatorY != null && !translationAnimatorY.isRunning()) {
            endHorizontalAnimator();
            translationAnimatorY.setRepeatCount(ValueAnimator.INFINITE);
            translationAnimatorY.start();
        }
    }

    private void endHorizontalAnimator() {
        if (translationAnimatorX != null) {
            translationAnimatorX.end();
        }
    }

    private void endVerticalAnimator() {
        if (translationAnimatorY != null) {
            translationAnimatorY.end();
        }
    }
}
