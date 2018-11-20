package com.barnettwong.lovedoudou.ui.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.barnettwong.lovedoudou.R;
import com.barnettwong.lovedoudou.app.AppConstant;
import com.barnettwong.lovedoudou.bean.PhotoGirl;
import com.barnettwong.lovedoudou.mvp.contract.PhotoContract;
import com.barnettwong.lovedoudou.mvp.model.PhotoListModel;
import com.barnettwong.lovedoudou.mvp.presenter.PhotoListPresenter;
import com.barnettwong.lovedoudou.ui.activity.PhotoDetailActivity;
import com.barnettwong.lovedoudou.ui.adapter.PhotoListAdapter;
import com.barnettwong.lovedoudou.ui.view.TitleBar;
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
 * 美女
 */
public class PhotoFragment extends BaseFragment<PhotoListPresenter,PhotoListModel>implements PhotoContract.View, OnRefreshListener, OnLoadmoreListener,PhotoListAdapter.OnPhotoItemClickListener {
    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.loadingtip)
    LoadingTip loadingtip;
    @BindView(R.id.smartrefreshlayout)
    SmartRefreshLayout smartrefreshlayout;

    private PhotoListAdapter adapter;
    private int size;
    private int page;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_photo;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        titleBar.setImmersive(false);
        titleBar.setTitle(getString(R.string.str_hot_girl_title));
        titleBar.setTitleColor(getResources().getColor(R.color.white));
        titleBar.setBackgroundColor(getResources().getColor(R.color.main_style_color));

        smartrefreshlayout.setOnLoadmoreListener(this);
        smartrefreshlayout.setOnRefreshListener(this);
        //声名为瀑布流的布局方式: 2列,垂直方向
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        //3.为recyclerView设置布局管理器
        recyclerview.setLayoutManager(staggeredGridLayoutManager);
        adapter = new PhotoListAdapter(getContext(),R.layout.item_photo_girl);
        adapter.setOnPhotoItemClickListener(this);
        recyclerview.setAdapter(adapter);

        size=10;
        page=1;
        mPresenter.getGirlListDataRequest(size,page);
    }

    @Override
    public void returnListData(List<PhotoGirl> newsSummaries) {
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
        mPresenter.getGirlListDataRequest(size,page);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        adapter.getPageBean().setRefresh(true);
        //发起请求
        mPresenter.getGirlListDataRequest(size,page);
    }

    @Override
    public void onPhotoItemClicked(View view, int position) {
        List<PhotoGirl> list = adapter.getAll();
        Intent intent = PhotoDetailActivity.getPhotoDetailIntent(getContext(),list.get(position).getUrl());
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ImageView animationIv = (ImageView) view.findViewById(R.id.photo_iv);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                    getActivity(),
                    animationIv,
                    AppConstant.TRANSITION_ANIMATION_NEWS_PHOTOS);
            startActivity(intent,options.toBundle());
        }else{
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(
                    view,
                    view.getWidth() / 2,
                    view.getHeight() / 2,
                    0,
                    0);
            ActivityCompat.startActivity(getActivity(),intent,optionsCompat.toBundle());
        }
    }
}
