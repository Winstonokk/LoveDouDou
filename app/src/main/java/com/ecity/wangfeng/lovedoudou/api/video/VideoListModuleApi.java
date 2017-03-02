package com.ecity.wangfeng.lovedoudou.api.video;


import com.ecity.wangfeng.lovedoudou.common.RequestCallBack;

import rx.Subscription;

/**
 *  视频模块
 *  @version 1.0
 */
public interface VideoListModuleApi<T> {

    Subscription getVideoList(RequestCallBack<T> callBack, String videoType, int startPage);
}

