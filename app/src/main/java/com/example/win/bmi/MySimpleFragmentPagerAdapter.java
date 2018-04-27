package com.example.win.bmi;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MySimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private CharSequence tabTitles[] = new CharSequence[]{"Title 1","Title 2","Title 3"};
    private Fragment[] myFragments = new Fragment[1];
    MySimpleFragmentPagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        return myFragments[position];
    }

    @Override
    public int getCount() {
        return myFragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    public void setFragments(Fragment[] fs){
        myFragments=fs;
    }
}
