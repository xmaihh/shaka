package com.wx.base.support.widget.menu;

import android.content.Context;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuView;
import android.widget.LinearLayout;

/**
 * Created by alex on 16-12-13.
 */

public class TempMenuView extends LinearLayout implements MenuView {
    public TempMenuView(Context context) {
        super(context);
    }

    @Override
    public void initialize(MenuBuilder menu) {

    }

    @Override
    public int getWindowAnimations() {
        return 0;
    }
}
