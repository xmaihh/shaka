package com.sychan.shaka.project.present;

import com.sychan.shaka.project.api.QueryApi;
import com.sychan.shaka.project.entity.model.ReleaseTask;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sychan on 2017-11-11.
 * Function：
 */
public class AllTaskPresenter implements Contract.Presenter {

    private QueryApi api;
    private Subscription loadingSubscription;
    private Contract.view view;

    public AllTaskPresenter(Contract.view view) {
        api = QueryApi.getInstance();
        this.view = view;
    }

    @Override
    public void getReaseTask(int type, int page) {
        Observable<List<ReleaseTask>> tasks = api.getReleaseTask(type, page);
        loadingSubscription = subscribe(gettaskObserver, tasks);
    }

    @Override
    public Subscription subscribe(Observer<List<ReleaseTask>> observer, Observable<List<ReleaseTask>> observable) {
        return observable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private Observer<List<ReleaseTask>> gettaskObserver = new Observer<List<ReleaseTask>>() {
        @Override
        public void onCompleted() {
            view.loadonCompleted();
        }

        @Override
        public void onError(Throwable throwable) {
            view.loadonError();
        }

        @Override
        public void onNext(List<ReleaseTask> releaseTasks) {
            view.updateData(releaseTasks);
        }
    };

}
