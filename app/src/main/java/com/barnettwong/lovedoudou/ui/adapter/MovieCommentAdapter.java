package com.barnettwong.lovedoudou.ui.adapter;

import android.content.Context;
import android.view.View;

import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;
import com.barnettwong.lovedoudou.R;
import com.barnettwong.lovedoudou.bean.MovieComment;
import com.barnettwong.lovedoudou.util.TextUtils;
import com.jaydenxiao.common.commonutils.ImageLoaderUtils;

public class MovieCommentAdapter extends CommonRecycleViewAdapter<MovieComment> {
    private Context mContext;

    public MovieCommentAdapter(Context context, int layoutId) {
        super(context, layoutId);
        mContext = context;
    }

    @Override
    public void convert(ViewHolderHelper helper, MovieComment movieComment) {
        helper.setText(R.id.tv_title, movieComment.getTitle());
        helper.setText(R.id.tv_desc, movieComment.getSummary());
        ImageLoaderUtils.displayRound(mContext, helper.getView(R.id.iv_avatar), movieComment.getUserImage());

        MovieComment.RelatedObjBean relatedObj = movieComment.getRelatedObj();
        if (relatedObj != null) {
            ImageLoaderUtils.display(mContext, helper.getView(R.id.iv_cover), relatedObj.getImage());
            helper.setText(R.id.tv_movie_name, relatedObj.getTitle());
        }
        helper.setText(R.id.tv_title, movieComment.getTitle());
        helper.setText(R.id.tv_desc, TextUtils.handleSpace(movieComment.getSummary()));
        helper.setText(R.id.tv_user_name, movieComment.getNickname() + "- 评");

        helper.getView(R.id.review_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClickListener(movieComment);
            }
        });
    }

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(MovieComment movieComment);
    }
}
