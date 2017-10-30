package com.wx.base.project.module.predux;

/**
 * Created by alex on 16-11-29.
 */

public class Store {

    /**
     * 被观察者，包含观察对象列表，父节点
     */
    DataObservable mObservable = new DataObservable();
    /**
     * 默认处理器，处理子节点上报的动作事件
     */
    Reducer reducer = new DefaultReducer(mObservable);

    public DataObserver getObserver(){
        return reducer;
    }

    public void setReducer(Reducer reducer){
        this.reducer = reducer;
    }

    public Reducer getReducer(){
        return this.reducer;
    }

    public final boolean hasReducers(){
        return mObservable.hasObservers();
    }

    public void addReducer(DataObserver reducer){
        mObservable.registerObserver(reducer);
    }


    public void removeReducer(DataObserver reducer){
        mObservable.unregisterObserver(reducer);
    }

    public void post(Object event){
        mObservable.post(event);
    }

    public void dispatch(Action action){
        mObservable.dispatch(action);
    }

    public void dispatchOnThread(Action action){
        dispatch(action);
    }

    public void dispatchSelf(Action action){
        if(reducer != null){
            reducer.reduce(action);
        }
    }



}
