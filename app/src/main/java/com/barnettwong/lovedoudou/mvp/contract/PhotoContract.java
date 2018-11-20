package com.barnettwong.lovedoudou.mvp.contract;

import com.barnettwong.lovedoudou.bean.PhotoGirl;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;

import java.util.List;

import rx.Observable;


public interface PhotoContract {
    interface Model extends BaseModel {
        Observable <List<PhotoGirl>> getGirlListData(int size, int page);
    }

    interface View extends BaseView {
        void returnListData(List<PhotoGirl> photoGirls);
    }
    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getGirlListDataRequest(int size, int page);
    }
}
