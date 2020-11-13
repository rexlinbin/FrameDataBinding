package com.lengyue.frame_databinding.base;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.lengyue.frame_databinding.R;
import com.lengyue.frame_databinding.bean.Resource;
import com.lengyue.frame_databinding.bean.ResponModel;
import com.lengyue.frame_databinding.network.Interceptor.NetCacheInterceptor;
import com.lengyue.frame_databinding.network.Interceptor.OfflineCacheInterceptor;
import com.lengyue.frame_databinding.network.ParamsBuilder;
import com.lengyue.frame_databinding.network.RetrofitManager;
import com.lengyue.frame_databinding.network.downloadutils.DownFileUtils;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * @author linbin
 */
public abstract class BaseViewModel<Api> extends AndroidViewModel {
    private ArrayList<String> onNetTags;
    private LifecycleTransformer objectLifecycleTransformer;


    public BaseViewModel(@NonNull Application application) {
        super(application);
        onNetTags = new ArrayList<>();
    }

    protected Api getApiService() {
        return (Api) RetrofitManager.getInstance().getApiService();
    }

    public void setObjectLifecycleTransformer(LifecycleTransformer objectLifecycleTransformer) {
        this.objectLifecycleTransformer = objectLifecycleTransformer;
    }

    protected  <T> MutableLiveData<T> observeGo(Observable observable, final MutableLiveData<T> liveData) {
        return observeGo(observable, liveData, null);
    }

    protected <T> MutableLiveData<T> observeGo(Observable observable, final MutableLiveData<T> liveData, ParamsBuilder paramsBuilder) {
        if (paramsBuilder == null) {
            paramsBuilder = ParamsBuilder.build();
        }
        int retryCount = paramsBuilder.getRetryCount();
        if (retryCount > 0) {
            return observeWithRetry(observable, liveData, paramsBuilder);
        } else {
            return observe(observable, liveData, paramsBuilder);
        }
    }


    /**
     * 把统一操作全部放在这，不会重连
     */
    private <T> MutableLiveData<T> observe(Observable observable, final MutableLiveData<T> liveData, ParamsBuilder paramsBuilder) {

        boolean showDialog = paramsBuilder.isShowDialog();
        String loadingMessage = paramsBuilder.getLoadingMessage();
        int onlineCacheTime = paramsBuilder.getOnlineCacheTime();
        int offlineCacheTime = paramsBuilder.getOfflineCacheTime();

        if (onlineCacheTime > 0) {
            setOnlineCacheTime(onlineCacheTime);
        }
        if (offlineCacheTime > 0) {
            setOfflineCacheTime(offlineCacheTime);
        }
        String oneTag = paramsBuilder.getOneTag();
        if (!TextUtils.isEmpty(oneTag)) {
            if (onNetTags.contains(oneTag)) {
                return liveData;
            }
        }
        observable.subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable1 -> {
                    if (!TextUtils.isEmpty(oneTag)) {
                        onNetTags.add(oneTag);
                    }
                    if (showDialog) {
                        liveData.postValue((T) Resource.loading(loadingMessage));
                    }
                })
                .doFinally(() -> {
                    if (!TextUtils.isEmpty(oneTag)) {
                        onNetTags.remove(oneTag);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(objectLifecycleTransformer)
                .subscribe(o -> {
                    liveData.postValue((T) Resource.response((ResponModel<Object>) o));
                }, throwable -> {
                    liveData.postValue((T) Resource.error((Throwable) throwable));
                });

        return liveData;
    }


    /**
     * 把统一操作全部放在这，这是带重连的
     */
    private  <T> MutableLiveData<T> observeWithRetry(Observable observable, final MutableLiveData<T> liveData, ParamsBuilder paramsBuilder) {
        boolean showDialog = paramsBuilder.isShowDialog();
        String loadingMessage = paramsBuilder.getLoadingMessage();
        int onlineCacheTime = paramsBuilder.getOnlineCacheTime();
        int offlineCacheTime = paramsBuilder.getOfflineCacheTime();

        if (onlineCacheTime > 0) {
            setOnlineCacheTime(onlineCacheTime);
        }
        if (offlineCacheTime > 0) {
            setOfflineCacheTime(offlineCacheTime);
        }

        String oneTag = paramsBuilder.getOneTag();
        if (!TextUtils.isEmpty(oneTag)) {
            if (onNetTags.contains(oneTag)) {
                return liveData;
            }
        }

        final int maxCount = paramsBuilder.getRetryCount();
        final int[] currentCount = {0};

        observable.subscribeOn(Schedulers.io())
                .retryWhen(throwable -> {
                    //如果还没到次数，就延迟5秒发起重连
                    if (currentCount[0] <= maxCount) {
                        currentCount[0]++;
                        return Observable.just(1).delay(5000, TimeUnit.MILLISECONDS);
                    } else {
                        //到次数了跑出异常
                        return Observable.error(new Throwable(getApplication().getApplicationContext().getString(R.string.result_retry_max_timeout)));
                    }
                })
                .doOnSubscribe(disposable1 -> {
                    if (!TextUtils.isEmpty(oneTag)) {
                        onNetTags.add(oneTag);
                    }
                    if (showDialog) {
                        liveData.postValue((T) Resource.loading(loadingMessage));
                    }
                })
                .doFinally(() -> {
                    if (!TextUtils.isEmpty(oneTag)) {
                        onNetTags.remove(oneTag);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(objectLifecycleTransformer)
                .subscribe(o -> {
                    liveData.postValue((T) Resource.response((ResponModel<Object>) o));
                }, throwable -> {
                    liveData.postValue((T) Resource.error((Throwable) throwable));
                });

        return liveData;
    }


    /**
     * 设置在线网络缓存
     */
    private void setOnlineCacheTime(int time) {
        NetCacheInterceptor.getInstance().setOnlineTime(time);
    }

    /**
     * 设置离线网络缓存
     */
    private void setOfflineCacheTime(int time) {
        OfflineCacheInterceptor.getInstance(getApplication().getApplicationContext()).setOfflineCacheTime(time);
    }

    /**
     * 正常下载(重新从0开始下载)
     */
    public <T> MutableLiveData<T> downLoadFile(Observable observable, final MutableLiveData<T> liveData, final String destDir, final String fileName) {
        return downLoadFile(observable, liveData, destDir, fileName, 0);
    }


    /**
     * 断点下载，如果下载到一半，可从一半开始下载
     */
    protected <T> MutableLiveData<T> downLoadFile(Observable observable, final MutableLiveData<T> liveData, final String destDir, final String fileName, long currentLength) {
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(requestBody -> {
                    if (currentLength == 0) {
                        return DownFileUtils.saveFile((ResponseBody) requestBody, destDir, fileName, liveData);
                    } else {
                        return DownFileUtils.saveFile((ResponseBody) requestBody, destDir, fileName, currentLength, liveData);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(file -> {
                    liveData.postValue((T) Resource.success(file));
                }, throwable -> {
                    liveData.postValue((T) Resource.error((Throwable) throwable));
                });
        return liveData;
    }


    /**
     * 上传文件只有2个参数，showDialog：是否显示dialog；loadmessage：showDialog显示的文字
     */
    protected <T> MutableLiveData<T> upLoadFile(Observable observable, MutableLiveData<T> liveData) {
        return upLoadFile(observable, liveData, null);
    }

    /**
     * 上传文件
     */
    protected <T> MutableLiveData<T> upLoadFile(Observable observable, MutableLiveData<T> liveData, ParamsBuilder paramsBuilder) {

        if (paramsBuilder == null) {
            paramsBuilder = ParamsBuilder.build();
        }
        boolean showDialog = paramsBuilder.isShowDialog();
        String loadingMessage = paramsBuilder.getLoadingMessage();

        observable.subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if (showDialog) {
                        liveData.postValue((T) Resource.loading(loadingMessage));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                //防止RxJava内存泄漏
                .subscribe(o -> {
                    liveData.postValue((T) Resource.success(getApplication().getApplicationContext().getString(R.string.result_upload_success)));
                }, throwable -> {
                    liveData.postValue((T) Resource.error((Throwable) throwable));
                });

        return liveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
