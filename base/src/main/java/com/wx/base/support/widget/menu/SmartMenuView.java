package com.wx.base.support.widget.menu;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by alex on 16-12-13.
 */

public class SmartMenuView extends RecyclerView implements MenuView{
    public SmartMenuView(Context context) {
        this(context,null);
    }

    public SmartMenuView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SmartMenuView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
    }

    @Override
    public void initialize(MenuBuilder menu) {

    }

    @Override
    public int getWindowAnimations() {
        return 0;
    }
}
