package com.barnettwong.lovedoudou.ui.view;

import android.view.View;
import android.widget.Toast;

import com.barnettwong.lovedoudou.R;
import com.barnettwong.lovedoudou.share.shareutil.share.ShareListener;
import com.jaydenxiao.common.commonutils.ToastUitl;

import me.shaohui.bottomdialog.BaseBottomDialog;

/**
 */
public class ShareBottomDialog extends BaseBottomDialog implements View.OnClickListener {

    private ShareListener mShareListener;

    @Override
    public int getLayoutRes() {
        return R.layout.layout_bottom_share;
    }

    @Override
    public void bindView(final View v) {
        v.findViewById(R.id.share_qq).setOnClickListener(this);
        v.findViewById(R.id.share_qzone).setOnClickListener(this);
        v.findViewById(R.id.share_weibo).setOnClickListener(this);
        v.findViewById(R.id.share_wx).setOnClickListener(this);
        v.findViewById(R.id.share_wx_timeline).setOnClickListener(this);
        v.findViewById(R.id.rl_cancle).setOnClickListener(this);

        mShareListener = new ShareListener() {
            @Override
            public void shareSuccess() {
                Toast.makeText(v.getContext(), "分享成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void shareFailure(Exception e) {
                Toast.makeText(v.getContext(), "分享失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void shareCancel() {
                Toast.makeText(v.getContext(), "取消分享", Toast.LENGTH_SHORT).show();

            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share_qq:
                ToastUitl.showShort("需自己根据平台id集成哦～");
//                ShareUtil.shareImage(getContext(), SharePlatform.QQ,
//                        "http://shaohui.me/images/avatar.gif", mShareListener);
                break;
            case R.id.share_qzone:
                ToastUitl.showShort("需自己根据平台id集成哦～");
//                ShareUtil.shareMedia(getContext(), SharePlatform.QZONE, "Title", "summary",
//                        "http://www.google.com", "http://shaohui.me/images/avatar.gif",
//                        mShareListener);
                break;
            case R.id.share_weibo:
                ToastUitl.showShort("需自己根据平台id集成哦～");
//                ShareUtil.shareImage(getContext(), SharePlatform.WEIBO,
//                        "http://shaohui.me/images/avatar.gif", mShareListener);
                break;
            case R.id.share_wx_timeline:
                ToastUitl.showShort("需自己根据平台id集成哦～");
//                ShareUtil.shareText(getContext(), SharePlatform.WX_TIMELINE, "测试分享文字",
//                        mShareListener);
                break;
            case R.id.share_wx:
                ToastUitl.showShort("需自己根据平台id集成哦～");
//                ShareUtil.shareMedia(getContext(), SharePlatform.WX, "Title", "summary",
//                        "http://www.google.com", "http://shaohui.me/images/avatar.gif",
//                        mShareListener);
                break;
            case R.id.rl_cancle:

                break;
        }
        dismiss();
    }
}
