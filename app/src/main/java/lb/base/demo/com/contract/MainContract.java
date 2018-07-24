package lb.base.demo.com.contract;

import java.util.List;
import lb.base.demo.com.base.IBaseView;
import lb.base.demo.com.bean.TestBean;

/**
 * @author Created by FLB on 2018/7/23.
 * 针对具体业务特有方法进行封装
 */
public interface MainContract {
    /**
     * View 特有接口
     */
    interface View extends IBaseView {
        void refreshTestList(List<TestBean> list);
    }

    /**
     * Presenter 特有接口
     */
    interface Presenter {
        void getTestList();
    }
}
