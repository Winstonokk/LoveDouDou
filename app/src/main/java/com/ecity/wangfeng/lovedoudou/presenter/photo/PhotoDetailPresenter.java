package com.ecity.wangfeng.lovedoudou.presenter.photo;

import com.ecity.wangfeng.lovedoudou.presenter.BasePresenter;

/**
 * @version 1.0
 * Created by Administrator on 2016/11/30.
 */

public interface PhotoDetailPresenter extends BasePresenter {

    //下载保存图片
    void savePhoto(String photoUrl);

    //分享图片
    void sharePhoto(String photoUrl);

    //设置墙纸
    void setWallpaper(String photoUrl);

}
