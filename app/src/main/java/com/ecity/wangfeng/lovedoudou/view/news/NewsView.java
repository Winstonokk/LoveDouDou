package com.ecity.wangfeng.lovedoudou.view.news;


import com.ecity.wangfeng.lovedoudou.entity.news.NewsChannelTable;
import com.ecity.wangfeng.lovedoudou.view.BaseView;

import java.util.List;

/**
 *   @version 1.0
 *   @author  yoosir
 * Created by Administrator on 2016/10/19 0019.
 */
public interface NewsView extends BaseView {

    void initViewPager(List<NewsChannelTable> newsChannels);

}
