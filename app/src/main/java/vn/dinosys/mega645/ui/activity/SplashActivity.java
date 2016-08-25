package vn.dinosys.mega645.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import butterknife.BindView;
import vn.dinosys.mega645.R;
import vn.dinosys.mega645.ui.activity.base.BaseActivity;
import vn.dinosys.mega645.ui.activity.login.LoginActivity;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.imgLogo)
    ImageView mImgLogo;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mHandler = new Handler();

        mHandler.postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            overridePendingTransition(0, 0);
            startActivity(intent);

            finish();
        }, 2000);
    }
}
