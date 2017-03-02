package com.ecity.wangfeng.lovedoudou.presenter.news;

import com.ecity.wangfeng.lovedoudou.api.news.NewsListModuleApi;
import com.ecity.wangfeng.lovedoudou.api.news.NewsListModuleApiImpl;
import com.ecity.wangfeng.lovedoudou.common.LoadDataType;
import com.ecity.wangfeng.lovedoudou.common.RequestCallBack;
import com.ecity.wangfeng.lovedoudou.entity.news.NewsSummary;
import com.ecity.wangfeng.lovedoudou.presenter.base.BasePresenterImpl;
import com.ecity.wangfeng.lovedoudou.view.news.NewsListView;
import com.socks.library.KLog;

import java.util.List;


/**
 * @version 1.0
 */
public class NewsListPresenterImpl extends BasePresenterImpl<NewsListView,List<NewsSummary>>
        implements NewsListPresenter,RequestCallBack<List<NewsSummary>> {

    private NewsListModuleApi<List<NewsSummary>> moduleApi;
    private int                                  mLoadDataType;
    private String                               mNewsType,mNewsId;
    private int mStartPage;

    public NewsListPresenterImpl(){
        moduleApi = new NewsListModuleApiImpl();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        KLog.d("NewsListFragment", "NewsListPresenterImpl");
        if(mView != null){
            firstLoadData();
        }
    }

    @Override
    public void success(List<NewsSummary> data) {
        super.success(data);
        mView.setNewsList(data,mLoadDataType);
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
        mView.updateErrorView(mLoadDataType);
    }

    @Override
    public void setNewsTypeAndId(String newsType, String newsId) {
        mStartPage = 0;
        mNewsType = newsType;
        mNewsId = newsId;
    }

    @Override
    public void firstLoadData() {
        beforeRequest();
        mLoadDataType = LoadDataType.TYPE_FIRST_LOAD;
        loadNewsData();
    }

    @Override
    public void refreshData() {
        mStartPage = 0;
        mLoadDataType = LoadDataType.TYPE_REFRESH;
        loadNewsData();
    }

    @Override
    public void loadMore() {
        mStartPage += 20;
        mLoadDataType = LoadDataType.TYPE_LOAD_MORE;
        loadNewsData();
    }

    public void loadNewsData(){
        mSubscription = moduleApi.loadNews(this,mNewsType,mNewsId,mStartPage);
    }
}
