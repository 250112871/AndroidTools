package lb.base.demo.com.presenter;


import java.util.ArrayList;
import java.util.List;

import lb.base.demo.com.base.BasePresenter;
import lb.base.demo.com.bean.TestBean;
import lb.base.demo.com.contract.MainContract;

/**
 * @author Created by 25011 on 2018/7/23.
 * 测试主页的P
 */
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {
    @Override
    public void getTestList() {
        List<TestBean> list = new ArrayList<>();
        list.add(new TestBean("a"));
        list.add(new TestBean("b"));
        list.add(new TestBean("c"));
        list.add(new TestBean("d"));
        if (isViewAttached()) {
            mView.refreshTestList(list);
        }
    }
}
