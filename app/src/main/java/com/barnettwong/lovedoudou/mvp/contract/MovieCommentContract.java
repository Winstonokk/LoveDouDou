package com.barnettwong.lovedoudou.mvp.contract;

import com.barnettwong.lovedoudou.bean.MovieComment;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * Created by wang on 2018/8/8.
 **/
public interface MovieCommentContract {
    interface Model extends BaseModel {
        Observable<List<MovieComment>> getMovieComments();
    }

    interface View extends BaseView {
        void returnResult(List<MovieComment> movieComments);

    }
    abstract static class Presenter extends BasePresenter<View, Model> {

        public abstract void startMovieCommentsRequest();

    }
}
