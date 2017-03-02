package com.ecity.wangfeng.lovedoudou.view.news;


import com.ecity.wangfeng.lovedoudou.entity.news.NewsChannelTable;
import com.ecity.wangfeng.lovedoudou.view.BaseView;

import java.util.List;

/**
 * @version 1.0
 * Created by Administrator on 2016/11/23.
 */

public interface NewsChannelView extends BaseView {

    void updateRecyclerView(List<NewsChannelTable> newsChannelMine, List<NewsChannelTable> newsChannelRecommend);

}
