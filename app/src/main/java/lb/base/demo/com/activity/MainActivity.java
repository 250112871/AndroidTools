package lb.base.demo.com.activity;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.base_tools.Lg;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lb.base.demo.com.R;
import lb.base.demo.com.adapter.TestRecyclerAdapter;
import lb.base.demo.com.base.BaseActivity;
import lb.base.demo.com.bean.TestBean;
import lb.base.demo.com.contract.MainContract;
import lb.base.demo.com.presenter.MainPresenter;
import lb.base.demo.com.view.CustomDialog;
import lb.base.demo.com.view.CustomToast;

/**
 * @author FLB
 * 工具类目录
 */
public class MainActivity extends BaseActivity implements MainContract.View {

    @BindView(R.id.text_recycle)
    RecyclerView recyclerView;
    private TestRecyclerAdapter adapter;

    private MainPresenter presenter;
    private CustomToast toast;
    private CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initStatuesBar();
        ButterKnife.bind(this);
        initView();
        initData();

    }

    private void initData() {
        presenter = new MainPresenter();
        presenter.attachView(this);
        presenter.getTestList();
        toast = CustomToast.getInstance(this);
        dialog = new CustomDialog(this);
    }

    private void initView() {
        adapter = new TestRecyclerAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);
                c.drawColor(ContextCompat.getColor(MainActivity.this, R.color.line));
            }

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, 1, 0, 1);
            }
        });
        recyclerView.setAdapter(adapter);
        adapter.setOnclickServiceItem(new TestRecyclerAdapter.OnclickServiceItem() {
            @Override
            public void onClick(TestBean testBean, int i) {
                if (i % 2 == 0) {
                    toast.showMsg("点击了：" + i);
                } else {
                    dialog.showMsg("击了：" + i);
                    Lg.ii("击了："+i);
                }
            }
        });
    }

    @Override
    public void refreshTestList(List<TestBean> list) {
        adapter.setData(list, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
