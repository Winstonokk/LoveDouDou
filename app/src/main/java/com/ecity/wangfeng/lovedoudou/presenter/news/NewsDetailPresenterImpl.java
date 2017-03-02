package com.ecity.wangfeng.lovedoudou.presenter.news;

import com.ecity.wangfeng.lovedoudou.api.news.NewsDetailModuleApi;
import com.ecity.wangfeng.lovedoudou.api.news.NewsDetailModuleApiImpl;
import com.ecity.wangfeng.lovedoudou.entity.news.NewsDetail;
import com.ecity.wangfeng.lovedoudou.presenter.base.BasePresenterImpl;
import com.ecity.wangfeng.lovedoudou.view.news.NewsDetailView;
import com.socks.library.KLog;

/**
 * @version 1.0
 */
public class NewsDetailPresenterImpl extends BasePresenterImpl<NewsDetailView,NewsDetail> implements NewsDetailPresenter{

    private final String TAG = "NewsDetailPresenterImpl";

    private NewsDetailModuleApi<NewsDetail> moduleApi;

    private String mPostId;

    public NewsDetailPresenterImpl(){
        moduleApi = new NewsDetailModuleApiImpl();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        loadNewsDetail();
    }

    @Override
    public void setPostId(String postId) {
        mPostId = postId;
    }

    @Override
    public void success(NewsDetail data) {
        super.success(data);
        KLog.d(TAG, data.toString());
        mView.showNewsContent(data);
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }

    public void loadNewsDetail(){
        mSubscription = moduleApi.getNewsDetail(this,mPostId);
    }
}
