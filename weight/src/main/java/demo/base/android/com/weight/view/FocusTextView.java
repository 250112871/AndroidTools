package demo.base.android.com.weight.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by fanlongbo on 2017/8/3.
 * 默认始终获取焦点的TextView  方便实现跑马灯效果。
 */

public class FocusTextView extends android.support.v7.widget.AppCompatTextView
{
    public FocusTextView(Context context) {
        super(context);
    }

    public FocusTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FocusTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
