package com.barnettwong.lovedoudou.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;
import com.barnettwong.lovedoudou.R;
import com.barnettwong.lovedoudou.app.AppConstant;
import com.barnettwong.lovedoudou.bean.VideoListBean;
import com.barnettwong.lovedoudou.mvp.contract.VideoListContract;
import com.barnettwong.lovedoudou.mvp.model.VideoListsModel;
import com.barnettwong.lovedoudou.mvp.presenter.VideoListPresenter;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.commonutils.ImageLoaderUtils;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.jaydenxiao.common.commonwidget.LoadingTip;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerManager;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class ChildVideoFragment extends BaseFragment<VideoListPresenter, VideoListsModel> implements VideoListContract.View, OnRefreshListener, OnLoadmoreListener {

    @BindView(R.id.fragment_videos_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.loadedTip)
    LoadingTip loadedTip;
    @BindView(R.id.fragment_videos_smartrefreshlayout)
    SmartRefreshLayout smartRefreshLayout;
    Unbinder unbinder;

    private CommonRecycleViewAdapter<VideoListBean.DataBean> videoListAdapter;

    private int mVideoType;
    private int mStartPage = 0;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_child_video;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        if (getArguments() != null) {
            mVideoType = getArguments().getInt(AppConstant.VIDEO_TYPE);
        }
        smartRefreshLayout.setOnLoadmoreListener(this);
        smartRefreshLayout.setOnRefreshListener(this);

        initData();
        initListener();
        startRequestData();

    }

    private void startRequestData() {
        //发起视频列表请求
        videoListAdapter.getPageBean().setRefresh(true);
        mStartPage = 1;
        mPresenter.startVideoListRequest(mVideoType, mStartPage);
    }

    private void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        videoListAdapter = new CommonRecycleViewAdapter<VideoListBean.DataBean>(getContext(), R.layout.item_video_list) {
            @Override
            public void convert(ViewHolderHelper helper, VideoListBean.DataBean videoData) {
                ImageLoaderUtils.displayRound(getContext(),helper.getView(R.id.iv_logo),videoData.getAvatar());
                helper.setText(R.id.tv_from,videoData.getAnchor());
                helper.setText(R.id.tv_play_time,String.format(getResources().getString(R.string.video_play_times), String.valueOf(videoData.getHot())));
                JCVideoPlayerStandard jcVideoPlayerStandard=helper.getView(R.id.videoplayer);
                boolean setUp = jcVideoPlayerStandard.setUp(
                        videoData.getUrl(), JCVideoPlayer.SCREEN_LAYOUT_LIST,
                        videoData.getTitle());
                if (setUp) {
                    ImageLoaderUtils.display(getContext(),jcVideoPlayerStandard.thumbImageView,videoData.getCover());
                }
            }
        };
        recyclerView.setAdapter(videoListAdapter);
    }


    private void initListener() {
        //视频监听
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                if (JCVideoPlayerManager.listener() != null) {
                    JCVideoPlayer videoPlayer = (JCVideoPlayer) JCVideoPlayerManager.listener();
                    if (((ViewGroup) view).indexOfChild(videoPlayer) != -1 && videoPlayer.currentState == JCVideoPlayer.CURRENT_STATE_PLAYING) {
                        JCVideoPlayer.releaseAllVideos();
                    }
                }
            }
        });

    }

    @Override
    public void returnVideoListResult(List<VideoListBean.DataBean> videoListBeans) {
        if (videoListBeans != null) {
            mStartPage += 1;
            if (videoListAdapter.getPageBean().isRefresh()) {
                smartRefreshLayout.finishRefresh();
                videoListAdapter.replaceAll(videoListBeans);
            } else {
                if (videoListBeans.size() > 0) {
                    videoListAdapter.addAll(videoListBeans);
                } else {
                    ToastUitl.showShort(getString(R.string.str_end));
                }
                smartRefreshLayout.finishLoadmore();
            }
        }
    }


    @Override
    public void showLoading(String title) {
        if (videoListAdapter.getPageBean().isRefresh()) {
            if (videoListAdapter.getSize() <= 0) {
                loadedTip.setLoadingTip(LoadingTip.LoadStatus.loading);
            }
        }
    }

    @Override
    public void stopLoading() {
        if(videoListAdapter.getSize()==0){
            loadedTip.setLoadingTip(LoadingTip.LoadStatus.empty);
        }else{
            loadedTip.setLoadingTip(LoadingTip.LoadStatus.finish);
        }
    }

    @Override
    public void showErrorTip(String msg) {
        if (videoListAdapter.getPageBean().isRefresh()) {
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadedTip.setLoadingTip(LoadingTip.LoadStatus.error);
                    loadedTip.setTips(msg);
                }
            }, 2000);
            videoListAdapter.clear();
            smartRefreshLayout.finishRefresh(false);
        } else {
            smartRefreshLayout.finishLoadmore(false);
        }
        //请求视频分类出现错误

    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        videoListAdapter.getPageBean().setRefresh(false);
        //发起请求
        mPresenter.startVideoListRequest(mVideoType,  mStartPage);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        videoListAdapter.getPageBean().setRefresh(true);
        mStartPage = 1;
        //发起请求
        mPresenter.startVideoListRequest(mVideoType,  mStartPage);
    }


    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
