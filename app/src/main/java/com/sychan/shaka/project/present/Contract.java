package com.sychan.shaka.project.present;

import com.sychan.shaka.project.entity.model.ReleaseTask;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * Created by sychan on 2017-11-11.
 * Function：
 */
public class Contract {
    public interface Presenter {
        void getReaseTask(int type, int page);

        Subscription subscribe(Observer<List<ReleaseTask>> observer, Observable<List<ReleaseTask>> observable);
    }

    public interface view {
        void loadonCompleted();

        void loadonError();

        void updateData(List<ReleaseTask> task);
    }
}
