package com.barnettwong.lovedoudou.mvp.presenter;

import com.barnettwong.lovedoudou.R;
import com.barnettwong.lovedoudou.bean.JuHeNewsData;
import com.barnettwong.lovedoudou.mvp.contract.JuHeNewsContract;
import com.jaydenxiao.common.baserx.RxSubscriber;

import java.util.List;


public class JuHeNewsPresenter extends JuHeNewsContract.Presenter{


    @Override
    public void getNewsListDataRequest(String type, String appkey) {
        mRxManage.add(mModel.getNewsListData(type, appkey).subscribe(new RxSubscriber<List<JuHeNewsData.ResultData.JuheNewsBean>>(mContext,false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(List<JuHeNewsData.ResultData.JuheNewsBean> newsSummaryBeans) {
                mView.returnNewsListData(newsSummaryBeans);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
