package com.wx.base.app.ui.holder;

import android.content.Context;
import android.view.View;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.wx.base.app.ui.adapter.recycler.BaseRecyclerViewAdapter;

/**
 * Created by lenovo on 2015/10/13 0013.
 */
public class BaseUltimateHolder<T>  extends UltimateRecyclerviewViewHolder {
    protected final String TAG = "wx.ui.holder.base";
    protected Context mContext;
    protected BaseRecyclerViewAdapter.IItemOperate listener = null;
    //public View view;

    public BaseUltimateHolder(View itemView) {
        super(itemView);
        //this.view = itemView;
        this.mContext = itemView.getContext();
    }

    public BaseUltimateHolder(View itemView, BaseRecyclerViewAdapter.IItemOperate listener) {
        super(itemView);
        //this.view = itemView;
        this.listener = listener;
    }

    //public BaseHolder(ViewGroup parent){
    //    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    //    View itemView = inflater.inflate(R.layout.item_im_message, parent, false);
    //    if (itemView == null) {
    //        throw new IllegalArgumentException("itemView may not be null");
    //    }
    //    this.itemView = itemView;
    //}

    public boolean bindViewHolder(T entity, int position){
        return false;
    };


    public boolean bindViewHolder(T entity, int position, String str){
        if(bindViewHolder(entity,position)){
            return true;
        }
        return false;
    };
}
