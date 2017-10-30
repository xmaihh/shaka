package com.wx.base.app.ui.adapter.recycler;

import android.content.Context;
import android.view.View;

import com.wx.base.app.ui.holder.BaseHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2015/8/5 0005.
 */
public abstract class BaseRecyclerViewAdapter<T, VH extends BaseHolder> extends BaseAdapter<VH>{//RecyclerView.Adapter<VH>{

    protected List<T> items = new ArrayList<T>();

    protected OnItemClickListener<T> listener;

    public interface OnItemClickListener<T> {
        void onItemClick(View view,T item, int position);
    }

    public BaseRecyclerViewAdapter(Context mContext){
        super(mContext);
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener){
        this.listener = listener;
    }

    public T getItem(int position){
        return this.items.get(position);
    }

    public void addHead(T item){
        this.items.add(0,item);
        itemInserted(0);
    }

    public void addPos(int pos,T item){
        this.items.add(pos,item);
        itemInserted(pos);
    }

    public void add(T item){
        this.items.add(item);
//        notifyDataSetChanged();
        itemInserted(items.size());
    }

    public void addAllHead(List<T> items){
        this.items.addAll(0, items);
        notifyDataSetChanged();
    }

    public void addAllPos(int pos, List<T> items){
        this.items.addAll(pos, items);
    }

    public void addAll(List<T> items){
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void updateAll(List<T> items){
        this.items = items;
        notifyDataSetChanged();
    }

    public void removeAll(){
        this.items.clear();
        notifyDataSetChanged();
    }
    public void remove(int i){
        this.items.remove(i);
        itemRemoved(i);
    }
    //
    protected void itemInserted(int position){
        notifyItemInserted(position);
    }

    protected void itemRemoved(int position){
        notifyItemRemoved(position);
    }

    protected void itemChanged(int position){
        notifyItemChanged(position);
    }
    //-------------------------------------------------//

    //@Override
    //public T onCreateViewHolder(ViewGroup parent, int i) {
    //    View itemView = LayoutInflater.from(parent.getContext())
    //            .inflate(R.layout.item_community_resource, parent, false);
    //    return null;//new CommunityRecyclerAdapter.ViewHolder(itemView);
    //}

    //@Override
    //public void onBindViewHolder(T t, int i) {
    //}

    @Override
    public int getItemCount() {
        return items.size();
    }



    public interface IItemOperate{
        void onClick(int position);
    }
}
