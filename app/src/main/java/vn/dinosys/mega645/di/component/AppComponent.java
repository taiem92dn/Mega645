package vn.dinosys.mega645.di.component;

import javax.inject.Singleton;

import dagger.Component;
import vn.dinosys.mega645.app.Runtime;
import vn.dinosys.mega645.di.module.AppModule;
import vn.dinosys.mega645.ui.activity.base.BaseActivity;
import vn.dinosys.mega645.ui.fragment.base.BaseFragment;
import vn.dinosys.mega645.ui.fragment.login.LoginFragment;

/**
 * Created by huutai.
 * Since: 8/18/16 on 11:51 AM
 * Project: Mega645
 */
@Singleton
@Component( modules = {
        AppModule.class
})
public interface AppComponent {

    Runtime runtime();

    void inject(BaseActivity pBaseActivity);
    void inject(BaseFragment pBaseFragment);
    void inject(LoginFragment pLoginFragment);

}
