package com.barnettwong.lovedoudou.mvp.contract;

import com.barnettwong.lovedoudou.bean.VideoTypeBean;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * Created by wang on 2018/8/8.
 **/
public interface VideoTypeContract {
    interface Model extends BaseModel {
        Observable<List<VideoTypeBean.DataBean>> getVideoTypes();
    }

    interface View extends BaseView {
        void returnTypesResult(List<VideoTypeBean.DataBean> typelist);

    }
    abstract static class Presenter extends BasePresenter<View, Model> {

        public abstract void startTypessRequest();

    }
}
