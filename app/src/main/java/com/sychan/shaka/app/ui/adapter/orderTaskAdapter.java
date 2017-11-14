package com.sychan.shaka.app.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.sychan.shaka.app.ui.holder.orderTaskHolder;
import com.sychan.shaka.project.config.SimpleBackHelper;
import com.sychan.shaka.project.config.SimpleBackPage;
import com.sychan.shaka.project.entity.model.ReleaseTask;
import com.sychan.shaka.project.repository.OpenDisplayDataRepository;
import com.sychan.shaka.support.utils.ToastUtil;
import com.wx.base.app.ui.adapter.recycler.BaseRecyclerViewAdapter;
import com.wx.base.app.ui.holder.BaseHolder;

import java.util.Date;

/**
 * Created by sychan on 2017-11-07.
 * Function：
 */
public class orderTaskAdapter extends BaseRecyclerViewAdapter<ReleaseTask, BaseHolder<ReleaseTask>>
        implements BaseRecyclerViewAdapter.OnItemClickListener<ReleaseTask> {
    public orderTaskAdapter(Context mContext) {
        super(mContext);
//        for (int i = 0; i < 56; i++) {
//            ReleaseTask task = new ReleaseTask();
//            task.setTitle("标题测试标题");
//            task.setDeadline(new Date());
//            task.setUnitprice(i + 1);
//            items.add(task);
//        }
//        updateAll(items);
    }

    @Override
    public BaseHolder<ReleaseTask> onCreateViewHolder(ViewGroup parent, int viewType) {
        return orderTaskHolder.newInstance(parent, this);
    }

    @Override
    public void onBindViewHolder(BaseHolder<ReleaseTask> holder, int position) {
        ReleaseTask prepare = items.get(position);
        holder.bindViewHolder(prepare, position);
    }

    @Override
    public void onItemClick(View view, ReleaseTask item, int position) {
        Logger.d("点击了第" + position + "条" + item.toString());
        OpenDisplayDataRepository.openOrderDisplayData(mContext, item);
    }
}
