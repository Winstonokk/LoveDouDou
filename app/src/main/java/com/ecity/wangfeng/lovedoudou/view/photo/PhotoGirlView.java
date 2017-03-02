package com.ecity.wangfeng.lovedoudou.view.photo;


import com.ecity.wangfeng.lovedoudou.entity.photo.PhotoGirl;
import com.ecity.wangfeng.lovedoudou.view.BaseView;

import java.util.List;


/**
 * @version 1.0
 * Created by Administrator on 2016/11/29.
 */

public interface PhotoGirlView extends BaseView {

    void updateListView(List<PhotoGirl> photoGirlList, int loadType);

    void updateErrorView(int loadType);
}
