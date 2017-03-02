package com.ecity.wangfeng.lovedoudou.presenter.video;

import android.util.Log;

import com.ecity.wangfeng.lovedoudou.api.video.VideoListModuleApi;
import com.ecity.wangfeng.lovedoudou.api.video.VideoListModuleApiImpl;
import com.ecity.wangfeng.lovedoudou.common.LoadDataType;
import com.ecity.wangfeng.lovedoudou.entity.video.VideoData;
import com.ecity.wangfeng.lovedoudou.presenter.base.BasePresenterImpl;
import com.ecity.wangfeng.lovedoudou.view.video.VideoListView;

import java.util.List;

/**
 *  视频 MVP  P 类
 */
public class VideoListPresenterImpl extends BasePresenterImpl<VideoListView,List<VideoData>> implements VideoListPresenter{

    private int mLoadDataType = LoadDataType.TYPE_FIRST_LOAD;

    private VideoListModuleApi<List<VideoData>> moduleApi;

    private String mVideoType;
    private int mStartPage;

    public VideoListPresenterImpl(){
        moduleApi = new VideoListModuleApiImpl();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("VideoListPresenterImpl","onCreate mStartPage="+mStartPage+" -- mVideoType="+mVideoType);
        if(mView != null){
            loadData();
        }
    }

    @Override
    public void setVideoType(String videoType) {
        mVideoType = videoType;
    }

    @Override
    public void loadData() {
        beforeRequest();
        mLoadDataType = LoadDataType.TYPE_FIRST_LOAD;
        mStartPage = 10;
        loadVideoData(mStartPage);
    }

    @Override
    public void refreshData() {
        mLoadDataType = LoadDataType.TYPE_REFRESH;
        mStartPage = 10;
        loadVideoData(mStartPage);
    }

    @Override
    public void loadMoreData() {
        mLoadDataType = LoadDataType.TYPE_LOAD_MORE;
        mStartPage += 10;
        loadVideoData(mStartPage);
    }

    private void loadVideoData(int startPage){
        mSubscription = moduleApi.getVideoList(this,mVideoType,startPage);
    }

    @Override
    public void success(List<VideoData> videoList) {
        super.success(videoList);
        mView.setVideoList(videoList,mLoadDataType);
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
        mView.updateErrorView(mLoadDataType);
    }
}
