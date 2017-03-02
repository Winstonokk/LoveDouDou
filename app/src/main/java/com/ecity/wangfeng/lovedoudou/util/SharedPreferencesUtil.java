package com.ecity.wangfeng.lovedoudou.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.ecity.wangfeng.lovedoudou.MyApplication;


/**
 * @version 1.0
 * Created by Administrator on 2016/11/14 0014.
 */
public class SharedPreferencesUtil {

    private static final String SHARES_AIDOUDOU = "shares_aidoudou";
    public static final String INIT_DB = "init_db";

    public static SharedPreferences getSharedPreference(){
        return MyApplication.getInstance().getSharedPreferences(SHARES_AIDOUDOU, Context.MODE_PRIVATE);
    }

    public static boolean isInitDB(){
        return getSharedPreference().getBoolean(INIT_DB,false);
    }

    public static void updateInitDBValue(boolean flag){
        getSharedPreference().edit().putBoolean(INIT_DB,flag).apply();
    }

}
