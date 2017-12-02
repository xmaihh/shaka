package com.sychan.shaka.app.ui.fragment;

import android.util.Log;

import com.sychan.shaka.R;
import com.sychan.shaka.app.ui.adapter.orderTaskAdapter;
import com.sychan.shaka.project.entity.model.ReleaseTask;
import com.sychan.shaka.project.present.AllTaskPresenter;
import com.wx.base.app.ui.fragment.BaseRecyclerFragment;
import com.wx.base.support.android.pagestate.StateView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * @author sychan
 * @date 2017-11-07
 * Function：
 */
public class orderTakeFragment extends BaseRecyclerFragment {

    private orderTaskAdapter adapter;
    private AllTaskPresenter presenter;

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
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void enableLoadMore() {
        super.enableLoadMore();
//        loadData();
//        pageStatePresenter.changeState(StateView.PageState.Loading);
    }

    private void loadData() {
        BmobQuery<ReleaseTask> query = new BmobQuery<>();
//        query.addWhereEqualTo("ispublish", "true");
        query.setLimit(50);
        query.findObjects(new FindListener<ReleaseTask>() {
            @Override
            public void done(List<ReleaseTask> list, BmobException e) {
                if (e == null) {
                    adapter.updateAll(list);
                    setSwipeRefresh(false);
                    pageStatePresenter.changeState(StateView.PageState.Content);
                } else {
                    Log.d("521", e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }


}
