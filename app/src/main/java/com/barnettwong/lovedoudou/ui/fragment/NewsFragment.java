package com.barnettwong.lovedoudou.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.barnettwong.lovedoudou.R;
import com.barnettwong.lovedoudou.app.AppConstant;
import com.barnettwong.lovedoudou.util.MyUtils;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.base.BaseFragmentAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * 新闻
 */
public class NewsFragment extends BaseFragment {
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
//    @BindView(R.id.titleBar)
//    TitleBar titleBar;

    private String[] channelNames;
    private String[] channelTypes;
    private BaseFragmentAdapter fragmentAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_news;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
//        titleBar.setImmersive(false);
//        titleBar.setTitle(getString(R.string.str_hot_news_title));
//        titleBar.setTitleColor(getResources().getColor(R.color.white));
//        titleBar.setBackgroundColor(getResources().getColor(R.color.main_style_color));
        channelNames = getResources().getStringArray(R.array.news_channel_name);
        channelTypes = getResources().getStringArray(R.array.news_channel_type);

        List<Fragment> mNewsFragmentList = new ArrayList<>();
        for (int i = 0; i < channelNames.length; i++) {
            mNewsFragmentList.add(createListFragments(channelTypes[i]));
        }
        fragmentAdapter = new BaseFragmentAdapter(getChildFragmentManager(), mNewsFragmentList, Arrays.asList(channelNames));
        viewPager.setAdapter(fragmentAdapter);
        tabs.setupWithViewPager(viewPager);
        MyUtils.dynamicSetTabLayoutMode(tabs);
        setPageChangeListener();
    }

    private void setPageChangeListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private ChildNewsFragment createListFragments(String channelType) {
        ChildNewsFragment fragment = new ChildNewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.NEWS_TYPE, channelType);
        fragment.setArguments(bundle);
        return fragment;
    }

}
