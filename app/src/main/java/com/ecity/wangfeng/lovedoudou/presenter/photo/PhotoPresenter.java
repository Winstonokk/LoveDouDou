package com.ecity.wangfeng.lovedoudou.presenter.photo;

import com.ecity.wangfeng.lovedoudou.presenter.BasePresenter;

/**
 * @version 1.0
 * Created by Administrator on 2016/11/29.
 */

public interface PhotoPresenter extends BasePresenter {

    void loadPhotoData();

    void refreshData();

    void loadMore();
}
