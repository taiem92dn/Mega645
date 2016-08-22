package vn.dinosys.mega645.presenter.base;

/**
 * Created by htsi.
 * Since: 7/4/16 on 9:57 AM
 * Project: Mega645
 */
public interface IBasePresenter<ViewType> {

    void setView(ViewType pViewType);
    void destroyView();
}
