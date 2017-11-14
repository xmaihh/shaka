package com.sychan.shaka.project.api;

import com.sychan.shaka.project.config.orderType;
import com.sychan.shaka.project.entity.model.ReleaseTask;
import com.sychan.shaka.project.entity.model.helper.DataHelper;

import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by sychan on 2017-11-11.
 * Function：
 */
public class QueryApi {
    public static QueryApi instance = null;

    public static QueryApi getInstance() {
        if (instance == null) {
            synchronized (QueryApi.class) {
                if (instance == null) {
                    instance = new QueryApi();
                }
            }
        }
        return instance;
    }

    public Observable<List<ReleaseTask>> getReleaseTask(int type, int page) {
        return makeObservable(getTask(type, page))
                .subscribeOn(Schedulers.computation()); // note: do not use Schedulers.io()
    }


    public Callable<List<ReleaseTask>> getTask(final int type, final int page) {
        return new Callable<List<ReleaseTask>>() {
            @Override
            public List<ReleaseTask> call() throws Exception {
                return DataHelper.getReleaseTask(page);
            }
        };

    }

    private <T> Observable<T> makeObservable(final Callable<T> func) {
        return Observable.create(
                new Observable.OnSubscribe<T>() {
                    @Override
                    public void call(Subscriber<? super T> subscriber) {
                        try {
                            subscriber.onNext(func.call());
                            subscriber.onCompleted();
                        } catch (Exception ex) {
                            subscriber.onError(ex);
                        }
                    }
                });
    }
}
