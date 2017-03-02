package com.ecity.wangfeng.lovedoudou.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ecity.wangfeng.lovedoudou.R;
import com.ecity.wangfeng.lovedoudou.fragment.GuildFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.ecity.wangfeng.lovedoudou.R.id.bt_start;

/**
 * Created by wangfeng on 2017/1/6.
 */

public class WelcomeActivity extends AppCompatActivity {
    @Bind(R.id.vp)
    ViewPager vp;
    @Bind(R.id.iv1)
    ImageView iv1;
    @Bind(R.id.iv2)
    ImageView iv2;
    @Bind(R.id.iv3)
    ImageView iv3;
    @Bind(bt_start)
    Button btStart;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        initData();
        initViewPager();
        initListener();
    }

    private void initListener() {
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //存储是否第一次进入状态
                SharedPreferences preferences = getSharedPreferences(
                        "first_pref", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isFirstIn", false);
                editor.commit();

                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * 初始化数据,添加三个Fragment
     */
    private void initData() {
        fragments = new ArrayList<>();

        Fragment fragment1 = new GuildFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("index", 1);
        fragment1.setArguments(bundle1);
        fragments.add(fragment1);

        Fragment fragment2 = new GuildFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("index", 2);
        fragment2.setArguments(bundle2);
        fragments.add(fragment2);

        Fragment fragment3 = new GuildFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putInt("index", 3);
        fragment3.setArguments(bundle3);
        fragments.add(fragment3);
    }

    /**
     * 设置ViewPager的适配器和滑动监听
     */
    private void initViewPager() {
        vp.setOffscreenPageLimit(3);
        vp.setAdapter(new MyPageAdapter(getSupportFragmentManager()));
        vp.addOnPageChangeListener(new MyPageChangeListener());
    }

    /**
     * ViewPager适配器
     */
    private class MyPageAdapter extends FragmentPagerAdapter {


        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    /**
     * ViewPager滑动页面监听器
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        /**
         * 根据页面不同动态改变红点和在最后一页显示立即体验按钮
         *
         * @param position
         */
        @Override
        public void onPageSelected(int position) {
            btStart.setVisibility(View.GONE);
            iv1.setImageResource(R.mipmap.dot_normal);
            iv2.setImageResource(R.mipmap.dot_normal);
            iv3.setImageResource(R.mipmap.dot_normal);
            if (position == 0) {
                iv1.setImageResource(R.mipmap.dot_focus);
            } else if (position == 1) {
                iv2.setImageResource(R.mipmap.dot_focus);
            } else {
                iv3.setImageResource(R.mipmap.dot_focus);
                btStart.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
