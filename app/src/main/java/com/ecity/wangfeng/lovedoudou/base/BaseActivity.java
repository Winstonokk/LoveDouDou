package com.ecity.wangfeng.lovedoudou.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ecity.wangfeng.lovedoudou.R;
import com.ecity.wangfeng.lovedoudou.activity.MainActivity;
import com.ecity.wangfeng.lovedoudou.presenter.BasePresenter;

import butterknife.ButterKnife;
import rx.Subscription;

/**
 * 基类Activity
 *
 * @author wangfeng
 * @date 2017/2/27
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

	protected T       mPresenter;
	private   boolean mIsAddedView;

	protected Subscription mSubscription;

	public abstract int getLayoutId();

	public abstract void initVariables();

	public abstract void initViews();

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int layoutId = getLayoutId();
		setContentView(layoutId);
		initVariables();
		ButterKnife.bind(this);
		initToolBar();
		initViews();
		if (mPresenter != null) {
			mPresenter.onCreate();
		}
	}

	protected void initToolBar() {
		if (!(this instanceof MainActivity)) {
			Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
			setSupportActionBar(toolbar);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
					finishAfterTransition();
				} else {
					finish();
				}
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mPresenter != null) {
			mPresenter.onDestroy();
		}
		//TODO unSubscribe
		if (mSubscription != null && !mSubscription.isUnsubscribed()) {
			mSubscription.unsubscribe();
		}
	}

	public boolean ismIsAddedView() {
		return mIsAddedView;
	}

	public void setIsAddedView(boolean isAddedView) {
		this.mIsAddedView = isAddedView;
	}


}

