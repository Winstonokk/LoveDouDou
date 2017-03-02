package com.ecity.wangfeng.lovedoudou.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.ecity.wangfeng.lovedoudou.R;

/**
 * description:
 * 仿QQ5.0主页面侧滑的自定View
 * Version：1.0
 */
public class SlidingMenu extends HorizontalScrollView {
    private static final String TAG = "HorizontalScrollView";
    private Context mContext;

    // 4.给菜单和内容View指定宽高 - 左边菜单View
    private View mMenuView;

    // 4.给菜单和内容View指定宽高 - 菜单的宽度
    private int mMenuWidth;
    // 5.3 手势处理类 主要用来处理手势快速滑动
    private GestureDetector mGestureDetector;
    // 5.3 菜单是否打开
    private boolean mMenuIsOpen = false;

    // 7(4). 主页面内容View的布局包括阴影ImageView
    private ViewGroup mContentView;
    // 7.给内容添加阴影效果 - 阴影的ImageView
    private ImageView mShadowIv;

    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //4.1 计算左边菜单的宽度
        //4.1.1 获取自定义的右边留出的宽度
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SlidingMenu);
        float rightPadding = array.getDimension(
                R.styleable.SlidingMenu_rightPadding, dip2px(50));
        // 4.1.2 计算菜单的宽度 = 屏幕的宽度 - 自定义右边留出的宽度
        mMenuWidth = (int) (getScreenWidth() - rightPadding);
        array.recycle();

        // 5.3 实例化手势处理类
        mGestureDetector = new GestureDetector(context, new GestureListener());

        this.mContext = context;
    }

    /**
     * 把dip 转成像素
     */
    private float dip2px(int dip) {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // 4.2 指定菜单和内容View的宽度
        // 4.2.1.获取根View也就是外层的LinearLayout
        ViewGroup container = (ViewGroup) this.getChildAt(0);

        int containerChildCount = container.getChildCount();
        if (containerChildCount > 2) {
            // 里面只允许放置两个布局  一个是Menu(菜单布局) 一个是Content（主页内容布局）
            throw new IllegalStateException("SlidingMenu 根布局LinearLayout下面只允许两个布局,菜单布局和主页内容布局");
        }

        // 4.2.2.获取菜单和内容布局
        mMenuView = container.getChildAt(0);

        // 7.给内容添加阴影效果
        // 7.1 先new一个主内容布局用来放  阴影和LinearLayout原来的内容布局
        mContentView = new FrameLayout(mContext);
        ViewGroup.LayoutParams contentParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        // 7.2 获取原来的内容布局，并把原来的内容布局从LinearLayout中异常
        View oldContentView = container.getChildAt(1);
        container.removeView(oldContentView);

        // 7.3 把原来的内容View 和 阴影加到我们新创建的内容布局中
        mContentView.addView(oldContentView);
        // 7.3.1 创建阴影ImageView
        mShadowIv = new ImageView(mContext);
        mShadowIv.setBackgroundColor(Color.parseColor("#99000000"));
        mContentView.addView(mShadowIv);

        // 7.4 把包含阴影的新的内容View 添加到 LinearLayout中
        container.addView(mContentView);

        // 4.2.3.指定内容和菜单布局的宽度
        // 4.2.3.1 菜单的宽度 = 屏幕的宽度 - 自定义的右边留出的宽度
        mMenuView.getLayoutParams().width = mMenuWidth;
        // 4.2.3.2 内容的宽度 = 屏幕的宽度
        mContentView.getLayoutParams().width = getScreenWidth();
    }

    /**
     * 5.处理手指抬起和快速滑动切换菜单
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // 5.3 处理手指快速滑动
        if (mGestureDetector.onTouchEvent(ev)) {
            return mGestureDetector.onTouchEvent(ev);
        }

        Log.e(TAG, 11 + "");

        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                // 5.1 手指抬起获取滚动的位置
                int currentScrollX = getScrollX();
                if (currentScrollX > mMenuWidth / 2) {
                    // 5.1.1 关闭菜单
                    closeMenu();
                } else {
                    // 5.1.2 打开菜单
                    openMenu();
                }
                return false;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        // l 是 当前滚动的x距离  在滚动的时候会不断反复的回调这个方法
        // Log.e(TAG, l + "");
        // 6. 实现菜单左边抽屉样式的动画效果
        mMenuView.setTranslationX(l * 0.8f);

        // 7.给内容添加阴影效果 - 计算梯度值
        float gradientValue = l * 1f / mMenuWidth;// 这是  1 - 0 变化的值

        // 7.给内容添加阴影效果 - 给阴影的View指定透明度   0 - 1 变化的值
        float shadowAlpha = 1 - gradientValue;
        mShadowIv.setAlpha(shadowAlpha);
    }

    /**
     * 5.1.2 打开菜单
     */
    private void openMenu() {
        smoothScrollTo(0, 0);
        mMenuIsOpen = true;
    }

    /**
     * 5.1.1 关闭菜单
     */
    private void closeMenu() {
        smoothScrollTo(mMenuWidth, 0);
        mMenuIsOpen = false;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        // 布局指定后会从新摆放子布局，当其摆放完毕后，让菜单滚动到不可见状态
        if (changed) {
            scrollTo(mMenuWidth, 0);
        }
    }

    /**
     * 获取屏幕的宽度
     */
    public int getScreenWidth() {
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }


    /**
     * 5.3 处理手指快速滑动
     */
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            // 当手指快速滑动时候回调的方法
            Log.e(TAG, velocityX + "  " + velocityY);
            if (Math.abs(velocityY) > Math.abs(velocityX)) {
                // 判断是不是左右快速滑动
                return false;
            }

            // 5.3.1 如果菜单打开 并且是向左快速滑动 切换菜单的状态
            if (mMenuIsOpen) {
                if (velocityX < 0) {
                    toggleMenu();
                    return true;
                }
            } else {
                // 5.3.2 如果菜单关闭 并且是向右快速滑动 切换菜单的状态
                if (velocityX > 0) {
                    toggleMenu();
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 8.处理事件分发
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mMenuIsOpen) {
            // 如果菜单是打开的  并且手指按下的位置是 大于菜单位置
            int fingerDownX = (int) ev.getX();
            if (fingerDownX > mMenuWidth) {
                // 关闭菜单 并且停止分发事件
                closeMenu();
                return false;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 切换菜单的状态
     */
    public void toggleMenu() {
        if (mMenuIsOpen) {
            closeMenu();
        } else {
            openMenu();
        }
    }
}
