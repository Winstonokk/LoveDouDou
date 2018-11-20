package com.barnettwong.lovedoudou.mvp.presenter;

import com.barnettwong.lovedoudou.R;
import com.barnettwong.lovedoudou.bean.VideoTypeBean;
import com.barnettwong.lovedoudou.mvp.contract.VideoTypeContract;
import com.jaydenxiao.common.baserx.RxSubscriber;

import java.util.List;

/**
 * Created by wang on 2018/8/8.
 **/
public class VideoTypesPresenter extends VideoTypeContract.Presenter {

    @Override
    public void startTypessRequest() {
        mRxManage.add(mModel.getVideoTypes().subscribe(new RxSubscriber<List<VideoTypeBean.DataBean>>(mContext,false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(List<VideoTypeBean.DataBean> dataBeanList) {
                mView.returnTypesResult(dataBeanList);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
