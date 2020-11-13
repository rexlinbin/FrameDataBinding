package com.lengyue.framedatabinding.ui;

import androidx.lifecycle.Observer;

import android.view.View;

import com.lengyue.frame_databinding.base.BaseActivity;
import com.lengyue.frame_databinding.bean.Resource;
import com.lengyue.framedatabinding.R;
import com.lengyue.framedatabinding.bean.UserBean;
import com.lengyue.framedatabinding.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> {

    public void onHello(View view){
        mViewModel.login(new UserBean()).observe(this, new Observer<Resource<UserBean>>() {
            @Override
            public void onChanged(Resource<UserBean> userBeanResource) {
                userBeanResource.handler(new OnCallback<UserBean>() {
                    @Override
                    public void onSuccess(UserBean data) {

                    }

                    @Override
                    public void onFailure(String code, String msg) {
                        super.onFailure(code, msg);
                    }
                });
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void processLogic() {

    }
}
