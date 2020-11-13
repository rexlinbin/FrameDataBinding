# FrameDataBinding

## 一、引用
在工程的`build.gradle`中添加
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
在app的`build.gradle`中添加

```
android {
    ...
    
    dataBinding{
        enabled = true
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

dependencies {
    ...
    implementation 'com.github.rexlinbin:FrameDataBinding:1.0.0'
}
```
## 二、初始化

新建`Application`类，在onCreate方法中初始化网络请求和本地缓存
```
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitManager.getInstance().initRetrofit(Urls.HOST, MyRetrofitApiService.class);
        PreferenceUtil.getInstance().setContext(getApplicationContext()).setFileName("my");
    }
}
```



