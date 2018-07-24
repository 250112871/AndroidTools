package lb.base.demo.com.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.base_tools.ViewUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lb.base.demo.com.R;
import lb.base.demo.com.bean.TestBean;

/**
 * @author Created by 25011 on 2018/7/19.
 * 选择医院适配
 */
public class TestRecyclerAdapter extends RecyclerView.Adapter<TestRecyclerAdapter.ServiceViewHolder> {
    private Context context;
    private List<TestBean> list;
    private int post = 0;

    public TestRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<TestBean> list, int post) {
        this.list = list;
        this.post = post;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ServiceViewHolder(View.inflate(context, R.layout.test_list_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder viewHolder, final int i) {
        final TestBean test = list.get(i);
        ViewUtils.setText(viewHolder.textView, test.getName());
        viewHolder.imageView.setVisibility(i == post ? View.VISIBLE : View.GONE);
        final int pp = i;
        viewHolder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post = pp;
                notifyDataSetChanged();
                if (onclickServiceItem != null) {
                    onclickServiceItem.onClick(test,i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (list != null) {
            count = list.size();
        }
        return count;
    }

    class ServiceViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_server_name)
        TextView textView;
        @BindView(R.id.iv_selected)
        ImageView imageView;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;

        ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnclickServiceItem onclickServiceItem;

    public void setOnclickServiceItem(OnclickServiceItem onclickServiceItem) {
        this.onclickServiceItem = onclickServiceItem;
    }

    public interface OnclickServiceItem {
        /**
         * 点击服务后返回监听
         *
         * @param testBean 测试Bean
         * @param i
         */
        void onClick(TestBean testBean, int i);
    }
}
