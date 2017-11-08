package com.sychan.shaka.app.ui.holder;

import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sychan.shaka.R;
import com.sychan.shaka.project.entity.model.ReleaseTask;
import com.wx.base.app.ui.adapter.recycler.BaseRecyclerViewAdapter;
import com.wx.base.app.ui.holder.BaseHolder;

import butterknife.BindView;

/**
 * @author sychan
 * @date 2017-11-07
 * Function：
 */
public class orderTaskHolder extends BaseHolder<ReleaseTask>
        implements View.OnClickListener,
        View.OnLongClickListener {

    @BindView(R.id.tv_task_id)
    TextView tvTaskId;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_deadline)
    TextView tvDeadline;
    @BindView(R.id.btn_take_it_now)
    Button btnTakeItNow;
    @BindView(R.id.card_main_1_1)
    CardView cardMain11;

    public static orderTaskHolder newInstance(ViewGroup parent, BaseRecyclerViewAdapter.OnItemClickListener<ReleaseTask> listener) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv_order_take, parent, false);
        return new orderTaskHolder(itemView, listener);
    }

    public orderTaskHolder(View itemView, BaseRecyclerViewAdapter.OnItemClickListener<ReleaseTask> listener) {
        super(itemView, listener);
    }

    @Override
    public boolean bindViewHolder(ReleaseTask entity, int position) {
//        tvTaskId.setText(entity.getUnitprice());
//        tvContent.setText(entity.getTitle() + entity.getRemark());
//        tvPrice.setText(entity.getUnitprice());
//        tvDeadline.setText(entity.getDeadline().toString());

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        itemView.setTag(R.id.order_task_entity, entity);
        itemView.setTag(R.id.order_task_positon, position);

        return super.bindViewHolder(entity, position);
    }

    @Override
    public void onClick(View v) {
        listener.onItemClick(v, (ReleaseTask) v.getTag(R.id.order_task_entity), (int) v.getTag(R.id.order_task_positon));
    }

    @Override
    public boolean onLongClick(View v) {
        listener.onItemClick(v, (ReleaseTask) v.getTag(R.id.order_task_entity), (int) v.getTag(R.id.order_task_positon));
        return false;
    }
}
