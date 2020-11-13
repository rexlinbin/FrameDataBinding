package com.lengyue.frame_databinding.bean;

import java.io.Serializable;

/**
 * @author linbin
 */
public class ResponModel<T> implements Serializable {
    private static final String RESULT_SUCCESS = "0";

    private T respData;
    private String code;
    private String msg;

    public T getRespData() {
        return respData;
    }

    public void setRespData(T respData) {
        this.respData = respData;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess(){
        return RESULT_SUCCESS.equals(code);
    }
}
