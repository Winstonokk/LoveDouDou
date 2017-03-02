package com.ecity.wangfeng.lovedoudou.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ecity.wangfeng.lovedoudou.R;
import com.ecity.wangfeng.lovedoudou.base.BaseActivity;
import com.ecity.wangfeng.lovedoudou.base.BaseFragment;
import com.ecity.wangfeng.lovedoudou.fragment.PhotoFragment;
import com.ecity.wangfeng.lovedoudou.fragment.news.NewsFragment;
import com.ecity.wangfeng.lovedoudou.fragment.video.VideoFragment;
import com.ecity.wangfeng.lovedoudou.view.SlidingMenu;
import com.ecity.wangfeng.lovedoudou.widget.video.VideoPlayView;

import de.hdodenhof.circleimageview.CircleImageView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;

/**
 * Created by wangfeng on 2017/1/6.
 */

public class MainActivity extends BaseActivity
	implements NewsFragment.OnNewsFIListener, VideoFragment.OnVideoFIListener, PhotoFragment.OnPhotoFIListener {

	@Bind(R.id.toolbar)
	Toolbar     toolbar;
	@Bind(R.id.sliding_menu)
	SlidingMenu slidingMenu;
	@Bind(R.id.tv_title)
	TextView    tvTitle;
//	@Bind(R.id.full_screen)
//	FrameLayout mFullScreenLayout;

	private CircleImageView avatar;
	private LinearLayout    llNews;
	private LinearLayout    llMusic;
	private LinearLayout    llSmileTalk;
	private LinearLayout    llGirl;
	private LinearLayout    llVideo;
	private LinearLayout    llShare;
	private LinearLayout    llAboutUs;

	private NewsFragment  mNewsFragment;
	private PhotoFragment mPhotoFragment;
	private VideoFragment mVideoFragment;
	private String        childFragmentType; // 1.news ; 2.photo ; 3.video

	private static final String CHILD_FRAGMENT_TAG_NEWS  = "child_news";
	private static final String CHILD_FRAGMENT_TAG_PHOTO = "child_photo";
	private static final String CHILD_FRAGMENT_TAG_VIDEO = "child_video";
	private static final String CHILD_FRAGMENT_TYPE      = "CHILD_FRAGMENT_TYPE";

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			childFragmentType = savedInstanceState.getString(CHILD_FRAGMENT_TYPE);
		}
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString(CHILD_FRAGMENT_TYPE, childFragmentType);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		childFragmentType = savedInstanceState.getString(CHILD_FRAGMENT_TYPE);
	}

	@Override
	public int getLayoutId() {
		return R.layout.activity_main;
	}

	@Override
	public void initVariables() {
		mPhotoFragment = new PhotoFragment();
		mNewsFragment = new NewsFragment();
		mVideoFragment = new VideoFragment();
	}

	@Override
	public void initViews() {
		setSupportActionBar(toolbar);
		initView();
		initListener();
		initOpenSlideListener();
		childFragmentType = childFragmentType == null ? CHILD_FRAGMENT_TAG_NEWS : childFragmentType;
		setDefaultChildFragment(childFragmentType);
	}

	private void initListener() {
		MyOnclickListener onClickListener = new MyOnclickListener();
		avatar.setOnClickListener(onClickListener);
		llNews.setOnClickListener(onClickListener);
		llMusic.setOnClickListener(onClickListener);
		llSmileTalk.setOnClickListener(onClickListener);
		llGirl.setOnClickListener(onClickListener);
		llVideo.setOnClickListener(onClickListener);
		llShare.setOnClickListener(onClickListener);
		llAboutUs.setOnClickListener(onClickListener);
	}

	private void initView() {
		llNews = (LinearLayout) findViewById(R.id.ll_news);
		llMusic = (LinearLayout) findViewById(R.id.ll_music);
		llSmileTalk = (LinearLayout) findViewById(R.id.ll_smile);
		llGirl = (LinearLayout) findViewById(R.id.ll_girl);
		llVideo = (LinearLayout) findViewById(R.id.ll_video);
		llShare = (LinearLayout) findViewById(R.id.ll_share);
		llAboutUs = (LinearLayout) findViewById(R.id.ll_about_us);
		avatar = (CircleImageView) findViewById(R.id.avatar);
	}

	private void initOpenSlideListener() {
		findViewById(R.id.user_head).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				slidingMenu.toggleMenu();
			}
		});
	}

	@Override
	public void onPhotoTitle(String title) {
		tvTitle.setText(title);
	}

	@Override
	public void onNewsTitle(String newsTitle) {
		tvTitle.setText(newsTitle);
	}

	@Override
	public void onVideoTitle(String videoTitle) {
		tvTitle.setText(videoTitle);
	}

	private class MyOnclickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.avatar:
					showUserCenter();
					break;
				case R.id.ll_news:
					showNews();
					break;
				case R.id.ll_music:
					showMusic();
					break;
				case R.id.ll_smile:
					showSmileTalk();
					break;
				case R.id.ll_girl:
					showBeautifulGirl();
					break;
				case R.id.ll_video:
					showVideo();
					break;
				case R.id.ll_share:
					showShare();
					break;
				case R.id.ll_about_us:
					showAboutUs();
					break;
			}
		}
	}

	/**
	 * 个人中心
	 */
	private void showUserCenter() {
		Toast.makeText(getApplicationContext(), "个人中心", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 热点新闻
	 */
	private void showNews() {
		setChildFragment(CHILD_FRAGMENT_TAG_NEWS);
		tvTitle.setText(getResources().getString(R.string.str_news));
		slidingMenu.toggleMenu();
	}

	/**
	 * 潮流音乐
	 */
	private void showMusic() {
		Toast.makeText(getApplicationContext(), "音乐", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 每天一笑
	 */
	private void showSmileTalk() {
		Toast.makeText(getApplicationContext(), "笑话", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 妹子
	 */
	private void showBeautifulGirl() {
		setChildFragment(CHILD_FRAGMENT_TAG_PHOTO);
		tvTitle.setText(getResources().getString(R.string.str_beautiful_girl));
		slidingMenu.toggleMenu();
	}
	/**
	 * 精品视频
	 */
	private void showVideo() {
		setChildFragment(CHILD_FRAGMENT_TAG_VIDEO);
		tvTitle.setText(getResources().getString(R.string.str_video));
		slidingMenu.toggleMenu();
	}

	/**
	 * 分享
	 */
	private void showShare() {
		Toast.makeText(getApplicationContext(), "分享", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 关于
	 */
	private void showAboutUs() {
		Toast.makeText(getApplicationContext(), "关于", Toast.LENGTH_SHORT).show();
	}

	private void setDefaultChildFragment(String childFragmentTag) {
		setChildFragment(childFragmentTag);
	}

	private void setChildFragment(String childFragmentTag) {
		FragmentManager mFragmentManager = getSupportFragmentManager();
		FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();

		if (childFragmentType.equals(childFragmentTag)) {
			BaseFragment childFragment = (BaseFragment) mFragmentManager.findFragmentByTag(childFragmentTag);
			if (childFragment == null) {
				childFragment = getChildFragmentByTag(childFragmentTag);
			} else {
				if (getChildFragmentByTag(childFragmentTag) != childFragment) {
					mFragmentTransaction.remove(childFragment);
					childFragment = getChildFragmentByTag(childFragmentTag);
				}
			}
			if (!childFragment.isAdded()) {
				mFragmentTransaction.add(R.id.main_content, childFragment, childFragmentTag);
			}
			if (childFragment.isHidden()) {
				mFragmentTransaction.show(childFragment);
			}
		} else {
			BaseFragment childFragment = (BaseFragment) mFragmentManager.findFragmentByTag(childFragmentType);
			if (childFragment != null) {
				mFragmentTransaction.hide(childFragment);
			}
			BaseFragment addChildFragment = (BaseFragment) mFragmentManager.findFragmentByTag(childFragmentTag);
			if (addChildFragment == null) {
				addChildFragment = getChildFragmentByTag(childFragmentTag);
			} else {
				if (getChildFragmentByTag(childFragmentTag) != addChildFragment) {
					mFragmentTransaction.remove(addChildFragment);
					addChildFragment = getChildFragmentByTag(childFragmentTag);
				}
			}
			if (!addChildFragment.isAdded()) {
				mFragmentTransaction.add(R.id.main_content, addChildFragment, childFragmentTag);
			}
			mFragmentTransaction.show(addChildFragment);
		}
		childFragmentType = childFragmentTag;
		mFragmentTransaction.commit();
	}

	private BaseFragment getChildFragmentByTag(String childFragmentTag) {
		switch (childFragmentTag) {
			case CHILD_FRAGMENT_TAG_PHOTO:
				return mPhotoFragment;
			case CHILD_FRAGMENT_TAG_VIDEO:
				return mVideoFragment;
			case CHILD_FRAGMENT_TAG_NEWS:
			default:
				return mNewsFragment;
		}
	}

	/**
	 * VideoListFragment  交互接口
	 */
	@Override
	public void onVideoFI(int stateCode, VideoPlayView playView) {
//		if (stateCode == 1) {
//			mFullScreenLayout.setVisibility(View.GONE);
//			mFullScreenLayout.removeAllViews();
//		} else if (stateCode == 2) {
//			mFullScreenLayout.addView(playView);
//			mFullScreenLayout.setVisibility(View.VISIBLE);
//		} else if (stateCode == 3) {
//			mFullScreenLayout.setVisibility(View.GONE);
//		}
	}

	/**
	 * 菜单、返回键响应
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitBy2Click(); // 调用双击退出函数
		}
		return false;
	}

	/**
	 * 双击退出函数
	 */
	private static Boolean isExit = false;

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备退出
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

		} else {
			finish();
			System.exit(0);
		}
	}
}
