package com.barnettwong.lovedoudou.ui.adapter;

import android.content.Context;
import android.view.View;

import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;
import com.barnettwong.lovedoudou.R;
import com.barnettwong.lovedoudou.bean.PhotoGirl;
import com.jaydenxiao.common.commonutils.ImageLoaderUtils;

public class PhotoListAdapter extends CommonRecycleViewAdapter<PhotoGirl> {
    private Context mContext;
    private OnPhotoItemClickListener onPhotoItemClickListener;

    public PhotoListAdapter(Context context, int layoutId) {
        super(context, layoutId);
        mContext=context;
    }

    public OnPhotoItemClickListener getOnPhotoItemClickListener() {
        return onPhotoItemClickListener;
    }

    public void setOnPhotoItemClickListener(OnPhotoItemClickListener onPhotoItemClickListener) {
        this.onPhotoItemClickListener = onPhotoItemClickListener;
    }

    @Override
    public void convert(ViewHolderHelper helper, PhotoGirl photoGirl) {
        ImageLoaderUtils.display(mContext,helper.getView(R.id.photo_iv),photoGirl.getUrl());
        helper.getView(R.id.fl_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPhotoItemClickListener.onPhotoItemClicked(v,helper.getAdapterPosition());
            }
        });
    }

     public interface OnPhotoItemClickListener{
       void onPhotoItemClicked(View view,int position);
    }
}
