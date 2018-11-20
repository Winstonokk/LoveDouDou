package com.barnettwong.lovedoudou.mvp.presenter;

import com.barnettwong.lovedoudou.R;
import com.barnettwong.lovedoudou.bean.ReviewDetails;
import com.barnettwong.lovedoudou.mvp.contract.MovieDetailContract;
import com.jaydenxiao.common.baserx.RxSubscriber;

/**
 * Created by wang on 2018/8/8.
 **/
public class MovieDetailPresenter extends MovieDetailContract.Presenter {

    @Override
    public void startMovieDetailRequest(int reviewId) {
        mRxManage.add(mModel.getMovieDetail(reviewId).subscribe(new RxSubscriber<ReviewDetails>(mContext,false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(ReviewDetails reviewDetails) {
                mView.returnResult(reviewDetails);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
