package com.wx.base.app.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.wx.base.R;
import com.wx.base.app.ui.adapter.pager.CustomPagerAdapter;
import com.wx.base.project.model.ResourceMap;

import java.util.List;

/**
 * Created by alex on 16-11-15.
 */

public class BottomNavigationActivity extends BaseActivity {

    private TextView name;
    private CustomPagerAdapter adapter;
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationLayout;

    protected int getLayout() {
        return R.layout.activity_common_bottom_naviga;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        name = (TextView) findViewById(R.id.name);

        setupToolbar();
        setActionBarTitle("default");

        setup();
        initViews();
    }

    @Override
    public void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            if (name != null) {
                name.setText(title);
            }
        }
    }

    protected void initViews() {
    }

    protected void setup() {
        adapter = new CustomPagerAdapter(this.getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3); //默认加载三页
        bottomNavigationLayout = (BottomNavigationView) findViewById(R.id.bottom_navigation);
    }

    protected void updateViewPager(List<ResourceMap> maps) {
        adapter.updateAll(maps);
    }
}
