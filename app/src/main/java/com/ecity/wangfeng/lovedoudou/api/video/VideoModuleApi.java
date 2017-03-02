package com.ecity.wangfeng.lovedoudou.api.video;


import com.ecity.wangfeng.lovedoudou.common.RequestCallBack;

import rx.Subscription;

/**
 * @version 1.0
 * Created by Administrator on 2016/11/26.
 */

public interface VideoModuleApi<T> {

    Subscription getVideoChannelList(RequestCallBack<T> callBack);
}
