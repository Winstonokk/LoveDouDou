package com.barnettwong.lovedoudou.mvp.model;

import com.barnettwong.lovedoudou.api.Api;
import com.barnettwong.lovedoudou.api.HostType;
import com.barnettwong.lovedoudou.bean.ReviewDetails;
import com.barnettwong.lovedoudou.mvp.contract.MovieDetailContract;
import com.jaydenxiao.common.baserx.RxSchedulers;

import rx.Observable;

/**
 * Created by wang on 2018/8/8.
 **/
public class MovieDetailModel implements MovieDetailContract.Model{

    @Override
    public Observable<ReviewDetails> getMovieDetail(int reviewId) {
        return Api.getDefault(HostType.YINGPIN_HOST)
                .getReviewDetails(reviewId)
                .map(resultBean -> resultBean)
                .compose(RxSchedulers.io_main());
    }
}
