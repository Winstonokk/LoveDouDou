package com.barnettwong.lovedoudou.mvp.model;

import com.barnettwong.lovedoudou.api.Api;
import com.barnettwong.lovedoudou.api.HostType;
import com.barnettwong.lovedoudou.bean.JuheJokeData;
import com.barnettwong.lovedoudou.mvp.contract.JuHeJokeContract;
import com.jaydenxiao.common.baserx.RxSchedulers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * des:笑话列表model
 */
public class JuHeJokesListModel implements JuHeJokeContract.Model {


    @Override
    public Observable<List<JuheJokeData.ResultBean.DataBean>> getJokesListData(String sort, int page, int pagesize, String time, String key) {
        Map<String, String> map = new HashMap<>();
        map.put("sort", sort);
        map.put("page", page+"");
        map.put("pagesize", pagesize+"");
        map.put("time",time);
        map.put("key",key);

        return Api.getDefault(HostType.NET_HOST).getJUHEJOkesList(map)
                .map(newsResult ->
                        newsResult.getResult().getData())
                .compose(RxSchedulers.io_main());
    }
}
