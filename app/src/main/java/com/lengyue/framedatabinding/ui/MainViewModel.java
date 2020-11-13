package com.lengyue.framedatabinding.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.lengyue.frame_databinding.base.BaseViewModel;
import com.lengyue.frame_databinding.bean.Resource;
import com.lengyue.framedatabinding.bean.UserBean;
import com.lengyue.framedatabinding.common.GlobalParams;
import com.lengyue.framedatabinding.network.MyRetrofitApiService;

public class MainViewModel extends BaseViewModel<MyRetrofitApiService> {
    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Resource<UserBean>> login(UserBean bean){
        MutableLiveData<Resource<UserBean>> liveData = new MutableLiveData<>();
        return observeGo(getApiService().login(GlobalParams.headers, bean), liveData);
    }
}
