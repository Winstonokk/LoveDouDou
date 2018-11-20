package com.barnettwong.lovedoudou.mvp.contract;

import com.barnettwong.lovedoudou.bean.ReviewDetails;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;

import rx.Observable;

/**
 * Created by wang on 2018/8/8.
 **/
public interface MovieDetailContract {
    interface Model extends BaseModel {
        Observable<ReviewDetails> getMovieDetail(int reviewId);
    }

    interface View extends BaseView {
        void returnResult(ReviewDetails details);

    }
    abstract static class Presenter extends BasePresenter<View, Model> {

        public abstract void startMovieDetailRequest(int reviewId);

    }
}
