package com.wx.base.project.module.predux;

import android.database.Observable;

/**
 * Created by alex on 16-11-29.
 */

public class DataObservable extends Observable<DataObserver> {
    public boolean hasObservers() {
        return !mObservers.isEmpty();
    }

    public void notifyChanged() {
        // since onChanged() is implemented by the app, it could do anything, including
        // removing itself from {@link mObservers} - and that could cause problems if
        // an iterator is used on the ArrayList {@link mObservers}.
        // to avoid such problems, just march thru the list in the reverse order.
        for (int i = mObservers.size() - 1; i >= 0; i--) {
            //mObservers.get(i).onChanged();
        }
    }


    public void post(Object event){
        for (int i = mObservers.size() - 1; i >= 0; i--) {
            mObservers.get(i).onEvent(event);
        }
    }

    public void dispatch(Action action){
        for (int i = mObservers.size() - 1; i >= 0; i--) {
            mObservers.get(i).reduce(action);
        }
    }

}
