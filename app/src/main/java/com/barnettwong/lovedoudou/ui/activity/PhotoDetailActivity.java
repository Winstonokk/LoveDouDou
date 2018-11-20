package com.barnettwong.lovedoudou.ui.activity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.barnettwong.lovedoudou.R;
import com.barnettwong.lovedoudou.util.SystemUiVisibilityUtil;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonutils.ImageLoaderUtils;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PhotoDetailActivity extends BaseActivity {
    private static final String PHOTO_URL = "PHOTO_URL";

    @BindView(R.id.img_iv)
    ImageView mImgIv;

    @BindView(R.id.photo_iv)
    PhotoView mPhotoIv;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private String mPhotoUrl;

    private boolean isHidden = false;
    private boolean mIsStatusBarHidden = false;

    public static Intent getPhotoDetailIntent(Context context, String photoUrl){
        Intent intent = new Intent(context,PhotoDetailActivity.class);
        intent.putExtra(PHOTO_URL,photoUrl);
        return intent;
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_photo_detail;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        mPhotoUrl = getIntent().getStringExtra(PHOTO_URL);
        ImageLoaderUtils.display(this,mImgIv,mPhotoUrl);

        mPhotoIv.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                hideToolBarAndTextView();
                hideOrShowStatusBar();
            }

        });

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//返回
            }
        });

        initLazyLoadView();
    }

    private void initLazyLoadView(){
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
        }else{
            loadPhotoView();
        }
    }

    private void loadPhotoView(){
        ImageLoaderUtils.display(this,mPhotoIv,mPhotoUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_photo_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:

                return true;
            case R.id.action_share:

                return true;
            case R.id.action_set_wallpaper:

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

    public void hideToolBarAndTextView(){
        isHidden = !isHidden;
        if(isHidden){
            startAnimation(true,1.0f,0.0f);
        }else{
            startAnimation(false,0.1f,1.0f);
        }
    }

    private void startAnimation(final boolean endState, float startValue, float endValue) {
        ValueAnimator animator = ValueAnimator.ofFloat(startValue,endValue).setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float y1;
                if(endState){
                    y1 = (0 - animation.getAnimatedFraction())*mToolbar.getHeight();
                }else{
                    y1 = (animation.getAnimatedFraction() - 1)*mToolbar.getHeight();
                }
                mToolbar.setTranslationY(y1);
            }
        });
        animator.start();
    }
}
