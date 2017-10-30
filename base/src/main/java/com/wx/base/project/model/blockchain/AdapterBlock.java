package com.wx.base.project.model.blockchain;

import com.wx.base.app.ui.adapter.recycler.BaseRecyclerAdapter;

/**
 * Created by alex on 16-11-30.
 */

public class AdapterBlock extends Block {
    int type;
    BaseRecyclerAdapter adapter;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public BaseRecyclerAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(BaseRecyclerAdapter adapter) {
        this.adapter = adapter;
    }
}
