package com.barnettwong.lovedoudou.mvp.presenter;

import com.barnettwong.lovedoudou.R;
import com.barnettwong.lovedoudou.bean.PhotoGirl;
import com.barnettwong.lovedoudou.mvp.contract.PhotoContract;
import com.jaydenxiao.common.baserx.RxSubscriber;

import java.util.List;

/**
 */
public class PhotoListPresenter extends PhotoContract.Presenter {


    @Override
    public void getGirlListDataRequest(int size, int page) {
        mRxManage.add(mModel.getGirlListData(size,page).subscribe(new RxSubscriber<List<PhotoGirl>>(mContext,false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }
            @Override
            protected void _onNext(List<PhotoGirl> photoGirls) {
                mView.returnListData(photoGirls);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
