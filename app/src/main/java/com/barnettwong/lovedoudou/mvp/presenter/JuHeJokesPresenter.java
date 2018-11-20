package com.barnettwong.lovedoudou.mvp.presenter;

import com.barnettwong.lovedoudou.R;
import com.barnettwong.lovedoudou.bean.JuheJokeData;
import com.barnettwong.lovedoudou.mvp.contract.JuHeJokeContract;
import com.jaydenxiao.common.baserx.RxSubscriber;

import java.util.List;


public class JuHeJokesPresenter extends JuHeJokeContract.Presenter{


    @Override
    public void getJokesListDataRequest(String sort, int page, int pagesize, String time, String key) {
        mRxManage.add(mModel.getJokesListData(sort,page,pagesize,time, key).subscribe(new RxSubscriber<List<JuheJokeData.ResultBean.DataBean>>(mContext,false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(List<JuheJokeData.ResultBean.DataBean> newsSummaryBeans) {
                mView.returnJokesListData(newsSummaryBeans);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
