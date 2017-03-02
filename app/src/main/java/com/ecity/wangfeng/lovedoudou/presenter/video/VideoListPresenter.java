package com.ecity.wangfeng.lovedoudou.presenter.video;

import com.ecity.wangfeng.lovedoudou.presenter.BasePresenter;

/**
 *  @version 1.0
 */
public interface VideoListPresenter extends BasePresenter {

    //设置视频的分类
    void setVideoType(String videoType);

    //刷新数据
    void refreshData();

    //加载数据
    void loadData();

    //加载更多
    void loadMoreData();

}
