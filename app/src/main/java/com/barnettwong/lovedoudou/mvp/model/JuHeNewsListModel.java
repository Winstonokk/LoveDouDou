package com.barnettwong.lovedoudou.mvp.model;

import com.barnettwong.lovedoudou.api.Api;
import com.barnettwong.lovedoudou.api.HostType;
import com.barnettwong.lovedoudou.bean.JuHeNewsData;
import com.barnettwong.lovedoudou.mvp.contract.JuHeNewsContract;
import com.jaydenxiao.common.baserx.RxSchedulers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * des:资讯列表model
 */
public class JuHeNewsListModel implements JuHeNewsContract.Model {


    @Override
    public Observable<List<JuHeNewsData.ResultData.JuheNewsBean>> getNewsListData(String type, String appkey) {
        Map<String, String> map = new HashMap<>();
        map.put("type", type);
        map.put("key", appkey);

        return Api.getDefault(HostType.NET_HOST).getJUHENewsList(map)
                .map(newsResult ->
                        newsResult.getResult().data)
                .compose(RxSchedulers.io_main());
    }
}
