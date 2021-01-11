package com.lengyue.frame_databinding.base;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;


import com.google.android.material.tabs.TabLayout;
import com.lengyue.frame_databinding.R;
import com.lengyue.frame_databinding.databinding.ActivityTabBinding;
import com.lengyue.frame_databinding.utils.ToastUtil;
import com.lengyue.frame_databinding.views.statusbar.StatusBarUtil;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public abstract class BaseTabActivity<VM extends BaseViewModel> extends BaseActivity<VM, ActivityTabBinding> {
    private boolean isMainActivity = true;

    public abstract List<BaseFragment> getFragments();

    protected TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> onTabSelectedListener;

    public boolean canScrollable(){
        return false;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_tab;
    }

    @Override
    protected void processLogic() {
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        StatusBarUtil.setStatusBarDarkTheme(this, false);

        List<BaseFragment> fragments = getFragments();
        binding.viewPager.setAdapter(new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragments));
        binding.viewPager.setScrollable(canScrollable());
        binding.tabLayout.setTabMode(TabLayout.MODE_FIXED);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (onTabSelectedListener != null){
                    onTabSelectedListener.onTabSelected(tab);
                }else {
                    isTabSelect(tab, true);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (onTabSelectedListener != null){
                    onTabSelectedListener.onTabUnselected(tab);
                }else {
                    isTabSelect(tab, false);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (onTabSelectedListener != null){
                    onTabSelectedListener.onTabReselected(tab);
                }
            }
        });

        for (int i = 0; i < fragments.size(); i++) {
            BaseFragment baseFragment = fragments.get(i);
            binding.tabLayout.getTabAt(i).setCustomView(getTabView(baseFragment));
        }
    }

    public void setOnTabSelectedListener(TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> onTabSelectedListener) {
        this.onTabSelectedListener = onTabSelectedListener;
    }

    public View getTabView(BaseFragment baseFragment){
        View view = LayoutInflater.from(this).inflate(R.layout.view_tab, null);
        TextView tv = view.findViewById(R.id.tv_title);
        ImageView iv = view.findViewById(R.id.iv_icon);
        tv.setText(baseFragment.getTitle());
        iv.setImageResource(baseFragment.getIcon());
        return view;
    }

    public void isTabSelect(TabLayout.Tab tab, boolean isSelect){
        View view = tab.getCustomView();
        if (view != null) {
            TextView tv = view.findViewById(R.id.tv_title);
            ImageView iv = view.findViewById(R.id.iv_icon);
            tv.setSelected(isSelect);
            iv.setSelected(isSelect);
        }
    }

    public void setMainActivity(boolean mainActivity) {
        isMainActivity = mainActivity;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && isMainActivity) {
            exitBy2Click();
        }
        return false;
    }

    private static Boolean isExit = false;

    private void exitBy2Click() {
        if (!isExit) {
            isExit = true;
            ToastUtil.showToast(this,"再点一次退出");
            Timer tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2 * 1000);
        } else {
            this.finish();
        }
    }
}
