package com.ecity.wangfeng.lovedoudou.activity;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ecity.wangfeng.lovedoudou.R;
import com.ecity.wangfeng.lovedoudou.base.BaseActivity;
import com.ecity.wangfeng.lovedoudou.entity.news.NewsPhotoDetail;
import com.ecity.wangfeng.lovedoudou.util.SystemUiVisibilityUtil;
import com.ecity.wangfeng.lovedoudou.widget.photoview.HackyViewPager;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

import java.util.List;

import butterknife.Bind;


public class NewsPhotoDetailActivity extends BaseActivity {

	private static final String PHOTO_DETAIL = "PHOTO_DETAIL";
	@Bind(R.id.photo_viewpager)
	HackyViewPager photoViewpager;
	@Bind(R.id.toolbar)
	Toolbar        toolbar;
	@Bind(R.id.photo_count_tv)
	TextView       photoCountTv;
	@Bind(R.id.photo_news_title_tv)
	TextView       photoNewsTitleTv;
	@Bind(R.id.photo_news_desc_tv)
	TextView       photoNewsDescTv;
	@Bind(R.id.photo_text_layout)
	FrameLayout    photoTextLayout;
	@Bind(R.id.activity_news_photo_detail)
	FrameLayout    activityNewsPhotoDetail;


	private NewsPhotoDetail                   mNewsPhotoDetail;
	private List<NewsPhotoDetail.PictureItem> mPictureList;

	private boolean isHidden           = false;
	private boolean mIsStatusBarHidden = false;
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API. See https://g.co/AppIndexing/AndroidStudio for more
	 * information.
	 */
	private GoogleApiClient client;

	public static Intent getNewsDetailIntent(Context context, NewsPhotoDetail newsPhotoDetail) {
		Intent intent = new Intent(context, NewsPhotoDetailActivity.class);
		intent.putExtra(PHOTO_DETAIL, newsPhotoDetail);
		return intent;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_news_photo_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public int getLayoutId() {
		return R.layout.activity_news_photo_detail;
	}

	@Override
	public void initVariables() {
		mNewsPhotoDetail = getIntent().getParcelableExtra(PHOTO_DETAIL);
		mPictureList = mNewsPhotoDetail.getPictureItemList();

	}

	@Override
	public void initViews() {
		photoTextLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		photoNewsTitleTv.setText(mNewsPhotoDetail.getTitle());
		photoNewsDescTv.setText(mPictureList.get(0).getDescription());
		photoCountTv.setText("1/" + mPictureList.size());
		photoViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				photoNewsDescTv.setText(mPictureList.get(position).getDescription());
				photoCountTv.setText((position + 1) + "/" + mPictureList.size());
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
		photoViewpager.setAdapter(new PhotoPagerAdapter());
	}

	private void hideOrShowStatusBar() {
		if (mIsStatusBarHidden) {
			SystemUiVisibilityUtil.enter(NewsPhotoDetailActivity.this);
		} else {
			SystemUiVisibilityUtil.exit(NewsPhotoDetailActivity.this);
		}
		mIsStatusBarHidden = !mIsStatusBarHidden;
	}

	public void hideToolBarAndTextView() {
		isHidden = !isHidden;
		if (isHidden) {
			startAnimation(true, 1.0f, 0.0f);
		} else {
			startAnimation(false, 0.1f, 1.0f);
		}
	}

	private void startAnimation(final boolean endState, float startValue, float endValue) {
		ValueAnimator animator = ValueAnimator.ofFloat(startValue, endValue).setDuration(500);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float y1, y2;
				if (endState) {
					y1 = (0 - animation.getAnimatedFraction()) * toolbar.getHeight();
					y2 = animation.getAnimatedFraction() * photoTextLayout.getHeight();
				} else {
					y1 = (animation.getAnimatedFraction() - 1) * toolbar.getHeight();
					y2 = (1 - animation.getAnimatedFraction()) * photoTextLayout.getHeight();
				}
				toolbar.setTranslationY(y1);
				photoTextLayout.setTranslationY(y2);
			}
		});
		animator.start();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
	}

	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API. See https://g.co/AppIndexing/AndroidStudio for more
	 * information.
	 */
	public Action getIndexApiAction() {
		Thing object = new Thing.Builder().setName("NewsPhotoDetail Page") // TODO: Define a title for the content shown.
										  // TODO: Make sure this auto-generated URL is correct.
										  .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]")).build();
		return new Action.Builder(Action.TYPE_VIEW).setObject(object).setActionStatus(Action.STATUS_TYPE_COMPLETED).build();
	}

	@Override
	public void onStart() {
		super.onStart();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.connect();
		AppIndex.AppIndexApi.start(client, getIndexApiAction());
	}

	@Override
	public void onStop() {
		super.onStop();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		AppIndex.AppIndexApi.end(client, getIndexApiAction());
		client.disconnect();
	}

	class PhotoPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mPictureList == null ? 0 : mPictureList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {

			PhotoView photoView = new PhotoView(NewsPhotoDetailActivity.this);
			Glide.with(NewsPhotoDetailActivity.this)
				 .load(mPictureList.get(position).getImgPath())
				 .placeholder(R.mipmap.ic_loading)
				 .error(R.mipmap.ic_load_fail)
				 .diskCacheStrategy(DiskCacheStrategy.ALL)
				 .into(photoView);
			photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
				@Override
				public void onPhotoTap(View view, float v, float v1) {
					hideToolBarAndTextView();
					hideOrShowStatusBar();
				}

				@Override
				public void onOutsidePhotoTap() {

				}
			});
			container.addView(photoView, ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT);
			return photoView;
		}
	}

}
