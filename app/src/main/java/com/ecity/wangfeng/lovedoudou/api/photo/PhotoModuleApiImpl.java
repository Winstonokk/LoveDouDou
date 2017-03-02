package com.ecity.wangfeng.lovedoudou.api.photo;


import com.ecity.wangfeng.lovedoudou.common.HostType;
import com.ecity.wangfeng.lovedoudou.common.RequestCallBack;
import com.ecity.wangfeng.lovedoudou.entity.photo.GirlData;
import com.ecity.wangfeng.lovedoudou.entity.photo.PhotoGirl;
import com.ecity.wangfeng.lovedoudou.service.PhotoService;
import com.ecity.wangfeng.lovedoudou.util.OkHttpUtil;
import com.ecity.wangfeng.lovedoudou.util.RetrofitManager;
import com.ecity.wangfeng.lovedoudou.util.RxJavaCustomTransform;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;

/**
 * @version 1.0
 * Created by Administrator on 2016/11/29.
 */

public class PhotoModuleApiImpl implements PhotoModuleApi<List<PhotoGirl>> {

    @Override
    public Subscription getPhotoList(final RequestCallBack<List<PhotoGirl>> callBack, int size, int startPage) {
        return RetrofitManager.getInstance(HostType.GANK_GIRL_PHOTO)
                              .createService(PhotoService.class)
                              .getPhotoList(OkHttpUtil.getCacheControl(), size, startPage)
                              .flatMap(new Func1<GirlData, Observable<PhotoGirl>>() {
                    @Override
                    public Observable<PhotoGirl> call(GirlData girlData) {
                        return Observable.from(girlData.getResults());
                    }
                })
                              .toList()
                              .compose(RxJavaCustomTransform.<List<PhotoGirl>>defaultSchedulers())
                              .subscribe(new Subscriber<List<PhotoGirl>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<PhotoGirl> photoGirls) {
                        callBack.success(photoGirls);
                    }
                });
    }

}
