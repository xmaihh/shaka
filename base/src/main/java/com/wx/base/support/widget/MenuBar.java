package com.wx.base.support.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuItemImpl;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wx.base.R;
import com.wx.base.app.ui.activity.BaseActivity;
import com.wx.base.project.model.action.ClickAction;
import com.wx.base.project.module.predux.Store;
import com.wx.base.support.widget.menu.SmartMenu;
import com.wx.base.support.widget.menu.SmartMenuPresenter;

import org.w3c.dom.Text;

/**
 * Created by alex on 16-12-13.
 */

public class MenuBar extends LinearLayout{
    /**
     * MenuBar 实现渲染底部菜单组件
     * 菜单生成，使用 R.Menu
     *
     * 默认添加返回菜单
     * 同时支持文本和图片菜单
     *
     * 支持分组菜单
     */

    protected Store store = new Store();
    public Store getStore(){
        return this.store;
    }

    private static final int PRESENTER_NAVIGATION_VIEW_ID = 1;

    private final SmartMenu mMenu;
    private final SmartMenuPresenter mPresenter = new SmartMenuPresenter();

    private MenuInflater mMenuInflater;

    public MenuBar(Context context) {
        this(context,null);
    }

    public MenuBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MenuBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(LinearLayout.VERTICAL);
        // Create the menu
        mMenu = new SmartMenu(context);

        // Custom attributes


        //
        //mPresenter.setId(PRESENTER_NAVIGATION_VIEW_ID);
        //mPresenter.initForMenu(context, mMenu);
        //mPresenter.setItemIconTintList(itemIconTint);

        //mMenu.addMenuPresenter(mPresenter);

        //addView((View)mPresenter.getMenuView(this));
    }

    private MenuInflater getMenuInflater(){
        if(mMenuInflater == null){
            mMenuInflater = new SupportMenuInflater(getContext());
        }
        return mMenuInflater;
    }

    protected boolean useBackMenu = true;

    public void useBack(boolean useBack){
        this.useBackMenu = useBack;
    }

    public void inflateMenu(int resId){

        boolean hasback = useBackMenu;

        if(resId != 0)
        getMenuInflater().inflate(resId, mMenu);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout layout1 = new LinearLayout(getContext());
        layout1.setBackgroundResource(R.drawable.bg_tabbar);
        layout1.setOrientation(HORIZONTAL);
        layout1.setGravity(Gravity.CENTER);
        layout1.setLayoutParams(params);
        LinearLayout layout2 = new LinearLayout(getContext());
        layout2.setBackgroundResource(R.drawable.bg_tabbar);
        layout2.setGravity(Gravity.CENTER);
        layout2.setOrientation(HORIZONTAL);
        if(hasback) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_menu_tp,null,false);
            TextView tv_back = (TextView)view.findViewById(R.id.title);
            tv_back.setId(android.R.id.home);
            tv_back.setText("返回");
            tv_back.setBackgroundResource(R.drawable.bg_btn_white);
            tv_back.setTextColor(getContext().getResources().getColor(R.color.colorMaintext));
            tv_back.setOnClickListener(listener);
            layout1.addView(view);
        }

        if((hasback?1:0)+mMenu.size() <= 2){
            //
            for(MenuItemImpl menuItem:mMenu.getVisibleItems()){
                View view = LayoutInflater.from(getContext()).inflate(R.layout.item_menu_tp,null,false);
                TextView tv_menu = (TextView)view.findViewById(R.id.title);
                tv_menu.setTag(menuItem);
                tv_menu.setText(menuItem.getTitle());
                tv_menu.setOnClickListener(listener);
                layout1.addView(view);
            }
        }else{


            for(MenuItemImpl menuItem:mMenu.getVisibleItems()){
                View view = LayoutInflater.from(getContext()).inflate(R.layout.item_menu_tp,null,false);
                TextView tv_menu = (TextView)view.findViewById(R.id.title);
                tv_menu.setTag(menuItem);
                tv_menu.setText(menuItem.getTitle());
                tv_menu.setOnClickListener(listener);
                if(hasback){
                    layout2.addView(view);
                }else {
                    layout1.addView(view);
                }
            }

        }

        if(layout2.getChildCount()>0) {
            addView(layout2, params);
            layout2.setWeightSum(layout2.getChildCount());
        }
        addView(layout1,params);
    }

    private OnItemSelectedListener mListener;

    interface OnItemSelectedListener{
        public boolean onItemSelected(MenuItem item);
    }

    OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            //
            if(view.getId() == android.R.id.home){
                if(getContext() instanceof BaseActivity){
                    ((BaseActivity)getContext()).onBackPressed();
                }
                return;
            }
            ClickAction action = new ClickAction();
            action.setEntity(view.getTag());
            action.setView(view);
            if(view.getTag() != null && view.getTag() instanceof MenuItem){
                //Toast.makeText(getContext(), "title "+((MenuItem)view.getTag()).getTitle()
                //        , Toast.LENGTH_SHORT).show();
            }else {

                //Toast.makeText(getContext(), "title " +  "返回"
                //        , Toast.LENGTH_SHORT).show();
            }

            getStore().dispatch(action);

        }
    };


}
