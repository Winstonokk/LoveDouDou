package com.ecity.wangfeng.lovedoudou.view.video;


import com.ecity.wangfeng.lovedoudou.entity.video.VideoData;
import com.ecity.wangfeng.lovedoudou.view.BaseView;

import java.util.List;

/**
 *  MVP V
 */
public interface VideoListView extends BaseView {

    void setVideoList(List<VideoData> videoDataList, int loadType);

    void updateErrorView(int loadType);
}
