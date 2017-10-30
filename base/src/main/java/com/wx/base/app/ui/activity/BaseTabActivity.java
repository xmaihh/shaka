package com.wx.base.app.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.wx.base.R;
import com.wx.base.app.ui.adapter.pager.CustomPagerAdapter;
import com.wx.base.project.model.ResourceMap;

import java.util.List;

/**
 * Created by lenovo on 2016/2/27 0027.
 */
public class BaseTabActivity extends BaseActivity {

    private CustomPagerAdapter adapter;
    protected ViewPager viewPager;
    protected TabLayout tabLayout;

    protected TextView name;

    protected int getLayout(){
        return R.layout.activity_common_tab;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        //this.community = getIntent().getParcelableExtra("community");
        //this.RESOURCE_INDEX = getIntent().getIntExtra("RESOURCE_INDEX",0);

        name = (TextView)findViewById(R.id.name);

        setupToolbar();
        setActionBarTitle("default");

        setup();
        initViews();
        //initDatas();
    }

    @Override
    public void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            if(name != null){
                name.setText(title);
            }
        }
    }

    protected void initViews(){}

    protected void setup(){
        //
        // 社群访问权限，如果用户不满足访问权限，则禁止访问
        //adapter = new CommunityPagerAdapter(this.getSupportFragmentManager());
        adapter = new CustomPagerAdapter(this.getSupportFragmentManager());

        //ViewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3); //默认加载三页
        //viewPager.setCurrentItem(0);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
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
