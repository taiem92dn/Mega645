package vn.dinosys.mega645.ui.activity.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.FacebookSdk;

import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import vn.dinosys.mega645.R;
import vn.dinosys.mega645.app.App;
import vn.dinosys.mega645.di.component.AppComponent;
import vn.dinosys.mega645.ui.fragment.base.BaseFragment;

/**
 * Created by htsi.
 * Since: 7/1/16 on 5:24 PM
 * Project: Mega645
 */
public class BaseActivity extends AppCompatActivity {


    protected AppComponent getApplicationComponent() {
        return ((App) getApplication()).getAppComponent();
    }

    protected void setupActivityComponent() {
        // Implement in child class
    }

    protected BaseFragment hostFragment() {
        // Implement in child class
        return null;
    }

    private void addFragment(BaseFragment baseFragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tf = fm.beginTransaction();
        tf.add(R.id.container, baseFragment, baseFragment.getClass().getName());
        tf.commit();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("BaseActivity", "onCreate");
        FacebookSdk.sdkInitialize(getApplicationContext());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupActivityComponent();

        BaseFragment hostFragment = hostFragment();
        if (hostFragment != null) {
            setContentView(R.layout.activity_base);
        }
        if (savedInstanceState == null && hostFragment != null) {
            addFragment(hostFragment);
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
