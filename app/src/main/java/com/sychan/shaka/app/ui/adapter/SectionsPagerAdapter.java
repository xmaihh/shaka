package com.sychan.shaka.app.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sychan.shaka.app.ui.fragment.NewTestFragment;
import com.sychan.shaka.app.ui.fragment.WaterMarkFragment;

/**
 * Created by sychan on 2017-11-07.
 * Function：
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NewTestFragment();
            case 1:
                return new NewTestFragment();
            case 2:
                return new WaterMarkFragment();
            case 3:
                return new NewTestFragment();
            default:
                return new NewTestFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
