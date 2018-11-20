package com.barnettwong.lovedoudou.mvp.model;

import com.barnettwong.lovedoudou.api.Api;
import com.barnettwong.lovedoudou.api.HostType;
import com.barnettwong.lovedoudou.bean.VideoTypeBean;
import com.barnettwong.lovedoudou.mvp.contract.VideoTypeContract;
import com.jaydenxiao.common.baserx.RxSchedulers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by wang on 2018/8/8.
 **/
public class VideoTypesModel implements VideoTypeContract.Model{

    @Override
    public Observable<List<VideoTypeBean.DataBean>> getVideoTypes() {
        Map<String, String> map = new HashMap<>();

        return Api.getDefault(HostType.PH_HOST)
                .getVideoTypes(map)
                .map(resultBean -> resultBean.getData())
                .compose(RxSchedulers.io_main());
    }
}
