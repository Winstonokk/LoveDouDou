package com.ecity.wangfeng.lovedoudou.api.photo;


import com.ecity.wangfeng.lovedoudou.common.RequestCallBack;

import rx.Subscription;

/**
 * @version 1.0
 * Created by Administrator on 2016/11/30.
 */

public interface PhotoDetailModuleApi<T> {

    Subscription saveImageAndGetImageUri(RequestCallBack<T> callBack, String url);

}
