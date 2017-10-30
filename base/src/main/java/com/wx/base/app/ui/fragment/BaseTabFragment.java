package com.wx.base.app.ui.fragment;

import android.support.annotation.CallSuper;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.wx.base.R;
import com.wx.base.app.ui.adapter.pager.CustomPagerAdapter;
import com.wx.base.project.model.ResourceMap;

import java.util.List;

/**
 * Created by alex on 16-12-8.
 */

public abstract class BaseTabFragment extends BaseFragment{

    protected TabLayout tabLayout;
    protected ViewPager viewPager;

    protected CustomPagerAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_common_tab;
    }

    @Override
    @CallSuper
    protected void bindViews(View view){
        super.bindViews(view);
        tabLayout = (TabLayout)view.findViewById(R.id.tab_layout);
        viewPager = (ViewPager)view.findViewById(R.id.viewpager);

    }

    @Override
    protected void setup(){
        //FragmentAdapter
        adapter = new CustomPagerAdapter(getChildFragmentManager());

        //ViewPager
        viewPager.setAdapter(adapter);
        //tabLayout.setupWithViewPager(viewPager);
        //tabsStrip.setViewPager(viewPager);
    }

    protected void updateViewPager(List<ResourceMap> maps){
        adapter.updateAll(maps);
        if(maps.size() > 5){
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }else{
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        }
        tabLayout.setupWithViewPager(viewPager);

        //viewPager.setCurrentItem(item_index);   //设置初始位置
    }
}
