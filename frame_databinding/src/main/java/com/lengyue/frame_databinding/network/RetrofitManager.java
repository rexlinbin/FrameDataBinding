package com.lengyue.frame_databinding.network;


import com.lengyue.frame_databinding.network.Interceptor.HttpLogInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager<T> {
    public static String DEFAULT_SERVER = "http://127.0.0.1/";
    private static RetrofitManager retrofitManager;
    private T retrofitApiService;
    private OkHttpClient okHttpClient;
    private Class retrofitClass;

    private RetrofitManager() {
        initOkHttpClient();
    }

    public static <T> RetrofitManager getInstance() {
        if (retrofitManager == null) {
            synchronized (RetrofitManager.class) {
                if (retrofitManager == null) {
                    retrofitManager = new RetrofitManager<T>();
                }
            }
        }
        return retrofitManager;
    }

    public T getApiService() {
        return (T) retrofitManager.retrofitApiService;
    }

    public T getApiService(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return (T) retrofit.create(retrofitClass);
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public void setOkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    private void initOkHttpClient(){
        okHttpClient = new OkHttpClient.Builder()
                //设置日志打印
                .addInterceptor(new HttpLogInterceptor())
                //失败重连
                .retryOnConnectionFailure(true)
                //网络请求超时时间单位为秒
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    public void initRetrofit(String url, Class classz) {
        DEFAULT_SERVER = url;
        retrofitClass = classz;
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(DEFAULT_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        retrofitApiService = (T) retrofit.create(classz);
    }

    public void initRetrofit(String url, Class classz, Interceptor interceptor) {
        DEFAULT_SERVER = url;
        retrofitClass = classz;
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(DEFAULT_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        retrofitApiService = (T) retrofit.create(classz);
    }

}
