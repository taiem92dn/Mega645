package vn.dinosys.mega645.ui.activity.login;

import vn.dinosys.mega645.di.base.HasComponent;
import vn.dinosys.mega645.di.component.AppComponent;
import vn.dinosys.mega645.ui.activity.base.BaseActivity;
import vn.dinosys.mega645.ui.fragment.base.BaseFragment;
import vn.dinosys.mega645.ui.fragment.login.LoginFragment;

/**
 * Created by huutai.
 * Since: 8/18/16 on 2:15 PM
 * Project: Mega645
 */
public class LoginActivity extends BaseActivity implements HasComponent<AppComponent> {

    private AppComponent mAppComponent;

    @Override
    protected BaseFragment hostFragment() {
        return LoginFragment.newInstance();
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        mAppComponent = getApplicationComponent();
    }

    @Override
    public AppComponent getComponent() {
        return mAppComponent;
    }
}
