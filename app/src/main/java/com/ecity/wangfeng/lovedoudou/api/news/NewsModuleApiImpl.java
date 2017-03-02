package com.ecity.wangfeng.lovedoudou.api.news;


import com.ecity.wangfeng.lovedoudou.MyApplication;
import com.ecity.wangfeng.lovedoudou.R;
import com.ecity.wangfeng.lovedoudou.common.RequestCallBack;
import com.ecity.wangfeng.lovedoudou.entity.news.NewsChannelTable;
import com.ecity.wangfeng.lovedoudou.service.localdb.NewsChannelTableManager;
import com.ecity.wangfeng.lovedoudou.util.RxJavaCustomTransform;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * @version 1.0
 */
public class NewsModuleApiImpl implements NewsModuleApi<List<NewsChannelTable>> {


    @Override
    public Subscription loadNewsChannel(final RequestCallBack<List<NewsChannelTable>> callBack) {
        return Observable.create(new Observable.OnSubscribe<List<NewsChannelTable>>() {
            @Override
            public void call(Subscriber<? super List<NewsChannelTable>> subscriber) {
                NewsChannelTableManager.initDB();
                List<NewsChannelTable> list = NewsChannelTableManager.loadNewsChannelsMine();
                subscriber.onNext(list);
                subscriber.onCompleted();
            }
        })
                .compose(RxJavaCustomTransform.<List<NewsChannelTable>>defaultSchedulers())
                .subscribe(new Subscriber<List<NewsChannelTable>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onError(MyApplication.getInstance().getString(R.string.db_error));
                    }

                    @Override
                    public void onNext(List<NewsChannelTable> newsChannelTables) {
                        callBack.success(newsChannelTables);
                    }
                });
    }
}
