package com.ecity.wangfeng.lovedoudou.view.news;


import com.ecity.wangfeng.lovedoudou.entity.news.NewsDetail;
import com.ecity.wangfeng.lovedoudou.view.BaseView;

/**
 * @version 1.0
 */
public interface NewsDetailView extends BaseView {

    void showNewsContent(NewsDetail newsDetail);

}
