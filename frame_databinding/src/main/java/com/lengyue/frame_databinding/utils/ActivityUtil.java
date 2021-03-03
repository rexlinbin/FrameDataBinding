package com.lengyue.frame_databinding.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;

public class ActivityUtil {
    private static class SingleTon{
        private static ActivityUtil instance = new ActivityUtil();
    }
    private ActivityUtil(){}

    public ActivityUtil getInstance(){
        return SingleTon.instance;
    }

    private WeakReference<Activity> currActivity;

    public void registerActivityLifecycle(Application application){
        application.registerActivityLifecycleCallbacks(new MyActivityLifecycle());
    }

    public void setCurrActivity(Activity currActivity) {
        this.currActivity = new WeakReference<>(currActivity);
    }

    public Activity getCurrActivity() {
        Activity activity = null;
        if (currActivity != null){
            activity = currActivity.get();
        }
        return activity;
    }

    class MyActivityLifecycle implements Application.ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(@NonNull Activity activity) {

        }

        @Override
        public void onActivityResumed(@NonNull Activity activity) {
            setCurrActivity(activity);
        }

        @Override
        public void onActivityPaused(@NonNull Activity activity) {

        }

        @Override
        public void onActivityStopped(@NonNull Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {

        }
    }
}
