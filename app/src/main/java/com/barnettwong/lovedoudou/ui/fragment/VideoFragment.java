package com.barnettwong.lovedoudou.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.barnettwong.lovedoudou.R;
import com.barnettwong.lovedoudou.app.AppConstant;
import com.barnettwong.lovedoudou.bean.VideoTypeBean;
import com.barnettwong.lovedoudou.mvp.contract.VideoTypeContract;
import com.barnettwong.lovedoudou.mvp.model.VideoTypesModel;
import com.barnettwong.lovedoudou.mvp.presenter.VideoTypesPresenter;
import com.barnettwong.lovedoudou.util.MyUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.base.BaseFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * 视频
 */
public class VideoFragment extends BaseFragment<VideoTypesPresenter,VideoTypesModel> implements VideoTypeContract.View {
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private BaseFragmentAdapter fragmentAdapter;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_video;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        mPresenter.startTypessRequest();
    }

    @Override
    public void returnTypesResult(List<VideoTypeBean.DataBean> typelist) {
        List<String> channelNames = new ArrayList<>();
        List<Fragment> mNewsFragmentList = new ArrayList<>();
        for (int i = 0; i < typelist.size(); i++) {
            channelNames.add(typelist.get(i).getTitle());
            mNewsFragmentList.add(createListFragments(typelist.get(i)));
        }
        fragmentAdapter = new BaseFragmentAdapter(getChildFragmentManager(), mNewsFragmentList, channelNames);
        viewPager.setAdapter(fragmentAdapter);
        tabs.setupWithViewPager(viewPager);
        MyUtils.dynamicSetTabLayoutMode(tabs);
        setPageChangeListener();
    }

    private void setPageChangeListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                JCVideoPlayer.releaseAllVideos();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                JCVideoPlayer.releaseAllVideos();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private ChildVideoFragment createListFragments(VideoTypeBean.DataBean videoChannelTable) {
        ChildVideoFragment fragment = new ChildVideoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstant.VIDEO_TYPE, videoChannelTable.getId());
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
