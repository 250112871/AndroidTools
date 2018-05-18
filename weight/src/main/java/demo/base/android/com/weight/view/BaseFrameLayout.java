package demo.base.android.com.weight.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import demo.base.android.com.weight.utils.AnimationUtils;


/**
 * Created by fanlongbo on 2017/7/28.
 * 继承此控件后,可实现如下功能
 * 1 setNextActivity(Intent intent) 实现此方法点击可去传入的intent页面.
 * 2 当获取焦点后,此控件自动放大,如果需要改变动画 可复写zoomIn(),zoomOn() 方法
 * 3 此控件获取焦点后会自动增加背景框,图片通过 selectBackground 设置
 */

public class BaseFrameLayout extends FrameLayout {
    private static final String TAG = "BaseRelativeLayout";
    private Context mContext;
    private Intent nextActivity;
    int selectBackground = 0;

    public BaseFrameLayout(Context context) {
        super(context);
        initView(context);
    }

    public BaseFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BaseFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        setFocusableInTouchMode(true);
        setFocusable(true);
        setClickable(true);
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    view.bringToFront();
                    zoomIn(view);
                    setBackgroundResource(selectBackground);
                } else {
                    zoomOn(view);
                    setBackgroundResource(0);
                }
            }
        });
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nextActivity != null) {
                    mContext.startActivity(nextActivity);
                } else {
                    Log.i(TAG, "没配制下页数据");
                }
            }
        });
    }

    /**
     * 点击后进入下一个页面
     */
    public void setNextActivity(Intent nextActivity) {
        this.nextActivity = nextActivity;
    }

    /**
     * 放大动画
     */
    public void zoomIn(View view) {
        AnimationUtils.zoomIn(view, 40);
    }

    /**
     * 缩小动画--
     */
    public void zoomOn(View view) {
        AnimationUtils.zoomOn(view, 40);
    }

    /**
     * 设置选中后的背景图
     */
    public void setSelectBackground(int selectBackground) {
        this.selectBackground = selectBackground;
    }
}
