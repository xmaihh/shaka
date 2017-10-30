package com.wx.base.support.widget.menu;

import android.view.ViewGroup;
import android.widget.TextView;

import com.wx.base.R;
import com.wx.base.app.ui.holder.BaseHolder;

/**
 * Created by alex on 16-12-13.
 */

public class SmartMenuHolder extends BaseHolder {

    public TextView name;
    public SmartMenuHolder(ViewGroup parent) {
        super(parent, R.layout.widget_item_menu);
        name = (TextView)itemView.findViewById(R.id.title);
    }
}
