package com.wx.base.app.ui.adapter.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.wx.base.app.ui.holder.BaseHolder;
import com.wx.base.project.model.action.ItemChagneAction;
import com.wx.base.project.module.predux.Store;

/**
 * Created by alex on 16-11-29.
 */

public abstract class BaseAdapter<VH extends BaseHolder> extends RecyclerView.Adapter<VH> {
    protected final String TAG = "wx.adapter.base";
    protected final Store store = new Store();
    protected int type = 0;

    protected Context mContext;

    public BaseAdapter(Context mContext){
        this.mContext = mContext;
    }

    public Store getStore(){
        return store;
    }


    public int getType(){
        return type;
    }

    public void setType(int type){
        this.type = type;
    }

    public void itemChanged(){
        notifyDataSetChanged();
        //通知
        ItemChagneAction action = new ItemChagneAction();
        action.setType(ItemChagneAction.TYPE_CHANGED);
        getStore().dispatch(action);
    }

    protected void itemInserted(int position){
        notifyItemInserted(position);
        ItemChagneAction action = new ItemChagneAction();
        action.setType(ItemChagneAction.TYPE_INSERT);
        action.setItemCount(1);
        action.setPositionStart(position);
        getStore().dispatch(action);
    }

    protected void itemRemoved(int position){
        notifyItemRemoved(position);

        ItemChagneAction action = new ItemChagneAction();
        action.setType(ItemChagneAction.TYPE_REMOVED);
        getStore().dispatch(action);
    }

    protected void itemChanged(int position){
        notifyItemChanged(position);

        ItemChagneAction action = new ItemChagneAction();
        action.setType(ItemChagneAction.TYPE_RANGE_CHANGED);
        getStore().dispatch(action);
    }


    protected void addReducer(VH holder){
        holder.getStore().addReducer(getStore().getReducer());
    }


}
