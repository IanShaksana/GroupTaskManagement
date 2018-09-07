package com.example.adrian.grouptaskmanagement;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrian on 5/18/2018.
 */

public class SectionPageAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentlist = new ArrayList<>();
    private final List<String> mFragmentTitlelist = new ArrayList<>();

    public void addFragment(Fragment fragment, String title) {
        mFragmentlist.add(fragment);
        mFragmentTitlelist.add(title);
    }


    public SectionPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitlelist.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentlist.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentlist.size();
    }
}
