package com.barnettwong.lovedoudou.ui.activity;

import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.barnettwong.lovedoudou.R;
import com.barnettwong.lovedoudou.bean.ReviewDetails;
import com.barnettwong.lovedoudou.mvp.contract.MovieDetailContract;
import com.barnettwong.lovedoudou.mvp.model.MovieDetailModel;
import com.barnettwong.lovedoudou.mvp.presenter.MovieDetailPresenter;
import com.barnettwong.lovedoudou.ui.view.TitleBar;
import com.barnettwong.lovedoudou.util.HtmlHelper;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonutils.ImageLoaderUtils;
import com.jaydenxiao.common.commonutils.ToastUitl;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class CommentDetailActivity extends BaseActivity<MovieDetailPresenter, MovieDetailModel> implements MovieDetailContract.View {
    @BindView(R.id.tv_detail_title)
    TextView tvTitle;
    @BindView(R.id.iv_movie_pic)
    ImageView ivMoviePic;
    @BindView(R.id.tv_publish_time)
    TextView tvPublishTime;
    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_movie_name)
    TextView tvMovieName;
    @BindView(R.id.wv_content)
    WebView wvContent;
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;
    @BindView(R.id.view_content)
    NestedScrollView viewContent;
    @BindView(R.id.titleBar)
    TitleBar titleBar;

    private int reviewId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_comment_detail;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        titleBar.setImmersive(false);
        titleBar.setTitle("影评详情");
        titleBar.setTitleColor(getResources().getColor(R.color.white));
        titleBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        titleBar.setLeftImageResource(R.mipmap.back);
        titleBar.setLeftClickListener(v -> finish());
        reviewId = getIntent().getIntExtra("reviewId", 0);
        setupWebView();
        mPresenter.startMovieDetailRequest(reviewId);
    }

    private void setupWebView() {
        WebViewClient client = new WebViewClient();
        WebSettings settings = wvContent.getSettings();
        settings.setJavaScriptEnabled(true);
        wvContent.setWebViewClient(client);
    }

    @Override
    public void returnResult(ReviewDetails reviewDetails) {
        setupHead(reviewDetails);
        setupWebData(reviewDetails);
    }

    @Override
    public void showLoading(String title) {
        pbLoading.setVisibility(View.VISIBLE);
        viewContent.setVisibility(View.GONE);
    }

    @Override
    public void stopLoading() {
        viewContent.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.GONE);
    }

    @Override
    public void showErrorTip(String msg) {
        ToastUitl.showShort(msg);
    }

    private void setupWebData(ReviewDetails reviewDetailData) {
        HtmlHelper htmlHelper = new HtmlHelper(reviewDetailData.getContent());
        wvContent.loadDataWithBaseURL(null, htmlHelper.getDoc().toString(), "text/html", "utf-8", null);
    }

    private void setupHead(ReviewDetails reviewDetailData) {
        tvTitle.setText(reviewDetailData.getTitle().trim());
        tvPublishTime.setText(reviewDetailData.getTime());
        ImageLoaderUtils.displayRound(this, ivAvatar, reviewDetailData.getUserImage());
        tvUserName.setText(new StringBuilder()
                .append(reviewDetailData.getNickname()).append(" 评"));
        final ReviewDetails.RelatedObjBean relatedObj = reviewDetailData.getRelatedObj();
        if (relatedObj != null) {
            ImageLoaderUtils.display(this, ivMoviePic, relatedObj.getImage());
            tvMovieName.setText(relatedObj.getTitle());
//            ivMoviePic.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startMovieDetailActivity(relatedObj.getId());
//                }
//            });
        }
    }
}
