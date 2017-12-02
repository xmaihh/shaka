package com.sychan.shaka.app.ui.fragment;

import com.sychan.shaka.R;
import com.wx.base.app.ui.fragment.BaseTabFragment;
import com.wx.base.project.model.ResourceMap;

import java.util.ArrayList;

/**
 * Created by sychan on 2017-12-02.
 * Function：
 */
public class orderReviewFragment extends BaseTabFragment {
    private ArrayList<ResourceMap> mMaps;

//    @Override
//    protected int getLayout() {
//        return R.layout.fragment_common_tab;
//    }

    @Override
    protected void initViews() {
        super.initViews();
        mMaps = new ArrayList<>();
        mMaps.add(new ResourceMap("我发起的", new orderReview2Fragment()));
        mMaps.add(new ResourceMap("我接收的", new orderReview2Fragment()));
        updateViewPager(mMaps);
    }
}
