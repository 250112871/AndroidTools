package lb.base.demo.com.base;

/**
 * @author FLB
 * Presenter 基类的实现
 */
public class BasePresenter<T extends IBaseView> implements IBstractPresenter<T> {
    protected static final int SO_TIMEOUT = 5000;
    protected static final int CON_TIMEOUT = 5000;
    protected T mView;

    @Override
    public void attachView(T view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public boolean isViewAttached() {
        return mView != null;
    }
}
