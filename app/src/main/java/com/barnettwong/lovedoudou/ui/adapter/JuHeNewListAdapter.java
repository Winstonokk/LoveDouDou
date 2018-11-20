package com.barnettwong.lovedoudou.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;
import com.barnettwong.lovedoudou.R;
import com.barnettwong.lovedoudou.bean.JuHeNewsData;
import com.barnettwong.lovedoudou.ui.activity.NewsDetailActivity;

/**
 * des:资讯列表适配器
 */
public class JuHeNewListAdapter extends CommonRecycleViewAdapter<JuHeNewsData.ResultData.JuheNewsBean>
{
    private Context mContext;
    public static final String TAG="wangfeng";

    public JuHeNewListAdapter(Context context, int layoutId) {
        super(context, layoutId);
        this.mContext = context;
    }


    @Override
    public void convert(ViewHolderHelper holder, JuHeNewsData.ResultData.JuheNewsBean juheNewsBean) {
        String title = juheNewsBean.getTitle();
        String author=juheNewsBean.getAuthor_name();
        String time= juheNewsBean.getDate();
        String imgSrc = juheNewsBean.getThumbnail_pic_s();
        String imgMid=juheNewsBean.getThumbnail_pic_s02();
        String imgRight=juheNewsBean.getThumbnail_pic_s03();

        holder.setText(R.id.news_summary_author,author);
        holder.setText(R.id.news_summary_title_tv,title);
        holder.setText(R.id.news_summary_time,time);
        holder.setImageUrl(R.id.news_summary_photo_iv_left,imgSrc);
        holder.setImageUrl(R.id.news_summary_photo_iv_middle,imgMid);
        holder.setImageUrl(R.id.news_summary_photo_iv_right,imgRight);
        holder.setOnClickListener(R.id.ll_root, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, NewsDetailActivity.class);
                intent.putExtra("detailUrl", juheNewsBean.getUrl());
                mContext.startActivity(intent);
            }
        });
    }
}
