package com.lengyue.frame_databinding.base;

import android.app.Application;

import androidx.annotation.NonNull;

import com.lengyue.frame_databinding.base.BaseViewModel;

/**
 * 不需要用ViewModel的,请用此类代替
 * @author linbin
 */
public class NormalViewModel extends BaseViewModel {


    public NormalViewModel(@NonNull Application application) {
        super(application);
    }

}
