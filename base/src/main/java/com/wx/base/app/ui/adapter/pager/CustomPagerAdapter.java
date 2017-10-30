package com.wx.base.app.ui.adapter.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import com.wx.base.app.ui.fragment.BaseFragment;
import com.wx.base.project.model.ResourceMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2015/8/12 0012.
 */
public class CustomPagerAdapter extends FragmentStatePagerAdapter {

    private List<ResourceMap> maps = new ArrayList<ResourceMap>();

    public CustomPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    public void add(ResourceMap fragment){
        this.maps.add(fragment);
        notifyDataSetChanged();
    }

    public void addAll(List<ResourceMap> maps){
        this.maps.addAll(maps);
        notifyDataSetChanged();
    }

    public void updateAll(List<ResourceMap> maps){
        this.maps = maps;
        notifyDataSetChanged();
    }
    //================================================//



    @Override
    public Fragment getItem(int position) {
        ResourceMap map = maps.get(position);
        if(map.getFragment() == null){
            return new Fragment();
        }

        // 如果有定制，可以根据

        return map.getFragment();
    }

    @Override
    public int getCount() {
        return maps.size();
    }

    public CharSequence getPageTitle(int position) {
        return maps.get(position).getName();
    }

    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    //@Override
    //public void destroyItem(ViewGroup container, int position, Object object) {
    //    //super.destroyItem(container, position, object);
    //}
}
