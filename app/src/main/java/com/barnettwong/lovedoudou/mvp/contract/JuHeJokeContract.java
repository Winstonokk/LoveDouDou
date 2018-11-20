package com.barnettwong.lovedoudou.mvp.contract;

import com.barnettwong.lovedoudou.bean.JuheJokeData;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;

import java.util.List;

import rx.Observable;


public interface JuHeJokeContract {
    interface Model extends BaseModel {
        //        sort	是	string	类型，desc:指定时间之前发布的，asc:指定时间之后发布的
//        page	否	int	当前页数,默认1
//        pagesize	否	int	每次返回条数,默认1,最大20
//        time	是	string	时间戳（10位），如：1418816972
//        key	是	string	在个人中心->我的数据,接口名称上方查看
        Observable<List<JuheJokeData.ResultBean.DataBean>> getJokesListData(String sort, int page, int pagesize, String time, String key);
    }

    interface View extends BaseView {
        void returnJokesListData(List<JuheJokeData.ResultBean.DataBean> dataBeans);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getJokesListDataRequest(String sort, int page, int pagesize, String time, String key);
    }
}
