package com.wx.base.project.module.predux;

import android.util.Log;

import com.wx.base.support.utils.XLog;

/**
 * Created by alex on 16-11-30.
 */

public class DefaultReducer extends BaseReducer {

    protected final String TAG = "wx.reducer.default";
    public DefaultReducer(Store presenter){
        this.mObservable = presenter.mObservable;
    }

    public DefaultReducer(DataObservable mObservable) {
        super(mObservable);
    }

    @Override
    public boolean onReduce(Action action) {
        XLog.v(TAG, "onreduce "+action.getClass().getSimpleName());
        return false;
    }
}
