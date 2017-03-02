package com.ecity.wangfeng.lovedoudou.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.ecity.wangfeng.lovedoudou.R;
import com.ecity.wangfeng.lovedoudou.adapter.NewsChannelAdapter;
import com.ecity.wangfeng.lovedoudou.base.BaseActivity;
import com.ecity.wangfeng.lovedoudou.entity.news.NewsChannelTable;
import com.ecity.wangfeng.lovedoudou.event.ChannelItemMoveEvent;
import com.ecity.wangfeng.lovedoudou.listener.ItemDragHelperCallback;
import com.ecity.wangfeng.lovedoudou.listener.MyRecyclerListener;
import com.ecity.wangfeng.lovedoudou.presenter.news.NewsChannelPresenter;
import com.ecity.wangfeng.lovedoudou.presenter.news.NewsChannelPresenterImpl;
import com.ecity.wangfeng.lovedoudou.util.RxBus;
import com.ecity.wangfeng.lovedoudou.view.news.NewsChannelView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;


/**
 * 频道管理
 *
 * @version 1.0
 */

public class NewsChannelActivity extends BaseActivity<NewsChannelPresenter> implements NewsChannelView {

	@Bind(R.id.tv_edit)
	TextView     mEditBtn;
	@Bind(R.id.mine_recycler_view)
	RecyclerView mMineRecyclerView;
	@Bind(R.id.recommend_recycler_view)
	RecyclerView mRecommendRecyclerView;

	@OnClick(R.id.tv_edit)
	public void onClick(View v) {
		if (v.getId() == R.id.tv_edit) {
			if (mMineAdapter.isEdit()) {
				mEditBtn.setText(R.string.edit_text);
				mMineAdapter.setEdit(false);
			} else {
				mEditBtn.setText(R.string.finish_text);
				mMineAdapter.setEdit(true);
			}
		}
	}

	@Override
	public int getLayoutId() {
		return R.layout.activity_news_channel;
	}

	private NewsChannelAdapter mMineAdapter;

	private NewsChannelAdapter mRecommendAdapter;

	@Override
	public void initVariables() {
		mSubscription = RxBus.getInstance().toObservable(ChannelItemMoveEvent.class).subscribe(new Action1<ChannelItemMoveEvent>() {
			@Override
			public void call(ChannelItemMoveEvent channelItemMoveEvent) {
				if (!mMineAdapter.isEdit()) {
					mEditBtn.setText(R.string.finish_text);
					mMineAdapter.setEdit(true);
				}
				mEditBtn.setText(R.string.finish_text);
				mPresenter.onItemSwap(channelItemMoveEvent.getFromPosition(), channelItemMoveEvent.getToPosition());
			}
		});
		mPresenter = new NewsChannelPresenterImpl();
		mPresenter.attachView(this);
	}

	@Override
	public void initViews() {
		initRecyclerView(mMineRecyclerView);
		initRecyclerView(mRecommendRecyclerView);
	}

	private void initRecyclerView(RecyclerView recyclerView) {
		recyclerView.setLayoutManager(new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false));
		recyclerView.setItemAnimator(new DefaultItemAnimator());
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
	public void updateRecyclerView(List<NewsChannelTable> newsChannelMine, List<NewsChannelTable> newsChannelRecommend) {

		mMineAdapter = new NewsChannelAdapter(newsChannelMine);
		mMineRecyclerView.setAdapter(mMineAdapter);
		setMineChannelOnItemClick();
		initItemDragHelper();

		mRecommendAdapter = new NewsChannelAdapter(newsChannelRecommend);
		mRecommendRecyclerView.setAdapter(mRecommendAdapter);
		setRecommendChannelOnItemClick();

	}

	private void setRecommendChannelOnItemClick() {
		// 推荐频道 item 点击事件
		mRecommendAdapter.setOnItemClickListener(new MyRecyclerListener() {
			@Override
			public void OnItemClickListener(View view, int position) {
				NewsChannelTable newsChannel = mRecommendAdapter.getList().get(position);
				newsChannel.setNewsChannelSelect(true);
				if (!mMineAdapter.isEdit()) {
					mEditBtn.setText(R.string.finish_text);
					mMineAdapter.setEdit(true);
				}
				mMineAdapter.add(mMineAdapter.getItemCount(), newsChannel);
				mRecommendAdapter.delete(position);
				mPresenter.onItemAddOrRemove(newsChannel, false);
			}
		});
	}

	private void initItemDragHelper() {
		ItemDragHelperCallback itemDragHelperCallback = new ItemDragHelperCallback(mMineAdapter);
		ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragHelperCallback);
		itemTouchHelper.attachToRecyclerView(mMineRecyclerView);

		mMineAdapter.setItemDragHelperCallback(itemDragHelperCallback);
	}

	private void setMineChannelOnItemClick() {
		//从 我的频道 中取消选中的频道
		mMineAdapter.setOnItemClickListener(new MyRecyclerListener() {
			@Override
			public void OnItemClickListener(View view, int position) {
				NewsChannelTable newsChannel = mMineAdapter.getList().get(position);
				if (!mMineAdapter.isEdit()) {
					mPresenter.selectIndex(newsChannel.getNewsChannelName());
					finish();
					return;
				}
				if (!newsChannel.isNewsChannelFixed()) {
					newsChannel.setNewsChannelSelect(false);
					mRecommendAdapter.add(mRecommendAdapter.getItemCount(), newsChannel);
					mMineAdapter.delete(position);
					mPresenter.onItemAddOrRemove(newsChannel, true);
				}
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO: add setContentView(...) invocation
		ButterKnife.bind(this);
	}
}
