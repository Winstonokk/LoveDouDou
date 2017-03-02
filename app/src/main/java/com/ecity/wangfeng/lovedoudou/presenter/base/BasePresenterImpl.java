package com.ecity.wangfeng.lovedoudou.presenter.base;

import android.support.annotation.NonNull;

import com.ecity.wangfeng.lovedoudou.common.RequestCallBack;
import com.ecity.wangfeng.lovedoudou.presenter.BasePresenter;
import com.ecity.wangfeng.lovedoudou.view.BaseView;

import rx.Subscription;

/**
 * @version 1.0
 */
public class BasePresenterImpl<T extends BaseView,E> implements BasePresenter, RequestCallBack<E> {

    protected T mView;
    protected Subscription mSubscription;

    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(@NonNull BaseView vid) {
        mView = (T) vid;
    }

    @Override
    public void onDestroy() {
        //TODO unSubscribe
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void beforeRequest() {
        if(mView != null) {
            mView.showProgress();
        }
    }

    @Override
    public void success(E data) {
        if(mView != null) {
            mView.hideProgress();
        }
    }

    @Override
    public void onError(String errorMsg) {
        if(mView != null) {
            mView.hideProgress();
            mView.showMsg(errorMsg);
        }
    }
}
