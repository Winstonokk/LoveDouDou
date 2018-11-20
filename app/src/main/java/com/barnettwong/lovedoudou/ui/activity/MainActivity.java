package com.barnettwong.lovedoudou.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.barnettwong.lovedoudou.R;
import com.barnettwong.lovedoudou.app.AppConstant;
import com.barnettwong.lovedoudou.bean.TabEntity;
import com.barnettwong.lovedoudou.ui.fragment.MineFragment;
import com.barnettwong.lovedoudou.ui.fragment.MovieCommentFragment;
import com.barnettwong.lovedoudou.ui.fragment.NewsFragment;
import com.barnettwong.lovedoudou.ui.fragment.PhotoFragment;
import com.barnettwong.lovedoudou.ui.fragment.VideoFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonutils.LogUtils;

import java.util.ArrayList;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class MainActivity extends BaseActivity {
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private String[] mTitles = {"资讯", "视频", "影评", "美女", "我的"};
    private int[] mIconUnselectIds = {
            R.mipmap.ic_home_n, R.mipmap.ic_video_n, R.mipmap.ic_movie_n, R.mipmap.ic_girl_n, R.mipmap.ic_wode_n};
    private int[] mIconSelectIds = {
            R.mipmap.ic_home_s, R.mipmap.ic_video_s, R.mipmap.ic_movie_s, R.mipmap.ic_girl_s, R.mipmap.ic_wode_s};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private NewsFragment newsFragment;
    private VideoFragment videoFragment;
    private MovieCommentFragment movieCommentFragment;
    private PhotoFragment photoFragment;
    private MineFragment mineFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        //初始化菜单
        initTab();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化frament
        initFragment(savedInstanceState);
    }

    /**
     * 初始化tab
     */
    private void initTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tabLayout.setTabData(mTabEntities);
        //点击监听
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                SwitchTo(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    /**
     * 初始化碎片
     */
    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 0;
        if (savedInstanceState != null) {
            newsFragment = (NewsFragment) getSupportFragmentManager().findFragmentByTag("newsFragment");
            videoFragment = (VideoFragment) getSupportFragmentManager().findFragmentByTag("videoFragment");
            movieCommentFragment = (MovieCommentFragment) getSupportFragmentManager().findFragmentByTag("movieCommentFragment");
            photoFragment = (PhotoFragment) getSupportFragmentManager().findFragmentByTag("photoFragment");
            mineFragment = (MineFragment) getSupportFragmentManager().findFragmentByTag("mineFragment");
            currentTabPosition = savedInstanceState.getInt(AppConstant.HOME_CURRENT_TAB_POSITION);
        } else {
            newsFragment = new NewsFragment();
            videoFragment = new VideoFragment();
            movieCommentFragment = new MovieCommentFragment();
            photoFragment = new PhotoFragment();
            mineFragment=new MineFragment();

            transaction.add(R.id.fl_body, newsFragment, "newsFragment");
            transaction.add(R.id.fl_body, videoFragment, "videoFragment");
            transaction.add(R.id.fl_body, movieCommentFragment, "movieCommentFragment");
            transaction.add(R.id.fl_body, photoFragment, "photoFragment");
            transaction.add(R.id.fl_body, mineFragment, "mineFragment");
        }
        transaction.commit();
        SwitchTo(currentTabPosition);
        tabLayout.setCurrentTab(currentTabPosition);
    }

    /**
     * 切换
     */
    private void SwitchTo(int position) {
        LogUtils.logd("主页菜单position" + position);
        JCVideoPlayer.releaseAllVideos();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            //资讯
            case 0:
                transaction.hide(videoFragment);
                transaction.hide(movieCommentFragment);
                transaction.hide(photoFragment);
                transaction.hide(mineFragment);
                transaction.show(newsFragment);
                transaction.commitAllowingStateLoss();
                break;
            //视频
            case 1:
                transaction.hide(newsFragment);
                transaction.hide(movieCommentFragment);
                transaction.hide(photoFragment);
                transaction.hide(mineFragment);
                transaction.show(videoFragment);
                transaction.commitAllowingStateLoss();
                break;
            //影评
            case 2:
                transaction.hide(newsFragment);
                transaction.hide(videoFragment);
                transaction.hide(photoFragment);
                transaction.hide(mineFragment);
                transaction.show(movieCommentFragment);
                transaction.commitAllowingStateLoss();
                break;
            //美女
            case 3:
                transaction.hide(newsFragment);
                transaction.hide(videoFragment);
                transaction.hide(movieCommentFragment);
                transaction.hide(mineFragment);
                transaction.show(photoFragment);
                transaction.commitAllowingStateLoss();
                break;
            //我的
            case 4:
                transaction.hide(newsFragment);
                transaction.hide(videoFragment);
                transaction.hide(movieCommentFragment);
                transaction.hide(photoFragment);
                transaction.show(mineFragment);
                transaction.commitAllowingStateLoss();
                break;
            default:
                break;
        }
    }

    /**
     * 监听全屏视频时返回键
     */
    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    /**
     * 监听返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //奔溃前保存位置
        LogUtils.loge("onSaveInstanceState进来了1");
        if (tabLayout != null) {
            LogUtils.loge("onSaveInstanceState进来了2");
            outState.putInt(AppConstant.HOME_CURRENT_TAB_POSITION, tabLayout.getCurrentTab());
        }
    }
}
