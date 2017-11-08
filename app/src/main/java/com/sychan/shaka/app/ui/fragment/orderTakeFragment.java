package com.sychan.shaka.app.ui.fragment;

import com.sychan.shaka.R;
import com.sychan.shaka.app.ui.adapter.orderTaskAdapter;
import com.wx.base.app.ui.fragment.BaseRecyclerFragment;

/**
 * @author sychan
 * @date 2017-11-07
 * Function：
 */
public class orderTakeFragment extends BaseRecyclerFragment {
    @Override
    protected void requestNext() {
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_order_take;
    }

    @Override
    protected void setup() {
        adapter = new orderTaskAdapter(mContext);
        recyclerView.setAdapter(adapter);
        super.setup();
    }

    @Override
    protected void requestData() {

    }
}
