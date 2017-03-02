package com.ecity.wangfeng.lovedoudou.api.photo;


import com.ecity.wangfeng.lovedoudou.common.RequestCallBack;

import rx.Subscription;

/**
 * @version 1.0
 * Created by Administrator on 2016/11/29.
 */

public interface PhotoModuleApi<T> {

    Subscription getPhotoList(RequestCallBack<T> callBack, int size, int startPage);

}
