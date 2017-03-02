package com.ecity.wangfeng.lovedoudou;

import android.app.Application;
import android.content.Context;

import com.socks.library.KLog;

/**
 * @version 1.0
 */
public class MyApplication extends Application{

    private static Context mInstance;

    public static Context getInstance(){
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        KLog.init(true);
    }
}
