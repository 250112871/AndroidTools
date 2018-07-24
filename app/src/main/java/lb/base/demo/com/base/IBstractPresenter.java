package lb.base.demo.com.base;


/**
 * @author FLB
 * mvp模型中的 presenter基类
 */
public interface IBstractPresenter<T extends IBaseView> {
    /**
     * 注入view
     *
     * @param view view
     */
    void attachView(T view);

    /**
     * 回收view
     */
    void detachView();

    /**
     * 每次调用业务请求的时候都要出先调用方法检查是否与View建立连接
     *
     * @return 是否与View建立连接
     */
    boolean isViewAttached();
}
