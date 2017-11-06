package com.sychan.shaka.app.ui.activity;

import com.sychan.shaka.R;
import com.sychan.shaka.app.ui.fragment.NewTaskFragment;
import com.sychan.shaka.app.ui.fragment.NewTestFragment;
import com.sychan.shaka.app.ui.fragment.WaterMarkFragment;
import com.wx.base.app.ui.activity.BaseTabActivity;
import com.wx.base.project.model.ResourceMap;

import java.util.ArrayList;

public class NewTaskActivity extends BaseTabActivity {
    private ArrayList<ResourceMap> mMaps;

    @Override
    protected int getLayout() {
        return R.layout.activity_newtask;
    }

    @Override
    protected void initViews() {
        super.initViews();
        mMaps = new ArrayList<>();
        mMaps.add(new ResourceMap("test", new NewTaskFragment()));
        mMaps.add(new ResourceMap("gallery", new NewTestFragment()));
        mMaps.add(new ResourceMap("watermark", new WaterMarkFragment()));
        updateViewPager(mMaps);
    }
}

