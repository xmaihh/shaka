package com.sychan.shaka.app.ui.holder;

import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sychan.shaka.R;
import com.sychan.shaka.project.entity.model.OrderTake;
import com.sychan.shaka.project.entity.model.ReleaseTask;
import com.wx.base.app.ui.adapter.recycler.BaseRecyclerViewAdapter;
import com.wx.base.app.ui.holder.BaseHolder;

import butterknife.BindView;

/**
 * Created by sychan on 2017-12-02.
 * Function：
 */
public class orderReviewHolder extends BaseHolder<OrderTake>
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
    @BindView(R.id.btn_stop)
    Button btnStop;
    @BindView(R.id.btn_edit)
    Button btnEdit;
    @BindView(R.id.btn_pass)
    Button btnPass;
    @BindView(R.id.btn_complete)
    Button btnComplete;
    @BindView(R.id.card_main_1_1)
    CardView cardMain11;

    public orderReviewHolder(View itemView, BaseRecyclerViewAdapter.OnItemClickListener<OrderTake> listener) {
        super(itemView, listener);
    }

    public static orderReviewHolder newInstance(ViewGroup parent, BaseRecyclerViewAdapter.OnItemClickListener<OrderTake> listener) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv_order_review, parent, false);
        return new orderReviewHolder(v, listener);
    }

    @Override
    public boolean bindViewHolder(OrderTake entity, int position) {
        tvTaskId.setText(entity.getState()+"sdf");
        tvContent.setText(entity.getTaskId()+111);
        tvDeadline.setText(entity.getFinishDate().toString()+"");

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        itemView.setTag(R.id.order_review_entity, entity);
        itemView.setTag(R.id.order_review_position, position);
        return super.bindViewHolder(entity, position);
    }

    @Override
    public void onClick(View v) {
        listener.onItemClick(v, (OrderTake) v.getTag(R.id.order_review_entity), (int) v.getTag(R.id.order_review_position));
    }

    @Override
    public boolean onLongClick(View v) {
        listener.onItemClick(v, (OrderTake) v.getTag(R.id.order_review_entity), (int) v.getTag(R.id.order_review_position));
        return false;
    }
}
