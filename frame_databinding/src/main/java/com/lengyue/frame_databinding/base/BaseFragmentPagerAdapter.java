package com.lengyue.frame_databinding.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author linbin
 */
public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> mList;

    public BaseFragmentPagerAdapter(@NonNull FragmentManager fm) {
        this(fm, new ArrayList<>());
    }

    public BaseFragmentPagerAdapter(@NonNull FragmentManager fm, List<BaseFragment> list) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @NonNull
    @Override
    public BaseFragment getItem(int position) {
        return mList.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position).getTitle();
    }
}
