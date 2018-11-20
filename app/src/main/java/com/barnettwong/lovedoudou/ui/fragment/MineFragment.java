package com.barnettwong.lovedoudou.ui.fragment;

import android.view.View;
import android.widget.LinearLayout;

import com.barnettwong.lovedoudou.R;
import com.barnettwong.lovedoudou.ui.activity.AboutUsActivity;
import com.barnettwong.lovedoudou.ui.view.ShareBottomDialog;
import com.barnettwong.lovedoudou.ui.view.TitleBar;
import com.jaydenxiao.common.base.BaseFragment;

import butterknife.BindView;

public class MineFragment extends BaseFragment {
    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.ll_smile)
    LinearLayout llSmile;
    @BindView(R.id.ll_share)
    LinearLayout llShare;
    @BindView(R.id.ll_about_us)
    LinearLayout llAboutUs;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        titleBar.setImmersive(false);
        titleBar.setTitle(getString(R.string.str_hot_mine_title));
        titleBar.setTitleColor(getResources().getColor(R.color.white));
        titleBar.setBackgroundColor(getResources().getColor(R.color.main_style_color));
        MyOnClickListener onClickListener=new MyOnClickListener();
        llSmile.setOnClickListener(onClickListener);
        llShare.setOnClickListener(onClickListener);
        llAboutUs.setOnClickListener(onClickListener);
    }

    private class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ll_smile:

                    break;
                case R.id.ll_share:
                    ShareBottomDialog dialog = new ShareBottomDialog();
                    dialog.show(getFragmentManager());
                    break;
                case R.id.ll_about_us:
                    startActivity(AboutUsActivity.class);
                    break;
            }
        }
    }


}
