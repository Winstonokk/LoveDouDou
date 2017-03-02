package com.ecity.wangfeng.lovedoudou.view.news;


import com.ecity.wangfeng.lovedoudou.entity.news.NewsSummary;
import com.ecity.wangfeng.lovedoudou.view.BaseView;

import java.util.List;

/**
 * @version 1.0
 * Created by Yoosir on 2016/11/11 0011.
 */
public interface NewsListView extends BaseView {

    void setNewsList(List<NewsSummary> newsSummaryList, int loadType);

    void updateErrorView(int loadType);
}
