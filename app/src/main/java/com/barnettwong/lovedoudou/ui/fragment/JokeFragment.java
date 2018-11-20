package com.barnettwong.lovedoudou.ui.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.barnettwong.lovedoudou.R;
import com.barnettwong.lovedoudou.app.AppConstant;
import com.barnettwong.lovedoudou.bean.JuheJokeData;
import com.barnettwong.lovedoudou.mvp.contract.JuHeJokeContract;
import com.barnettwong.lovedoudou.mvp.model.JuHeJokesListModel;
import com.barnettwong.lovedoudou.mvp.presenter.JuHeJokesPresenter;
import com.barnettwong.lovedoudou.ui.adapter.JokeListAdapter;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.jaydenxiao.common.commonwidget.LoadingTip;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;

/**
 * 笑话
 */
public class JokeFragment extends BaseFragment<JuHeJokesPresenter,JuHeJokesListModel> implements JuHeJokeContract.View, OnRefreshListener, OnLoadmoreListener {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.loadingtip)
    LoadingTip loadingtip;
    @BindView(R.id.smartrefreshlayout)
    SmartRefreshLayout smartrefreshlayout;

    private JokeListAdapter adapter;
    private String sort;
    private int page;
    private int pageSize;
    private String queryTime;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_joke;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        smartrefreshlayout.setOnLoadmoreListener(this);
        smartrefreshlayout.setOnRefreshListener(this);

        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new JokeListAdapter(getContext(),R.layout.item_juhe_joke);
        recyclerview.setAdapter(adapter);

        sort="asc";
        page=1;
        pageSize=15;
        queryTime="1526718597";
        mPresenter.getJokesListDataRequest(sort,page,pageSize,queryTime,AppConstant.JUHE_JOKE_KEY);

    }

    @Override
    public void returnJokesListData(List<JuheJokeData.ResultBean.DataBean> newsSummaries) {
        if (newsSummaries != null) {
            page += 1;
            if (adapter.getPageBean().isRefresh()) {
                smartrefreshlayout.finishRefresh();
                adapter.replaceAll(newsSummaries);
            } else {
                if (newsSummaries.size() > 0) {
                    smartrefreshlayout.finishLoadmore();
                    adapter.addAll(newsSummaries);
                } else {
                    smartrefreshlayout.finishLoadmore();
                    ToastUitl.showShort(getString(R.string.str_end));
                }
            }
        }
    }

    @Override
    public void showLoading(String title) {
        if( adapter.getPageBean().isRefresh()) {
            if(adapter.getSize()<=0) {
                loadingtip.setLoadingTip(LoadingTip.LoadStatus.loading);
            }
        }
    }

    @Override
    public void stopLoading() {
        if(adapter.getSize()==0){
            loadingtip.setLoadingTip(LoadingTip.LoadStatus.empty);
        }else{
            loadingtip.setLoadingTip(LoadingTip.LoadStatus.finish);
        }
    }

    @Override
    public void showErrorTip(String msg) {
        if( adapter.getPageBean().isRefresh()) {
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingtip.setLoadingTip(LoadingTip.LoadStatus.error);
                    loadingtip.setTips(msg);
                }
            },2000);
            adapter.clear();
            smartrefreshlayout.finishRefresh(false);
        }else{
            smartrefreshlayout.finishLoadmore(false);
        }
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        adapter.getPageBean().setRefresh(false);
        mPresenter.getJokesListDataRequest(sort,page,pageSize,queryTime,AppConstant.JUHE_JOKE_KEY);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        adapter.getPageBean().setRefresh(true);
        //发起请求
        mPresenter.getJokesListDataRequest(sort,page,pageSize,queryTime,AppConstant.JUHE_JOKE_KEY);
    }


}
