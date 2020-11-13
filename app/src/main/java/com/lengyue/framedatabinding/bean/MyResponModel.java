package com.lengyue.framedatabinding.bean;

import com.lengyue.frame_databinding.bean.ResponModel;

public class MyResponModel<T> extends ResponModel<T> {
    private String message;

    @Override
    public String getMsg() {
        return message;
    }
}
