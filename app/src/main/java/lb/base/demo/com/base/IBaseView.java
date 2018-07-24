package lb.base.demo.com.base;

/**
 * @author FLB
 * Mvp 模型中View 的基类
 */
public interface IBaseView {
    /**
     * Show loading
     */
    void showLoading();

    /**
     * hide loading
     */
    void hideLoading();

    /**
     * 展示错误信息
     *
     * @param ret 错误码
     * @param e   Exception
     */
    void showError(int ret, Exception e);
}
