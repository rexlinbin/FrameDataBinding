package com.lengyue.framedatabinding.ui.fragments;

import android.os.Bundle;

import com.lengyue.frame_databinding.base.BaseFragment;
import com.lengyue.framedatabinding.R;
import com.lengyue.framedatabinding.databinding.FragmentHomeBinding;
import com.lengyue.framedatabinding.ui.MainViewModel;

public class HomeFragment extends BaseFragment<MainViewModel, FragmentHomeBinding> {

    @Override
    public String getTitle() {
        return "Home";
    }

    @Override
    public int getIcon() {
        return R.drawable.ic_empty_zhihu;
    }
    @Override
    protected int getContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}