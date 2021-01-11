package com.lengyue.framedatabinding.ui;

import android.view.View;

import androidx.lifecycle.Observer;

import com.lengyue.frame_databinding.base.BaseActivity;
import com.lengyue.frame_databinding.base.BaseFragment;
import com.lengyue.frame_databinding.base.BaseTabActivity;
import com.lengyue.frame_databinding.bean.Resource;
import com.lengyue.framedatabinding.R;
import com.lengyue.framedatabinding.bean.UserBean;
import com.lengyue.framedatabinding.databinding.ActivityMainBinding;
import com.lengyue.framedatabinding.db.DataBaseHelper;
import com.lengyue.framedatabinding.ui.fragments.HomeFragment;
import com.lengyue.framedatabinding.ui.fragments.MineFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends BaseTabActivity<MainViewModel> {



    @Override
    public List<BaseFragment> getFragments() {
        List<BaseFragment> list = new ArrayList<>();

        HomeFragment homeFragment = new HomeFragment();
        list.add(homeFragment);

        MineFragment mineFragment = new MineFragment();
        list.add(mineFragment);
        return list;
    }

    @Override
    protected void processLogic() {
        setMainActivity(true);
        super.processLogic();
    }

    DataBaseHelper dataBaseHelper;
    private void initDataBase(){
        dataBaseHelper = new DataBaseHelper(this, "user.db", UserBean.class);
        dataBaseHelper.getSqLiteOpenHelper().getReadableDatabase();
    }

    private void insertData(){
        Random random = new Random(System.currentTimeMillis());
        UserBean userBean = new UserBean();
        userBean.setaBoolean(random.nextBoolean());
        userBean.setaDouble(random.nextDouble());
        userBean.setAnInt(random.nextInt());
        userBean.setId(random.nextInt()+ "");
        dataBaseHelper.insert(userBean);
    }

    private void login(){
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

    public void onHello(View view){


    }
}
