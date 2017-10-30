package com.wx.base.support.widget.menu;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.ViewGroup;

import com.wx.base.app.ui.adapter.recycler.BaseAdapter;
import com.wx.base.app.ui.adapter.recycler.BaseRecyclerAdapter;

/**
 * Created by alex on 16-12-13.
 */

public class SmartMenuAdapter extends BaseRecyclerAdapter<MenuItemImpl,SmartMenuHolder>{

    public SmartMenuAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public SmartMenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SmartMenuHolder(parent);
    }

    @Override
    public void onBindViewHolder(SmartMenuHolder holder, int position) {
        holder.name.setText(getItem(position).getTitle());
    }

    public Bundle createInstanceState(){
        return null;
    }

    public void restoreInstanceState(Bundle state){

    }


    public void update(MenuBuilder menu){
        prepareMenuItems(menu);
        notifyDataSetChanged();
    }

    private void prepareMenuItems(MenuBuilder mMenu){

        //for(int i = 0,totalSize = mMenu.getVisibleItems().size();i<totalSize;i++){
        //    MenuItemImpl item = mMenu.getVisibleItems().get(i);
        //    add(item);
        //}
        updateAll(mMenu.getVisibleItems());
    }
}
