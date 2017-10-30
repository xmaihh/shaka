package com.wx.base.support.widget.menu;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuView;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.ViewGroup;

import com.wx.base.R;

/**
 * Created by alex on 16-12-13.
 */

public class SmartMenuPresenter implements MenuPresenter{
    private static final String STATE_HIERARCHY = "android:menu:list";
    private static final String STATE_ADAPTER = "android:menu:adapter";

    private SmartMenuView mMenuView;

    private Callback mCallback;
    private MenuBuilder mMenu;
    private int mId;

    private SmartMenuAdapter mAdapter;
    private LayoutInflater mLayoutInflater;

    private Context context;

    @Override
    public void initForMenu(Context context, MenuBuilder menu) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        mMenu = menu;

        Resources res = context.getResources();
        //参数
    }

    @Override
    public MenuView getMenuView(ViewGroup root) {

        if(mMenuView == null){
            mMenuView = (SmartMenuView)mLayoutInflater.inflate(
                    R.layout.widget_smart_menu, root, false);
            if(mAdapter == null){
                mAdapter = new SmartMenuAdapter(context);
            }

            mMenuView.setAdapter(mAdapter);
        }

        return mMenuView;
    }

    @Override
    public void updateMenuView(boolean cleared) {
        if(mAdapter != null){
            mAdapter.update(mMenu);
        }
    }

    @Override
    public void setCallback(Callback cb) {
        mCallback = cb;
    }

    @Override
    public boolean onSubMenuSelected(SubMenuBuilder subMenu) {
        return false;
    }

    @Override
    public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
        if(mCallback != null){
            mCallback.onCloseMenu(menu,allMenusAreClosing);
        }
    }

    @Override
    public boolean flagActionItems() {
        return false;
    }

    @Override
    public boolean expandItemActionView(MenuBuilder menu, MenuItemImpl item) {
        return false;
    }

    @Override
    public boolean collapseItemActionView(MenuBuilder menu, MenuItemImpl item) {
        return false;
    }

    @Override
    public int getId() {
        return mId;
    }

    public void setId(int id){
        mId = id;
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle state = new Bundle();
        if(mMenuView != null){
            SparseArray<Parcelable> hierarchy = new SparseArray<>();
            mMenuView.saveHierarchyState(hierarchy);
            state.putSparseParcelableArray(STATE_HIERARCHY, hierarchy);
        }

        if(mAdapter != null){
            state.putBundle(STATE_ADAPTER, mAdapter.createInstanceState());
        }
        return state;
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        Bundle state = (Bundle)parcelable;
        SparseArray<Parcelable> hierarchy = state.getSparseParcelableArray(STATE_HIERARCHY);
        if(hierarchy != null){
            mMenuView.restoreHierarchyState(hierarchy);
        }

        Bundle adapterState = state.getBundle(STATE_ADAPTER);
        if(adapterState != null){
            mAdapter.restoreInstanceState(adapterState);
        }
    }
}
