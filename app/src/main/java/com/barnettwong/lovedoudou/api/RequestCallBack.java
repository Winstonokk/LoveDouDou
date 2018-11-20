package com.barnettwong.lovedoudou.api;

/**
 *  请求回调监听接口
 */
public interface RequestCallBack<T> {

    void beforeRequest();

    void success(T data);

    void onError(String errorMsg);

}
