package com.barnettwong.lovedoudou.mvp.presenter;

import com.barnettwong.lovedoudou.R;
import com.barnettwong.lovedoudou.bean.VideoListBean;
import com.barnettwong.lovedoudou.mvp.contract.VideoListContract;
import com.jaydenxiao.common.baserx.RxSubscriber;

import java.util.List;

/**
 * Created by wang on 2018/8/8.
 **/
public class VideoListPresenter extends VideoListContract.Presenter {

    @Override
    public void startVideoListRequest(int type, int page) {
        mRxManage.add(mModel.getVideoLists(type,page).subscribe(new RxSubscriber<List<VideoListBean.DataBean>>(mContext,false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(List<VideoListBean.DataBean> dataBeanList) {
                mView.returnVideoListResult(dataBeanList);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
