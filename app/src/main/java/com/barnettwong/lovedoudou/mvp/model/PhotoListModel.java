package com.barnettwong.lovedoudou.mvp.model;

import com.barnettwong.lovedoudou.api.Api;
import com.barnettwong.lovedoudou.api.HostType;
import com.barnettwong.lovedoudou.bean.PhotoGirl;
import com.barnettwong.lovedoudou.mvp.contract.PhotoContract;
import com.jaydenxiao.common.baserx.RxSchedulers;

import java.util.List;

import rx.Observable;

/**
 * des:妹子列表model
 */
public class PhotoListModel implements PhotoContract.Model {


    @Override
    public Observable<List<PhotoGirl>> getGirlListData(int size, int page) {
        return Api.getDefault(HostType.GIRL_HOST).getPhotoList(Api.getCacheControl(),size,page)
                .map(newsResult ->
                        newsResult.getResults())
                .compose(RxSchedulers.io_main());
    }
}
