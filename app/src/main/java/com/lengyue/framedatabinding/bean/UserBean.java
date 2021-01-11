package com.lengyue.framedatabinding.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;


import java.io.Serializable;


public class UserBean extends BaseObservable implements Serializable {
    private String userName;
    private String password;
    private String id;
    private String tokenName;
    private String tokenValue;
    private int anInt;
    private boolean aBoolean;
    private boolean anBoolean;
    private double aDouble;
    private double anDouble;

    public boolean isAnBoolean() {
        return anBoolean;
    }

    public void setAnBoolean(boolean anBoolean) {
        this.anBoolean = anBoolean;
    }

    public double getAnDouble() {
        return anDouble;
    }

    public void setAnDouble(double anDouble) {
        this.anDouble = anDouble;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public int getAnInt() {
        return anInt;
    }

    public void setAnInt(int anInt) {
        this.anInt = anInt;
    }

    public boolean isaBoolean() {
        return aBoolean;
    }

    public void setaBoolean(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    public double getaDouble() {
        return aDouble;
    }

    public void setaDouble(double aDouble) {
        this.aDouble = aDouble;
    }
}
