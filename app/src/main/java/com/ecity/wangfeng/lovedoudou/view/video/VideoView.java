package com.ecity.wangfeng.lovedoudou.view.video;


import com.ecity.wangfeng.lovedoudou.entity.video.VideoChannel;
import com.ecity.wangfeng.lovedoudou.view.BaseView;

import java.util.List;

/**
 * @version 1.0
 * Created by Administrator on 2016/11/26.
 */

public interface VideoView extends BaseView {

    void initViewPager(List<VideoChannel> videoChannelList);

}
