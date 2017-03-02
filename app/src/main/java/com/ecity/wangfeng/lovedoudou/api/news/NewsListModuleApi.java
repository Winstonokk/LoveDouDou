package com.ecity.wangfeng.lovedoudou.api.news;


import com.ecity.wangfeng.lovedoudou.common.RequestCallBack;

import rx.Subscription;

/**
 *  新闻模块 API接口
 * @version 1.0
 */
public interface NewsListModuleApi<T> {

    Subscription loadNews(RequestCallBack<T> listener, String type, String id, int startPage);

}
