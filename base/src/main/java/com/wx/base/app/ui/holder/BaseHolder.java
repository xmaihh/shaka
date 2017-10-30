package com.wx.base.app.ui.holder;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.wx.base.R;
import com.wx.base.app.ui.adapter.recycler.BaseRecyclerViewAdapter;
import com.wx.base.project.module.predux.Action;
import com.wx.base.project.module.predux.Store;
import com.wx.base.project.module.selection.Selection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;

/**
 * Created by lenovo on 2015/8/27 0027.
 */
public abstract class BaseHolder<T>  extends BaseUltimateHolder<T> {
    protected final String TAG = "wx.ui.holder.base";
    //protected Context mContext;
    protected BaseRecyclerViewAdapter.OnItemClickListener<T> listener = null;
    protected Store store = new Store();
    //public View view;
    protected int dismode;
    protected int type;
    protected Selection selection;

    public BaseHolder(ViewGroup parent, int resid){
        this(LayoutInflater.from(parent.getContext())
                .inflate(resid, parent, false));
    }

    //SuK ++
    public BaseHolder(ViewGroup parent, int resid,BaseRecyclerViewAdapter.OnItemClickListener<T> listener){
        this(LayoutInflater.from(parent.getContext())
                .inflate(resid, parent, false),listener);
    }

    public BaseHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        //this.view = itemView;
        //this.mContext = itemView.getContext();
    }

    public Store getStore(){
        return store;
    }

    public void setSelection(Selection selection){
        this.selection = selection;
    }

    public BaseHolder(View itemView, BaseRecyclerViewAdapter.OnItemClickListener<T> listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        //this.view = itemView;
        this.listener = listener;
    }

    public void setDismode(int dismode){
        this.dismode = dismode;
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
    }

    protected void dispatch(Action action){
        getStore().dispatch(action);
    }

    protected SpannableStringBuilder highLight(String source,String query){
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(source);
        try {
            Pattern p = Pattern.compile(query);
            Matcher m = p.matcher(stringBuilder);

            while (m.find()) {
                int start = m.start();
                int end = m.end();
                stringBuilder.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.colorPrimary)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return stringBuilder;
    }

}
