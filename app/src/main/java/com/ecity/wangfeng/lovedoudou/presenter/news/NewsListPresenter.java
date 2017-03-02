package com.ecity.wangfeng.lovedoudou.presenter.news;

import com.ecity.wangfeng.lovedoudou.presenter.BasePresenter;

/**
 * @version 1.0
 */
public interface NewsListPresenter extends BasePresenter {

    void setNewsTypeAndId(String newsType, String newsId);

    void firstLoadData();

    void refreshData();

    void loadMore();
}
