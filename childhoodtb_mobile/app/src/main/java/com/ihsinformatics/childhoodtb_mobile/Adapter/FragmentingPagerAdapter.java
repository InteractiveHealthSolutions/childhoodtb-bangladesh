package com.ihsinformatics.childhoodtb_mobile.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

/**
 * Created by Shujaat on 3/21/2017.
 */
public class FragmentingPagerAdapter extends FragmentPagerAdapter {
    Fragment fragment;
    int pageCount;


    public FragmentingPagerAdapter(FragmentManager fm, Fragment fragment, int pageCount) {
        super(fm);
        this.fragment = fragment;
        this.pageCount=pageCount;
    }

    @Override
    public Fragment getItem(int position) {

        Bundle data = new Bundle();
        data.putInt("current_page", position + 1);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public int getCount() {
        return pageCount;
    }

}
