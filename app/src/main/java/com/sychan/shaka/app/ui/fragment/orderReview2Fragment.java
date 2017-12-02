package com.sychan.shaka.app.ui.fragment;

import com.sychan.shaka.R;
import com.sychan.shaka.app.ui.adapter.orderReviewAdapter;
import com.wx.base.app.ui.fragment.BaseRecyclerFragment;

/**
 * Created by sychan on 2017-12-02.
 * Function：
 */
public class orderReview2Fragment extends BaseRecyclerFragment {
    @Override
    protected void requestNext() {
    }

    @Override
    protected void setup() {
        adapter = new orderReviewAdapter(mContext);
        recyclerView.setAdapter(adapter);
        super.setup();
    }
}
