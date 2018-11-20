package com.barnettwong.lovedoudou.mvp.presenter;

import com.barnettwong.lovedoudou.R;
import com.barnettwong.lovedoudou.bean.MovieComment;
import com.barnettwong.lovedoudou.mvp.contract.MovieCommentContract;
import com.jaydenxiao.common.baserx.RxSubscriber;

import java.util.List;

/**
 * Created by wang on 2018/8/8.
 **/
public class MovieCommentPresenter extends MovieCommentContract.Presenter {

    @Override
    public void startMovieCommentsRequest() {
        mRxManage.add(mModel.getMovieComments().subscribe(new RxSubscriber<List<MovieComment>>(mContext,false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(List<MovieComment> dataBeanList) {
                mView.returnResult(dataBeanList);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
