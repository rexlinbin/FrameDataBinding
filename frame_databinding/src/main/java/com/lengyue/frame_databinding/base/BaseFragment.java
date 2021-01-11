package com.lengyue.frame_databinding.base;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonSyntaxException;
import com.lengyue.frame_databinding.R;
import com.lengyue.frame_databinding.utils.ToastUtil;
import com.lengyue.frame_databinding.views.CustomProgress;
import com.lengyue.frame_databinding.bean.Resource;
import com.lengyue.frame_databinding.network.NetWorkUtils;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;


/**
 * @author linbin
 */
public abstract class BaseFragment<VM extends BaseViewModel, VDB extends ViewDataBinding> extends RxFragment {
    /**
     * 获取当前activity布局文件
     */
    protected abstract int getContentViewId();

    /**
     * 处理逻辑业务
     */
    protected abstract void processLogic(Bundle savedInstanceState);

    public String getTitle(){
        return "";
    }

    @DrawableRes
    public int getIcon(){
        return 0;
    }

    protected VM mViewModel;
    protected View mContentView;
    protected VDB binding;

    private CustomProgress dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // 避免多次从xml中加载布局文件
        if (mContentView == null) {
            binding = DataBindingUtil.inflate(inflater, getContentViewId(), null, false);
            mContentView = binding.getRoot();
            binding.setLifecycleOwner(this);
            createViewModel();
            processLogic(savedInstanceState);
        } else {
            ViewGroup parent = (ViewGroup) mContentView.getParent();
            if (parent != null) {
                parent.removeView(mContentView);
            }
        }
        return mContentView;
    }


    private void createViewModel() {
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
        Intent intent = new Intent(getActivity(), classz);
        startActivity(intent);
    }

    protected void startActivity(Class classz, Bundle bundle){
        Intent intent = new Intent(getActivity(), classz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void startActivityForResult(Class classz, Bundle bundle, int requestCode){
        Intent intent = new Intent(getActivity(), classz);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    protected void startActivityForResult(Class classz, int requestCode){
        Intent intent = new Intent(getActivity(), classz);
        startActivityForResult(intent, requestCode);
    }

    public void showDialog(){
        if (dialog == null) {
            dialog = CustomProgress.show(getActivity(), "", true, null);
        }

        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    public void showDialog(String msg){
        if (dialog == null) {
            dialog = CustomProgress.show(getActivity(), msg, true, null);
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

    public abstract class OnCallback<T> implements Resource.OnHandleCallback<T> {
        @Override
        public void onLoading(String msg) {
            if (dialog == null) {
                dialog = CustomProgress.show(getActivity(), "", true, null);
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
