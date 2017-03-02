package com.ecity.wangfeng.lovedoudou.util;

import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;

public class GravityUtil {

    public static boolean isLeft(int gravity) {
        return gravity == Gravity.START || gravity == Gravity.LEFT;
    }

    public static boolean isLeft(View view) {
        return isLeft(getGravity(view));
    }

    public static boolean isRight(int gravity) {
        return gravity == Gravity.END || gravity == Gravity.RIGHT;
    }

    public static boolean isRight(View view) {
        return isRight(getGravity(view));
    }

    public static int getGravity(View view) {
        if (view.getLayoutParams() instanceof DrawerLayout.LayoutParams) {
            return ((DrawerLayout.LayoutParams) view.getLayoutParams()).gravity;
        }
        throw new IllegalArgumentException("Not child of DrawerLayout");
    }
}
