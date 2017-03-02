package com.ecity.wangfeng.lovedoudou.api.news;


import com.ecity.wangfeng.lovedoudou.common.RequestCallBack;
import com.ecity.wangfeng.lovedoudou.entity.news.NewsChannelTable;

import rx.Subscription;

/**
 * @version 1.0
 * Created by Administrator on 2016/11/23.
 */

public interface NewsChannelApi<T> {

    Subscription loadNewsChannels(RequestCallBack<T> callBack);

    void swapDB(int fromPosition, int toPosition);

    void updateDB(NewsChannelTable newsChannelTable, boolean isChannelMine);

}
