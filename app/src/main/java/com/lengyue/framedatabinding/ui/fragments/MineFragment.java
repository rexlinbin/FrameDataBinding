package com.lengyue.framedatabinding.ui.fragments;

import android.os.Bundle;

import com.lengyue.frame_databinding.base.BaseFragment;
import com.lengyue.framedatabinding.R;
import com.lengyue.framedatabinding.databinding.FragmentMineBinding;
import com.lengyue.framedatabinding.ui.MainViewModel;

public class MineFragment extends BaseFragment<MainViewModel, FragmentMineBinding> {

    @Override
    public String getTitle() {
        return "我的";
    }

    @Override
    public int getIcon() {
        return R.drawable.ic_empty_zhihu;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
