package com.barnettwong.lovedoudou.ui.adapter;

import android.content.Context;

import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;
import com.barnettwong.lovedoudou.R;
import com.barnettwong.lovedoudou.bean.JuheJokeData;

/**
 * des:笑话列表适配器
 */
public class JokeListAdapter extends CommonRecycleViewAdapter<JuheJokeData.ResultBean.DataBean>
{
    private Context mContext;
    public static final String TAG="wangfeng";

    public JokeListAdapter(Context context, int layoutId) {
        super(context, layoutId);
        this.mContext = context;
    }


    @Override
    public void convert(ViewHolderHelper holder, JuheJokeData.ResultBean.DataBean dataBean) {
        holder.setText(R.id.content,dataBean.getContent());
    }
}
