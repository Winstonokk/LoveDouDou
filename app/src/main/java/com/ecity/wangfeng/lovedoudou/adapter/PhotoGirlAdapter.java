package com.ecity.wangfeng.lovedoudou.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ecity.wangfeng.lovedoudou.MyApplication;
import com.ecity.wangfeng.lovedoudou.R;
import com.ecity.wangfeng.lovedoudou.entity.photo.PhotoGirl;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @version 1.0 Created by Administrator on 2016/11/29.
 */

public class PhotoGirlAdapter extends BaseRecyclerViewAdapter<PhotoGirl> {

	public PhotoGirlAdapter(List<PhotoGirl> list) {
		super(list);
	}

	@Override
	public int getItemViewType(int position) {
		if (mIsShowFooter && isFooterPosition(position)) {
			return TYPE_FOOTER;
		} else {
			return TYPE_ITEM;
		}
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if (viewType == TYPE_FOOTER) {
			return new FooterViewHolder(getView(parent, R.layout.adapter_footer_item));
		} else {
			PhotoGirlViewHolder photoGirlViewHolder = new PhotoGirlViewHolder(getView(parent, R.layout.adapter_photo_girl_item));
			setItemOnClickEvent(photoGirlViewHolder);
			return photoGirlViewHolder;
		}
	}

	private void setItemOnClickEvent(final RecyclerView.ViewHolder holder) {
		if (mOnItemClickListener != null) {
			holder.itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mOnItemClickListener.OnItemClickListener(v, holder.getLayoutPosition());
				}
			});
		}
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		super.onBindViewHolder(holder, position);
		if (getItemViewType(position) == TYPE_ITEM) {
			PhotoGirl bean = mList.get(position);
			PhotoGirlViewHolder mHolder = (PhotoGirlViewHolder) holder;

			//Picasso 会自动计算图片的宽高比
			Picasso.with(MyApplication.getInstance())
				   .load(bean.getUrl())
				   .placeholder(R.color.image_place_holder)
				   .error(R.mipmap.ic_load_fail)
				   .into(mHolder.photoIv);
		}
	}

	public class PhotoGirlViewHolder extends RecyclerView.ViewHolder {

		@Bind(R.id.photo_iv)
		ImageView photoIv;

		public PhotoGirlViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
}
