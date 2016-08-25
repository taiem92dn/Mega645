package vn.dinosys.mega645.app;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import vn.dinosys.mega645.R;
import vn.dinosys.mega645.di.component.AppComponent;
import vn.dinosys.mega645.di.component.DaggerAppComponent;
import vn.dinosys.mega645.di.module.AppModule;

/**
 * Created by huutai.
 * Since: 8/18/16 on 11:49 AM
 * Project: Mega645
 */
public class App extends Application {

    private static App sInstance;

    private AppComponent mAppComponent;

    public App() {
        sInstance = this;
    }

    public static App getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        this.mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();


        //  Set default font to MyriadPro
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(Constants.APP_DEFAULT_FONT)
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
