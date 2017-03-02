package com.ecity.wangfeng.lovedoudou.activity;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ecity.wangfeng.lovedoudou.R;
import com.ecity.wangfeng.lovedoudou.base.BaseActivity;
import com.ecity.wangfeng.lovedoudou.entity.news.NewsDetail;
import com.ecity.wangfeng.lovedoudou.presenter.news.NewsDetailPresenter;
import com.ecity.wangfeng.lovedoudou.presenter.news.NewsDetailPresenterImpl;
import com.ecity.wangfeng.lovedoudou.util.RxJavaCustomTransform;
import com.ecity.wangfeng.lovedoudou.view.news.NewsDetailView;
import com.ecity.wangfeng.lovedoudou.widget.phototext.PhotoTextView;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;


public class NewsDetailActivity extends BaseActivity<NewsDetailPresenter> implements NewsDetailView {

	private static final String NEWS_POST_ID = "NEWS_POST_ID";
	private static final String NEWS_IMG_RES = "NEWS_IMG_RES";
	@Bind(R.id.news_detail_picture_iv)
	ImageView               newsDetailPictureIv;
	@Bind(R.id.toolbar_layout)
	CollapsingToolbarLayout toolbarLayout;
	@Bind(R.id.news_content_from)
	TextView                newsContentFrom;
	@Bind(R.id.news_content_text)
	PhotoTextView           newsContentText;

	private String          mPostId;
	private String          mPostImgPath;
	private String          mShareLink;
	private String          mNewsTitle;
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API. See https://g.co/AppIndexing/AndroidStudio for more
	 * information.
	 */
	private GoogleApiClient client;


	@OnClick(R.id.fab)
	public void onClick(View v) {
		if (v.getId() == R.id.fab) {
			share();
		}
	}

	public static Intent getNewsDetailIntent(Context context, String postId, String postImgPath) {
		Intent intent = new Intent(context, NewsDetailActivity.class);
		intent.putExtra(NEWS_POST_ID, postId);
		intent.putExtra(NEWS_IMG_RES, postImgPath);
		return intent;
	}

	private void share() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share));
		intent.putExtra(Intent.EXTRA_TEXT, getShareContents());
		startActivity(Intent.createChooser(intent, getTitle()));
	}

	@NonNull
	private String getShareContents() {
		if (mShareLink == null) {
			mShareLink = "";
		}
		return getString(R.string.share_contents, mNewsTitle, mShareLink);
	}

	@Override
	protected void onDestroy() {
		if (newsContentText != null) {
			newsContentText.cancelImageGetterSubscription();
		}
		super.onDestroy();
	}

	@Override
	public int getLayoutId() {
		return R.layout.activity_news_detail;
	}

	@Override
	public void initVariables() {
		getIntentParams();
		mPresenter = new NewsDetailPresenterImpl();
		mPresenter.attachView(this);
		mPresenter.setPostId(mPostId);
	}

	/**
	 * 获取跳转传递的参数
	 */
	private void getIntentParams() {
		mPostId = getIntent().getStringExtra(NEWS_POST_ID);
		mPostImgPath = getIntent().getStringExtra(NEWS_IMG_RES);
	}

	@Override
	public void initViews() {

		toolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.title_color));
		toolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.title_color));
		Glide.with(this)
			 .load(mPostImgPath)
			 .asBitmap()
			 .placeholder(R.mipmap.ic_loading)
			 .format(DecodeFormat.PREFER_ARGB_8888)
			 .error(R.mipmap.ic_load_fail)
			 .diskCacheStrategy(DiskCacheStrategy.ALL)
			 .into(newsDetailPictureIv);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_news_detail, menu);
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
	public void showProgress() {

	}

	@Override
	public void hideProgress() {

	}

	@Override
	public void showMsg(String message) {

	}

	@Override
	public void showNewsContent(NewsDetail newsDetail) {
		mShareLink = newsDetail.getShareLink();
		mNewsTitle = newsDetail.getTitle();
		toolbarLayout.setTitle(mNewsTitle);
		newsContentFrom.setText(getString(R.string.news_content_from_value, newsDetail.getSource(), newsDetail.getPtime()));
		newsContentText.setContainText(newsDetail.getBody(), false);
		//        setBodyView(newsDetail.getBody());
	}

	private void setBodyView(final String bodyText) {
		if (bodyText != null) {
			Observable.timer(500, TimeUnit.MILLISECONDS)
					  .compose(RxJavaCustomTransform.<Long>defaultSchedulers())
					  .subscribe(new Subscriber<Long>() {
						  @Override
						  public void onCompleted() {

						  }

						  @Override
						  public void onError(Throwable e) {

						  }

						  @Override
						  public void onNext(Long aLong) {
							  newsContentText.setContainText(bodyText, false);
						  }
					  });
		}

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
		Thing object = new Thing.Builder().setName("NewsDetail Page") // TODO: Define a title for the content shown.
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
}
