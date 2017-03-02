package com.ecity.wangfeng.lovedoudou.presenter;

import android.support.annotation.NonNull;
import com.ecity.wangfeng.lovedoudou.view.BaseView;

/**
 *
 */
public interface BasePresenter {

	//创建
	void onCreate();

	//绑定
	void attachView(@NonNull BaseView vid);

	void onDestroy();
}
