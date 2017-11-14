package com.wx.base.app.ui.fragment;

import android.content.res.Configuration;
import android.support.annotation.CallSuper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import android.view.View;

import com.wx.base.R;
import com.wx.base.app.ui.adapter.recycler.BaseAdapter;

/**
 * Created by lenovo on 2016/2/27 0027.
 */
public abstract class BaseRecyclerFragment extends BaseFragment {

    protected final String TAG = "wx.base.fragment.recycler";

    //@Bind(R.id.recycler)
    protected RecyclerView recyclerView;
    //@Bind(R.id.swipeLayout)
    protected SwipeRefreshLayout swipeLayout;

    //

    protected BaseAdapter adapter;
    protected RecyclerView.LayoutManager layoutManager;
    protected RecyclerView.ItemDecoration itemDecoration;

    protected boolean PAGE_LOADING = true;

    protected int TOTAL_COUNTER = 100;
    protected int REQUEST_COUNT = 10;//Constants.DB.PAGE_SIZE;

    //
    protected int mCurrentCounter = 0;
    protected int mCurrentPage = 0;

    @Override
    protected int getLayout() {
        return R.layout.fragment_common_recyclerview;
    }

    @Override
    @CallSuper
    protected void bindViews(View view) {
        super.bindViews(view);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
    }

    //protected void initBundle(){};
    //protected abstract void initViews();
    //protected abstract void initDatas();
    protected void requestData() {
    }

    protected abstract void requestNext();

    @Override
    @CallSuper
    protected void setup() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(getDefaultLayoutManager());
        recyclerView.addItemDecoration(itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        if (adapter != null) {
            BaseAdapter madapter = adapter;
            adapter = null;
            setRecyclerAdapter(madapter);
        }
        // adapter = new ProductAdapter(mContext);
        // recyclerView.setAdapter(adapter);
        enableSwipe(true);
        enableLoadMore();
    }

    protected void enableSwipe(boolean enabled) {
        if (swipeLayout == null) {
            return;
        }
        swipeLayout.setEnabled(enabled);
        if (enabled) {
            swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    enableLoadMore();
                    requestData();
                }
            });
        } else {
            swipeLayout.setOnRefreshListener(null);
        }
    }

    protected void setSwipeRefresh(boolean refreshing) {
        if (swipeLayout != null)
            swipeLayout.setRefreshing(refreshing);
    }

    /**
     * Provide the RecyclerView.Adapter for the recycler view.
     */
    protected void setRecyclerAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
        if (recyclerView != null) {
            recyclerView.setAdapter(adapter);
        }
    }

    /**
     * Get the RecyclerView.Adapter associated with this fragment's RecyclerView.
     */
    protected BaseAdapter getRecyclerAdapter() {
        return this.adapter;
    }


    protected void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        if (recyclerView != null) {
            recyclerView.setLayoutManager(layoutManager);
        }
    }

    protected RecyclerView.LayoutManager getDefaultLayoutManager() {
        return new LinearLayoutManager(getActivity());//,LinearLayoutManager.VERTICAL, false);
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        if (layoutManager != null) {
            return layoutManager;
        }
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {//ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            return new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        } else {
            return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        }
        //if(layoutManager == null){
        //    layoutManager = new LinearLayoutManager(getActivity());//,LinearLayoutManager.VERTICAL, false);
        //}
        //return layoutManager;
    }


    protected void enableLoadMore() {
        if (recyclerView == null) {
            return;
        }

        if (!PAGE_LOADING) {
            return;
        }

        recyclerView.clearOnScrollListeners();
        //recyclerView.addOnScrollListener(mOnScrollListener);
    }

    protected void disableLoadMore() {
        if (recyclerView == null) {
            return;
        }
        recyclerView.clearOnScrollListeners();

    }

    @Override
    public void onDestroyView() {
        //mRecycler.removeOnItemTouchListener(mDefaultOnItemTouchListener);
        super.onDestroyView();
        recyclerView = null;
        layoutManager = null;
        adapter = null;
    }
}
