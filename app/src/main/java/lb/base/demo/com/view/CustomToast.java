package lb.base.demo.com.view;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import lb.base.demo.com.R;

/**
 * @author Created by 25011 on 2018/7/17.
 * 中间toast提示框
 */
public class CustomToast {
    private Context context;
    private Toast toast;
    private TextView toastContent;
    private int duration = Toast.LENGTH_SHORT;

    private CustomToast(Context context) {
        this.context = context;
        toast = new Toast(context);
        initView();
    }

    private static volatile CustomToast customToast;

    public static CustomToast getInstance(Context context) {
        if (customToast == null) {
            synchronized (CustomToast.class) {
                if (customToast == null) {
                    customToast = new CustomToast(context);
                }
            }
        }
        return customToast;
    }

    private void initView() {
        View view = View.inflate(context, R.layout.custom_toast, null);
        toastContent = view.findViewById(R.id.tv_content);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(duration);
    }

    public void showMsg(String msg) {
        if (msg != null) {
            toastContent.setText(msg);
            toast.show();
        }
    }
}
