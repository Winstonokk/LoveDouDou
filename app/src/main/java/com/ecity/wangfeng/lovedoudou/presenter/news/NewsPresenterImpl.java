package com.ecity.wangfeng.lovedoudou.presenter.news;


import com.ecity.wangfeng.lovedoudou.api.news.NewsModuleApi;
import com.ecity.wangfeng.lovedoudou.api.news.NewsModuleApiImpl;
import com.ecity.wangfeng.lovedoudou.entity.news.NewsChannelTable;
import com.ecity.wangfeng.lovedoudou.presenter.base.BasePresenterImpl;
import com.ecity.wangfeng.lovedoudou.view.news.NewsView;

import java.util.List;


/**
 *  @version 1.0
 */
public class NewsPresenterImpl extends BasePresenterImpl<NewsView,List<NewsChannelTable>> implements NewsPresenter{

    private NewsModuleApi<List<NewsChannelTable>> moduleApi;

    public NewsPresenterImpl(){
        moduleApi = new NewsModuleApiImpl();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        loadNewsChannelFromDB();
    }

    @Override
    public void success(List<NewsChannelTable> data) {
        super.success(data);
        mView.initViewPager(data);
    }

    @Override
    public void loadNewsChannels() {
        loadNewsChannelFromDB();
    }

    private void loadNewsChannelFromDB(){
        mSubscription = moduleApi.loadNewsChannel(this);
    }
}
