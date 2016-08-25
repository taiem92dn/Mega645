package vn.dinosys.mega645.ui.fragment.base;

import android.content.Context;

/**
 * Created by htsi.
 * Since: 7/3/16 on 9:51 AM
 * Project: DinoAd
 */
public interface IBaseView {
    void showLoading();

    void hideLoading();

    void showRetry();

    void showRetry(String message);

    void hideRetry();

    void showNoData();

    Context getContext();
}
