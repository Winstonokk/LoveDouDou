package com.barnettwong.lovedoudou.mvp.model;

import com.barnettwong.lovedoudou.api.Api;
import com.barnettwong.lovedoudou.api.HostType;
import com.barnettwong.lovedoudou.bean.MovieComment;
import com.barnettwong.lovedoudou.mvp.contract.MovieCommentContract;
import com.jaydenxiao.common.baserx.RxSchedulers;

import java.util.List;

import rx.Observable;

/**
 * Created by wang on 2018/8/8.
 **/
public class MovieCommentModel implements MovieCommentContract.Model{


    @Override
    public Observable<List<MovieComment>> getMovieComments() {
        return Api.getDefault(HostType.YINGPIN_HOST)
                .getMovieReview()
                .map(resultBean -> resultBean)
                .compose(RxSchedulers.io_main());
    }
}
