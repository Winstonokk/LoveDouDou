package com.ecity.wangfeng.lovedoudou.api.video;


import com.ecity.wangfeng.lovedoudou.common.HostType;
import com.ecity.wangfeng.lovedoudou.common.RequestCallBack;
import com.ecity.wangfeng.lovedoudou.entity.video.VideoData;
import com.ecity.wangfeng.lovedoudou.service.VideoService;
import com.ecity.wangfeng.lovedoudou.util.DateUtil;
import com.ecity.wangfeng.lovedoudou.util.OkHttpUtil;
import com.ecity.wangfeng.lovedoudou.util.RetrofitManager;
import com.ecity.wangfeng.lovedoudou.util.RxJavaCustomTransform;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;

/**
 * 视频 Video
 * @version 1.0
 */
public class VideoListModuleApiImpl implements VideoListModuleApi<List<VideoData>> {

    private static final String TAG = "VideoListModuleApiImpl";


    public VideoListModuleApiImpl(){}


    @Override
    public Subscription getVideoList(final RequestCallBack<List<VideoData>> callBack, final String videoType, int startPage) {
        return RetrofitManager.getInstance(HostType.VIDEO_HOST)
                              .createService(VideoService.class)
                              .getVideoList(OkHttpUtil.getCacheControl(), videoType, startPage)
                              .flatMap(new Func1<Map<String, List<VideoData>>, Observable<VideoData>>() {
                    @Override
                    public Observable<VideoData> call(Map<String, List<VideoData>> stringListMap) {
                        return Observable.from(stringListMap.get(videoType));
                    }
                })
                              .map(new Func1<VideoData, VideoData>() {
                    @Override
                    public VideoData call(VideoData videoData) {
                        String videoLength = DateUtil.getLengthStr(videoData.getLength());
                        videoData.setSectiontitle(videoLength);
                        return videoData;
                    }
                })
                              .toList()
                              .compose(RxJavaCustomTransform.<List<VideoData>>defaultSchedulers())
                              .subscribe(new Subscriber<List<VideoData>>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("--------------------- onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("--------------------- onError:"+e.getMessage());
                        callBack.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<VideoData> videoDatas) {
                        System.out.println("--------------------- onNext:"+videoDatas.size());
                        for (VideoData v:videoDatas) {
                            System.out.println(v.getCover() +" ,"+v.getTitle());
                        }
                        callBack.success(videoDatas);
                    }
                });

    }
}
