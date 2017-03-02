package com.ecity.wangfeng.lovedoudou.activity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ecity.wangfeng.lovedoudou.R;
import com.ecity.wangfeng.lovedoudou.base.BaseActivity;
import com.ecity.wangfeng.lovedoudou.presenter.photo.PhotoDetailPresenter;
import com.ecity.wangfeng.lovedoudou.presenter.photo.PhotoDetailPresenterImpl;
import com.ecity.wangfeng.lovedoudou.util.SystemUiVisibilityUtil;
import com.ecity.wangfeng.lovedoudou.view.photo.PhotoDetailView;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 1.共享元素 动画，使用 PhotoView 作为共享元素时 ，动画不流畅
 * 2.当使用Glide加载图片时，会导致 ImageView 与 PhotoView 呈现的大小不一样
 */

public class PhotoDetailActivity extends BaseActivity<PhotoDetailPresenter> implements PhotoDetailView {

    private static final String PHOTO_URL = "PHOTO_URL";
    @Bind(R.id.img_iv)
    ImageView mImgIv;
    @Bind(R.id.photo_iv)
    PhotoView mPhotoIv;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;


    private String mPhotoUrl;

    private boolean isHidden = false;
    private boolean mIsStatusBarHidden = false;

    public static Intent getPhotoDetailIntent(Context context, String photoUrl) {
        Intent intent = new Intent(context, PhotoDetailActivity.class);
        intent.putExtra(PHOTO_URL, photoUrl);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_photo_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                mPresenter.savePhoto(mPhotoUrl);
                return true;
            case R.id.action_share:
                mPresenter.sharePhoto(mPhotoUrl);
                return true;
            case R.id.action_set_wallpaper:
                mPresenter.setWallpaper(mPhotoUrl);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void hideOrShowStatusBar() {
        if (mIsStatusBarHidden) {
            SystemUiVisibilityUtil.enter(PhotoDetailActivity.this);
        } else {
            SystemUiVisibilityUtil.exit(PhotoDetailActivity.this);
        }
        mIsStatusBarHidden = !mIsStatusBarHidden;
    }

    public void hideToolBarAndTextView() {
        isHidden = !isHidden;
        if (isHidden) {
            startAnimation(true, 1.0f, 0.0f);
        } else {
            startAnimation(false, 0.1f, 1.0f);
        }
    }

    private void startAnimation(final boolean endState, float startValue, float endValue) {
        ValueAnimator animator = ValueAnimator.ofFloat(startValue, endValue).setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float y1;
                if (endState) {
                    y1 = (0 - animation.getAnimatedFraction()) * mToolbar.getHeight();
                } else {
                    y1 = (animation.getAnimatedFraction() - 1) * mToolbar.getHeight();
                }
                mToolbar.setTranslationY(y1);
            }
        });
        animator.start();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_photo_detail;
    }

    @Override
    public void initVariables() {
        mPhotoUrl = getIntent().getStringExtra(PHOTO_URL);
        initPresenter();
    }

    private void initPresenter() {
        mPresenter = new PhotoDetailPresenterImpl(this);
        mPresenter.attachView(this);
    }

    @Override
    public void initViews() {

        Picasso.with(this)
                .load(mPhotoUrl)
                .placeholder(R.mipmap.ic_loading)
                .error(R.mipmap.ic_load_fail)
                .into(mImgIv);

        mPhotoIv.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                hideToolBarAndTextView();
                hideOrShowStatusBar();
            }

            @Override
            public void onOutsidePhotoTap() {

            }
        });

        initLazyLoadView();
    }

    private void initLazyLoadView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getEnterTransition().addListener(new Transition.TransitionListener() {

                @Override
                public void onTransitionStart(Transition transition) {

                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    loadPhotoView();
                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });
        } else {
            loadPhotoView();
        }
    }

    private void loadPhotoView() {
        Picasso.with(this)
                .load(mPhotoUrl)
                .into(mPhotoIv);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMsg(String message) {
        Toast.makeText(PhotoDetailActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
