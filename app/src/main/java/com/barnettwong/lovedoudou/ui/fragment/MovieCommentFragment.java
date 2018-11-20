package com.barnettwong.lovedoudou.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.barnettwong.lovedoudou.R;
import com.barnettwong.lovedoudou.bean.MovieComment;
import com.barnettwong.lovedoudou.mvp.contract.MovieCommentContract;
import com.barnettwong.lovedoudou.mvp.model.MovieCommentModel;
import com.barnettwong.lovedoudou.mvp.presenter.MovieCommentPresenter;
import com.barnettwong.lovedoudou.ui.activity.CommentDetailActivity;
import com.barnettwong.lovedoudou.ui.adapter.MovieCommentAdapter;
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

public class MovieCommentFragment extends BaseFragment<MovieCommentPresenter,MovieCommentModel> implements MovieCommentContract.View, OnRefreshListener, OnLoadmoreListener {
    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.fragment_news_recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.loadedTip)
    LoadingTip loadedTip;
    @BindView(R.id.fragment_news_smartrefreshlayout)
    SmartRefreshLayout smartrefreshlayout;

    private MovieCommentAdapter adapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_movie_comment;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        titleBar.setImmersive(false);
        titleBar.setTitle(getString(R.string.str_hot_movie_title));
        titleBar.setTitleColor(getResources().getColor(R.color.white));
        titleBar.setBackgroundColor(getResources().getColor(R.color.main_style_color));
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        adapter=new MovieCommentAdapter(getContext(),R.layout.movie_comment_item);
        adapter.setOnItemClickListener(new MovieCommentAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(MovieComment movieComment) {
                Intent intent=new Intent(getActivity(),CommentDetailActivity.class);
                intent.putExtra("reviewId",movieComment.getId());
                startActivity(intent);
            }
        });
        recyclerview.setAdapter(adapter);

        mPresenter.startMovieCommentsRequest();
    }

    @Override
    public void returnResult(List<MovieComment> newsList) {
        if (newsList != null) {
            if (adapter.getPageBean().isRefresh()) {
                smartrefreshlayout.finishRefresh();
                adapter.replaceAll(newsList);
            } else {
                if (newsList.size() > 0) {
                    adapter.addAll(newsList);
                } else {
                    ToastUitl.showShort(getString(R.string.str_end));
                }
                smartrefreshlayout.finishLoadmore();
            }
        }
    }

    @Override
    public void showLoading(String title) {
        if( adapter.getPageBean().isRefresh()) {
            if(adapter.getSize()<=0) {
                loadedTip.setLoadingTip(LoadingTip.LoadStatus.loading);
            }
        }
    }

    @Override
    public void stopLoading() {
        if(adapter.getSize()==0){
            loadedTip.setLoadingTip(LoadingTip.LoadStatus.empty);
        }else{
            loadedTip.setLoadingTip(LoadingTip.LoadStatus.finish);
        }
    }

    @Override
    public void showErrorTip(String msg) {
        if( adapter.getPageBean().isRefresh()) {
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadedTip.setLoadingTip(LoadingTip.LoadStatus.error);
                    loadedTip.setTips(msg);
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
        //发起请求
//        mPresenter.startMovieCommentsRequest();
        smartrefreshlayout.finishLoadmore(true);
        ToastUitl.showShort("没有更多哦～");
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        adapter.getPageBean().setRefresh(true);
        //发起请求
        mPresenter.startMovieCommentsRequest();
    }


}
