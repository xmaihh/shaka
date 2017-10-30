package com.wx.base.support.widget.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wx.base.R;

/**
 * Created by alex on 16-12-13.
 */

public class SmartMenuItemView extends LinearLayout implements MenuView.ItemView{

    private final TextView mTextView;

    public SmartMenuItemView(Context context) {
        this(context,null);
    }

    public SmartMenuItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmartMenuItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(getContext())
                .inflate(R.layout.widget_item_menu, this, true);

        mTextView = (TextView)findViewById(R.id.title);
        initAttrs();
        setup();
    }


    protected void initAttrs(){

    }

    protected void setup(){


    }


    @Override
    public void initialize(MenuItemImpl itemData, int menuType) {

    }

    @Override
    public MenuItemImpl getItemData() {
        return null;
    }

    @Override
    public void setTitle(CharSequence title) {

    }

    @Override
    public void setCheckable(boolean checkable) {

    }

    @Override
    public void setChecked(boolean checked) {

    }

    @Override
    public void setShortcut(boolean showShortcut, char shortcutKey) {

    }

    @Override
    public void setIcon(Drawable icon) {

    }

    @Override
    public boolean prefersCondensedTitle() {
        return false;
    }

    @Override
    public boolean showsIcon() {
        return false;
    }
}
