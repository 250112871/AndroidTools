package lb.base.demo.com.base;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.jaeger.library.StatusBarUtil;

import lb.base.demo.com.R;

import static android.os.Build.VERSION.SDK_INT;

/**
 * @author FLB
 * View 基类的实现
 */
public class BaseActivity extends AppCompatActivity implements IBaseView {
    private Dialog loading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLoading();
    }


    private void initLoading() {
        loading = new Dialog(this, R.style.loading);
        loading.setContentView(new ProgressBar(this));
    }

    /**
     * 设置状态栏
     */
    public void initStatuesBar() {
        initStatuesBar(R.color.colorPrimary, 0);
    }

    /**
     * 设置状态栏颜色和透明度
     *
     * @param color 颜色
     * @param alpha 透明度
     */
    public void initStatuesBar(int color, int alpha) {
        if (SDK_INT > Build.VERSION_CODES.M) {
            StatusBarUtil.setLightMode(this);
            StatusBarUtil.setTranslucent(this);
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, color), alpha);
        } else {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, color), alpha);
        }
    }

    /**
     * 设置Loding 是否可以取消
     *
     * @param arg true 可以  默认不可以
     */
    protected void setLoadingCancelable(boolean arg) {
        loading.setCanceledOnTouchOutside(arg);
        loading.setCancelable(arg);
    }

    /**
     * 显示Loading
     */
    @Override
    public void showLoading() {
        loading.show();
    }

    /**
     * 隐藏Loading
     */
    @Override
    public void hideLoading() {
        if (null != loading && loading.isShowing()) {
            loading.dismiss();
        }
    }

    /**
     * 显示错误页面
     *
     * @param ret 错误码
     * @param e   Exception
     */
    @Override
    public void showError(int ret, Exception e) {

    }
}
