package com.ihsinformatics.childhoodtb_mobile.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import com.ihsinformatics.childhoodtb_mobile.ChildhoodTbNewActivities.PediatricContactInvestigationAtFacilityActivity;

/**
 * Created by Shujaat on 3/21/2017.
 */
public class FragmentingPagerAdapter extends FragmentPagerAdapter {
    int pageCount;
    PediatricContactInvestigationAtFacilityActivity activity;
    public FragmentingPagerAdapter(
            FragmentManager fragmentManager, int pageCount) {
        super(fragmentManager);
        this.pageCount = pageCount;
       activity= new PediatricContactInvestigationAtFacilityActivity();

    }

    /**
     * This method will be invoked when a page is requested to create
     */
    @Override
    public Fragment getItem(int arg0) {
   PediatricContactInvestigationAtFacilityActivity.PediatricPresumptiveFragment fragment = activity.new PediatricPresumptiveFragment();
        Bundle data = new Bundle();
        data.putInt("current_page", arg0 + 1);
        fragment.setArguments(data);
        return fragment;
    }

    /**
     * Returns the number of pages
     */
    @Override
    public int getCount() {
        return pageCount;
    }

}
