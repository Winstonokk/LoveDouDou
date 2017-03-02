package com.ecity.wangfeng.lovedoudou.fragment;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ecity.wangfeng.lovedoudou.Constants;
import com.ecity.wangfeng.lovedoudou.R;
import com.ecity.wangfeng.lovedoudou.activity.PhotoDetailActivity;
import com.ecity.wangfeng.lovedoudou.adapter.PhotoGirlAdapter;
import com.ecity.wangfeng.lovedoudou.base.BaseFragment;
import com.ecity.wangfeng.lovedoudou.common.LoadDataType;
import com.ecity.wangfeng.lovedoudou.entity.photo.PhotoGirl;
import com.ecity.wangfeng.lovedoudou.listener.MyRecyclerListener;
import com.ecity.wangfeng.lovedoudou.presenter.photo.PhotoPresenter;
import com.ecity.wangfeng.lovedoudou.presenter.photo.PhotoPresenterImpl;
import com.ecity.wangfeng.lovedoudou.view.photo.PhotoGirlView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;



/**
 * @author yoosir Created by Administrator on 2016/11/29.
 * @version 1.0
 */

public class PhotoFragment extends BaseFragment<PhotoPresenter> implements PhotoGirlView, MyRecyclerListener {
	@Bind(R.id.recycler_view)
	RecyclerView         mRecyclerView;
	@Bind(R.id.swipe_refresh_layout)
	SwipeRefreshLayout   mSwipeRefreshLayout;
	@Bind(R.id.empty_view)
	TextView             mEmptyView;
	@Bind(R.id.progress_bar)
	ProgressBar          mProgressBar;

	@OnClick({R.id.empty_view,
			  R.id.fab})
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.empty_view:
				mPresenter.loadPhotoData();
				break;
			case R.id.fab:
				mRecyclerView.getLayoutManager().scrollToPosition(0);
				break;
		}
	}

	private PhotoGirlAdapter mAdapter;
	private boolean isLoading = false;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof OnPhotoFIListener) {
			mListener = (OnPhotoFIListener) context;
		} else {
			throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_photo;
	}

	@Override
	public void initViews(View view) {
		mListener.onPhotoTitle(getString(R.string.str_beautiful_girl));
		initSwipeRefreshLayout();
		initRecyclerView();
		initPresenter();
	}

	private void initSwipeRefreshLayout() {
		mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_orange_light,
													android.R.color.holo_green_light, android.R.color.holo_blue_light);
		mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				//TODO refresh data
				mPresenter.refreshData();
			}
		});
	}

	private void initRecyclerView() {
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();

				int[] lastVisibleItemPosition = layoutManager.findLastVisibleItemPositions(null);
				int visibleItemCount = layoutManager.getChildCount();
				int totalItemCount = layoutManager.getItemCount();

				if (!isLoading && visibleItemCount > 0 &&
					(newState == RecyclerView.SCROLL_STATE_IDLE) &&
					((lastVisibleItemPosition[0] >= totalItemCount - 1) || (lastVisibleItemPosition[1] >= totalItemCount - 1))) {
					isLoading = true;
					mPresenter.loadMore();
					mAdapter.showFooter();
					mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
				}
			}
		});
		mAdapter = new PhotoGirlAdapter(null);
		mAdapter.setOnItemClickListener(this);
		mRecyclerView.setAdapter(mAdapter);
	}

	private void initPresenter() {
		mPresenter = new PhotoPresenterImpl();
		mPresenter.attachView(this);
		mPresenter.onCreate();
	}

	@Override
	public void showProgress() {
		mProgressBar.setVisibility(View.VISIBLE);
	}

	@Override
	public void hideProgress() {
		mProgressBar.setVisibility(View.GONE);
	}

	@Override
	public void showMsg(String message) {
		Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void updateListView(List<PhotoGirl> photoGirlList, int loadType) {
		switch (loadType) {
			case LoadDataType.TYPE_FIRST_LOAD:
				mEmptyView.setVisibility(View.GONE);
				mSwipeRefreshLayout.setVisibility(View.VISIBLE);
				mAdapter.setList(photoGirlList);
				mAdapter.notifyDataSetChanged();
				break;
			case LoadDataType.TYPE_REFRESH:
				mSwipeRefreshLayout.setRefreshing(false);
				mAdapter.setList(photoGirlList);
				mAdapter.notifyDataSetChanged();
				break;
			case LoadDataType.TYPE_LOAD_MORE:
				isLoading = false;
				mAdapter.hideFooter();
				mAdapter.addMore(photoGirlList);
				break;
			default:
				break;
		}
	}

	@Override
	public void updateErrorView(int loadType) {
		switch (loadType) {
			case LoadDataType.TYPE_FIRST_LOAD:
				mSwipeRefreshLayout.setVisibility(View.GONE);
				mEmptyView.setVisibility(View.VISIBLE);
				break;
			case LoadDataType.TYPE_REFRESH:
				mSwipeRefreshLayout.setRefreshing(false);
				mSwipeRefreshLayout.setVisibility(View.GONE);
				mEmptyView.setVisibility(View.VISIBLE);
				break;
			case LoadDataType.TYPE_LOAD_MORE:
				isLoading = false;
				mAdapter.hideFooter();
				mAdapter.notifyDataSetChanged();
				break;
			default:
				break;
		}
	}

	@Override
	public void OnItemClickListener(View view, int position) {
		List<PhotoGirl> list = mAdapter.getList();
		Intent intent = PhotoDetailActivity.getPhotoDetailIntent(getContext(), list.get(position).getUrl());
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			ImageView animationIv = (ImageView) view.findViewById(R.id.photo_iv);
			ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), animationIv,
																				   Constants.TRANSITION_ANIMATION_NEWS_PHOTOS);
			startActivity(intent, options.toBundle());
		} else {
			ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(view, view.getWidth() / 2,
																							 view.getHeight() / 2, 0, 0);
			ActivityCompat.startActivity(getActivity(), intent, optionsCompat.toBundle());
		}
	}

	private OnPhotoFIListener mListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO: inflate a fragment view
		View rootView = super.onCreateView(inflater, container, savedInstanceState);
		ButterKnife.bind(this, rootView);
		return rootView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}

	public interface OnPhotoFIListener {

		void onPhotoTitle(String title);
	}
}
