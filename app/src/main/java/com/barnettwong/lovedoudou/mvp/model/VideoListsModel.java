package com.barnettwong.lovedoudou.mvp.model;

import com.barnettwong.lovedoudou.api.Api;
import com.barnettwong.lovedoudou.api.HostType;
import com.barnettwong.lovedoudou.bean.VideoListBean;
import com.barnettwong.lovedoudou.mvp.contract.VideoListContract;
import com.jaydenxiao.common.baserx.RxSchedulers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by wang on 2018/8/8.
 **/
public class VideoListsModel implements VideoListContract.Model{

    @Override
    public Observable<List<VideoListBean.DataBean>> getVideoLists(int type, int page) {
        Map<String, String> map = new HashMap<>();
        map.put("type",type+"");
        map.put("page",page+"");

        return Api.getDefault(HostType.PH_HOST)
                .getVideoLists(map)
                .map(resultBean -> resultBean.getData())
                .compose(RxSchedulers.io_main());
    }
}
