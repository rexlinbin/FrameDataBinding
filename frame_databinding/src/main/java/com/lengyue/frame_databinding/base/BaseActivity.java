package com.lengyue.frame_databinding.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.JsonSyntaxException;
import com.lengyue.frame_databinding.R;
import com.lengyue.frame_databinding.utils.ToastUtil;
import com.lengyue.frame_databinding.views.CustomProgress;
import com.lengyue.frame_databinding.views.statusbar.StatusBarUtil;
import com.lengyue.frame_databinding.bean.Resource;
import com.lengyue.frame_databinding.network.NetWorkUtils;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * @author linbin
 */
public abstract class BaseActivity<VM extends BaseViewModel, VDB extends ViewDataBinding> extends RxFragmentActivity {

    /**
     * 获取当前activity布局文件
     */
    protected abstract int getContentViewId();

    /**
     * 处理逻辑业务
     */
    protected abstract void processLogic();


    protected VM mViewModel;
    protected VDB binding;

    private CustomProgress dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, getContentViewId());
        binding.setLifecycleOwner(this);
        createViewModel();

        //用来设置整体下移，状态栏沉浸
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        //透明状态栏
        StatusBarUtil.setTranslucentStatus(this);
        StatusBarUtil.setStatusBarDarkTheme(this, true);

        processLogic();


    }

    public void createViewModel() {
        if (mViewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            mViewModel = (VM) ViewModelProviders.of(this).get(modelClass);
            mViewModel.setObjectLifecycleTransformer(bindToLifecycle());
        }
    }

    protected void startActivity(Class classz){
        Intent intent = new Intent(this, classz);
        startActivity(intent);
    }

    protected void startActivity(Class classz, Bundle bundle){
        Intent intent = new Intent(this, classz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void startActivityForResult(Class classz, Bundle bundle, int requestCode){
        Intent intent = new Intent(this, classz);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    protected void startActivityForResult(Class classz, int requestCode){
        Intent intent = new Intent(this, classz);
        startActivityForResult(intent, requestCode);
    }

    public void showDialog(){
        if (dialog == null) {
            dialog = CustomProgress.show(this, "", true, null);
        }

        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    public void showDialog(String msg){
        if (dialog == null) {
            dialog = CustomProgress.show(this, msg, true, null);
        }

        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    public void hideDialog(){
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public Context getContext() {
        return this;
    }

    public void onBack(View v){
        finish();
    }

    public abstract class OnCallback<T> implements Resource.OnHandleCallback<T> {
        @Override
        public void onLoading(String msg) {
            if (dialog == null) {
                dialog = CustomProgress.show(BaseActivity.this, "", true, null);
            }

            if (!TextUtils.isEmpty(msg)) {
                dialog.setMessage(msg);
            }

            if (!dialog.isShowing()) {
                dialog.show();
            }
        }

        @Override
        public void onError(Throwable throwable) {
            if (!NetWorkUtils.isNetworkConnected(getContext())) {
                ToastUtil.showToast(getContext(),getContext().getResources().getString(R.string.result_network_error));
                return;
            }

            if (throwable instanceof ConnectException) {
                ToastUtil.showToast(getContext(),getContext().getResources().getString(R.string.result_server_error));
            } else if (throwable instanceof SocketTimeoutException) {
                ToastUtil.showToast(getContext(),getContext().getResources().getString(R.string.result_server_timeout));
            } else if (throwable instanceof JsonSyntaxException) {
                ToastUtil.showToast(getContext(),getContext().getResources().getString(R.string.result_data_error));
            } else {
                ToastUtil.showToast(getContext(),getContext().getResources().getString(R.string.result_empty_error));
            }
        }

        @Override
        public void onFailure(String code, String msg) {
            ToastUtil.showToast(getContext(),msg);
        }

        @Override
        public void onCompleted() {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        @Override
        public void onProgress(int percent, long total) {

        }
    }
}
