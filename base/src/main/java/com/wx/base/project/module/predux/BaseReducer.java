package com.wx.base.project.module.predux;

/**
 * Created by alex on 16-11-30.
 */

public abstract class BaseReducer extends Reducer {

    protected final String TAG = "wx.reducer.base";
    DataObservable mObservable;

    public BaseReducer(){}

    public BaseReducer(Store presenter){
        this.mObservable = presenter.mObservable;
    }

    public BaseReducer(DataObservable mObservable){
        this.mObservable = mObservable;
    }


    @Override
    public void reduce(Action action) {
        if(!onReduce(action)){
            if(mObservable != null) {
                mObservable.dispatch(action);
            }
        }
    }

    public abstract boolean onReduce(Action action);
}
