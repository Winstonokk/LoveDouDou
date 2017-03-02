package com.ecity.wangfeng.lovedoudou.presenter.news;

import com.ecity.wangfeng.lovedoudou.entity.news.NewsChannelTable;
import com.ecity.wangfeng.lovedoudou.presenter.BasePresenter;

/**
 *
 * @version 1.0
 * Created by Administrator on 2016/11/22.
 */

public interface NewsChannelPresenter extends BasePresenter {

    void onItemSwap(int fromPosition, int toPosition);

    void onItemAddOrRemove(NewsChannelTable newsChannel, boolean isChannelMine);

    void selectIndex(String newsChannelId);
}
