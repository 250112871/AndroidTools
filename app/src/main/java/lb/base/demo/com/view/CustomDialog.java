package lb.base.demo.com.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import lb.base.demo.com.R;

/**
 * @author Created by 25011 on 2018/7/17.
 */
public class CustomDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private Window window;

    @BindView(R.id.tv_content_msg)
    TextView msgContent;
    @BindView(R.id.ll_two_button)
    LinearLayout llTowButon;
    @BindView(R.id.ll_one_button)
    RelativeLayout llOneButton;

    @BindView(R.id.bt_left)
    Button btLeft;
    @BindView(R.id.bt_right)
    Button btRight;
    @BindView(R.id.bt_single)
    Button btSingle;

    private DialogType type;

    @Override
    public void onClick(View view) {
        if (onclickListion != null) {
            if (view == btSingle || view == btLeft) {
                onclickListion.onClick(Direction.Left);
            } else if (view == btRight) {
                onclickListion.onClick(Direction.Right);
            }
        }
        dismiss();
    }

    public enum DialogType {
        /**
         * 一个按钮的对话框
         */
        SINGLE,
        /**
         * 两个按钮的框
         */
        TWO
    }

    public enum Direction {
        /**
         * 点击了左按键
         */
        Left,
        /**
         * 点击了右按键
         */
        Right
    }

    public CustomDialog(@NonNull Context context) {
        super(context);
        init(context);
        type = DialogType.TWO;
        initView();
    }

    public CustomDialog(@NonNull Context context, DialogType type) {
        super(context);
        init(context);
        this.type = type;
        initView();
    }

    private void init(Context context) {
        this.context = context;
        window = getWindow();
        if (window != null) {
            window.requestFeature(Window.FEATURE_NO_TITLE);
        }
    }

    private void initView() {
        View inflate = View.inflate(context, R.layout.custom_dialog, null);
        setContentView(inflate);
        ButterKnife.bind(this, inflate);
        switch (type) {
            case SINGLE:
                llTowButon.setVisibility(View.GONE);
                llOneButton.setVisibility(View.VISIBLE);
                break;
            case TWO:
                llTowButon.setVisibility(View.VISIBLE);
                llOneButton.setVisibility(View.GONE);
                break;
            default:
                break;
        }

        btLeft.setOnClickListener(this);
        btRight.setOnClickListener(this);
        btSingle.setOnClickListener(this);
    }

    public void setSingleButtonName(@NonNull String name) {
        btSingle.setText(name);
    }

    @Override
    protected void onStart() {
        super.onStart();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawableResource(R.color.transparency);
    }

    public void showMsg(String msg) {
        msgContent.setText(msg);
        show();
    }

    private OnclickListen onclickListion;

    /**
     * 如果只是实现 确定键可实现此方法
     */
    public abstract static class BaseDefaultOnclick implements OnclickListen {
        /**
         * 出发点击事件
         */
        public abstract void onClick();

        @Override
        public void onClick(Direction direction) {
            if (direction == Direction.Right) {
                onClick();
            }
        }
    }

    public interface OnclickListen {
        /**
         * 出发点击事件
         *
         * @param direction 按键点击方向 Direction.Left Direction.Right
         */
        void onClick(Direction direction);
    }


    /**
     * 设置确定按钮监听时间
     *
     * @param onclickListion 监听
     */
    public void setOnButtomListion(OnclickListen onclickListion) {
        this.onclickListion = onclickListion;
    }
}
