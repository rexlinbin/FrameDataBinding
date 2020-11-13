package com.lengyue.framedatabinding;

import android.app.Application;

import com.lengyue.frame_databinding.network.RetrofitManager;
import com.lengyue.frame_databinding.utils.PreferenceUtil;
import com.lengyue.framedatabinding.network.MyRetrofitApiService;
import com.lengyue.framedatabinding.network.Urls;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitManager.getInstance().initRetrofit(Urls.HOST, MyRetrofitApiService.class);
        PreferenceUtil.getInstance().setContext(getApplicationContext()).setFileName("my");
    }
}
