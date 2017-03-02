package com.ecity.wangfeng.lovedoudou.presenter.video;


import com.ecity.wangfeng.lovedoudou.api.video.VideoModuleApi;
import com.ecity.wangfeng.lovedoudou.api.video.VideoModuleApiIml;
import com.ecity.wangfeng.lovedoudou.entity.video.VideoChannel;
import com.ecity.wangfeng.lovedoudou.presenter.base.BasePresenterImpl;
import com.ecity.wangfeng.lovedoudou.view.video.VideoView;

import java.util.List;

/**
 * @version 1.0
 * Created by Administrator on 2016/11/26.
 */

public class VideoPresenterImpl extends BasePresenterImpl<VideoView,List<VideoChannel>> implements VideoPresenter {

    private VideoModuleApi<List<VideoChannel>> videoModuleApi;

    public VideoPresenterImpl(){
        videoModuleApi = new VideoModuleApiIml();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        loadVideoChannel();
    }

    @Override
    public void loadVideoChannel() {
        mSubscription = videoModuleApi.getVideoChannelList(this);
    }

    @Override
    public void success(List<VideoChannel> data) {
        super.success(data);
        mView.initViewPager(data);
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
