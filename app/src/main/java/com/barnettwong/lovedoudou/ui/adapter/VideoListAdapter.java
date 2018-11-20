package com.barnettwong.lovedoudou.ui.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;
import com.barnettwong.lovedoudou.R;
import com.barnettwong.lovedoudou.bean.VideoData;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class VideoListAdapter extends CommonRecycleViewAdapter<VideoData> {
    private Context mContext;

    public VideoListAdapter(Context context, int layoutId) {
        super(context, layoutId);
        mContext=context;
    }

    @Override
    public void convert(ViewHolderHelper helper, VideoData videoData) {
        helper.setImageRoundUrl(R.id.iv_logo,videoData.getTopicImg());
        helper.setText(R.id.tv_from,videoData.getTopicName());
        helper.setText(R.id.tv_play_time,String.format(mContext.getResources().getString(R.string.video_play_times), String.valueOf(videoData.getPlayCount())));
        JCVideoPlayerStandard jcVideoPlayerStandard=helper.getView(R.id.videoplayer);
        boolean setUp = jcVideoPlayerStandard.setUp(
                videoData.getMp4_url(), JCVideoPlayer.SCREEN_LAYOUT_LIST,
                TextUtils.isEmpty(videoData.getDescription())?videoData.getTitle()+"":videoData.getDescription());
        if (setUp) {
            Glide.with(mContext).load(videoData.getCover())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .error(com.jaydenxiao.common.R.drawable.ic_empty_picture)
                    .crossFade().into(jcVideoPlayerStandard.thumbImageView);
        }
    }
}
