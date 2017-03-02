package com.ecity.wangfeng.lovedoudou.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecity.wangfeng.lovedoudou.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {
    @Bind(R.id.logo_bg)
    ImageView logoBg;
    @Bind(R.id.logo_word)
    ImageView logoWord;
    @Bind(R.id.logo_trumpet)
    ImageView logoTrumpet;
    @Bind(R.id.app_name_tv)
    TextView appNameTv;
    private boolean isShowingRubberEffect = false;

    private boolean isFirstIn = false;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.zoomin, 0);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        SharedPreferences preferences = getSharedPreferences("first_pref",
                MODE_PRIVATE);
        isFirstIn = preferences.getBoolean("isFirstIn", true);

        initAnimation();
    }

    private void initAnimation() {
        startLogoInner1();
        startLogoOuterAndAppName();
    }

    private void startLogoInner1() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_top_in);
        logoWord.startAnimation(animation);
    }

    private void startLogoOuterAndAppName() {
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                if (fraction >= 0.8 && !isShowingRubberEffect) {
                    isShowingRubberEffect = true;
                    startLogoOuter();
                    startShowAppName();
                    finishActivity();
                } else if (fraction >= 0.95) {
                    valueAnimator.cancel();
                    startLogoInner2();
                }

            }
        });
        valueAnimator.start();
    }

    private void startLogoOuter() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(1000);
        animatorSet.playTogether(ObjectAnimator.ofFloat(logoBg, "scaleX", new float[]{1.0F, 1.25F, 0.75F, 1.15F, 1.0F}),
                ObjectAnimator.ofFloat(logoBg, "scaleY", new float[]{1.0F, 0.75F, 1.25F, 0.85F, 1.0F}));
        animatorSet.start();
    }

    private void startShowAppName() {
        ObjectAnimator.ofFloat(appNameTv, "alpha", new float[]{0, 1}).setDuration(1000).start();
        ObjectAnimator.ofFloat(logoTrumpet, "alpha", new float[]{0, 1}).setDuration(1000).start();
    }

    private void startLogoInner2() {
        ObjectAnimator.ofFloat(logoWord, "translationY", new float[]{0.0F, 0.0F, -30.0F, 0.0F, -15.0F, 0.0F, 0.0F});
    }

    private void finishActivity() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    if (isFirstIn) {
                        intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                    } else {
                        intent = new Intent(SplashActivity.this, MainActivity.class);
                    }
                    startActivity(intent);
                    overridePendingTransition(0, android.R.anim.fade_out);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
