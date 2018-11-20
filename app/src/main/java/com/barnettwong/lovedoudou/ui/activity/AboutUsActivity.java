package com.barnettwong.lovedoudou.ui.activity;

import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;

import com.barnettwong.lovedoudou.R;
import com.jaydenxiao.common.base.BaseActivity;

import butterknife.BindView;

public class AboutUsActivity extends BaseActivity {
    @BindView(R.id.app_about_text)
    TextView mAboutText;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        mAboutText.setAutoLinkMask(Linkify.ALL);
        mAboutText.setMovementMethod(LinkMovementMethod.getInstance());

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
