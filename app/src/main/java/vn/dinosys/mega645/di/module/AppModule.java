package vn.dinosys.mega645.di.module;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.dinosys.mega645.R;
import vn.dinosys.mega645.app.Runtime;
import vn.dinosys.mega645.util.security.ObscuredSharedPreferences;

/**
 * Created by huutai.
 * Since: 8/18/16 on 11:54 AM
 * Project: Mega645
 */
@Module
public class AppModule {

    private final Application mApplication;

    public AppModule(Application pApplication) {
        mApplication = pApplication;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() { return mApplication;}

    @Provides
    @Singleton
    ObscuredSharedPreferences provideObscuredSharedPreferences() {
        return new ObscuredSharedPreferences(this.mApplication,
                mApplication.getSharedPreferences(this.mApplication.getString(R.string.app_name), Context.MODE_PRIVATE));
    }

    @Provides
    @Singleton
    Runtime provideRuntime(ObscuredSharedPreferences pSharedPreferences) {
        return new Runtime(pSharedPreferences);
    }

//    @Provides
//    @Singleton
//    DaoSession provideDaoSession(Context pContext) {
//        DaoMaster.OpenHelper helper = new DbUpgradeHelper(pContext, Constants.APP_DATABASE_NAME, null);
//        SQLiteDatabase db = helper.getWritableDatabase();
//        DaoMaster daoMaster = new DaoMaster(db);
//        return daoMaster.newSession();
//    }

    @Provides
    @Singleton
    @Named("REST_ADAPTER")
    Retrofit provideRestAdapter() {
        Log.d("TAG", "create rest adapter");
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://192.168.1.34:8080");
        return builder.build();
    }
}
