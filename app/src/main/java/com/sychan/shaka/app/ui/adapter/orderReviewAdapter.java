package com.sychan.shaka.app.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.sychan.shaka.app.ui.holder.orderReviewHolder;
import com.sychan.shaka.project.entity.model.OrderTake;
import com.wx.base.app.ui.adapter.recycler.BaseRecyclerAdapter;
import com.wx.base.app.ui.adapter.recycler.BaseRecyclerViewAdapter;
import com.wx.base.app.ui.holder.BaseHolder;

import java.util.Date;

/**
 * Created by sychan on 2017-12-02.
 * Function：
 */
public class orderReviewAdapter extends BaseRecyclerAdapter<OrderTake, BaseHolder<OrderTake>>
        implements BaseRecyclerViewAdapter.OnItemClickListener<OrderTake> {
    public orderReviewAdapter(Context mContext) {
        super(mContext);
        for (int i = 0; i < 56; i++) {
            OrderTake task = new OrderTake();
            task.setTaskId(i + "内容content内容");
            task.setState(i);
            task.setFinishDate(new Date());
            items.add(task);
        }
        updateAll(items);
    }

    @Override
    public BaseHolder<OrderTake> onCreateViewHolder(ViewGroup parent, int viewType) {
        return orderReviewHolder.newInstance(parent, this);
    }

    @Override
    public void onBindViewHolder(BaseHolder<OrderTake> holder, int position) {
        OrderTake prepare = items.get(position);
        holder.bindViewHolder(prepare, position);
    }

    @Override
    public void onItemClick(View view, OrderTake item, int position) {
    }
}
