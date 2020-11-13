package com.lengyue.framedatabinding.network;


import com.lengyue.frame_databinding.bean.ResponModel;
import com.lengyue.framedatabinding.bean.MyResponModel;
import com.lengyue.framedatabinding.bean.UserBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface MyRetrofitApiService {
    @POST("customer/login")
    Observable<MyResponModel<UserBean>> login(@HeaderMap Map<String, Object> headerMap, @Body UserBean bean);
}
