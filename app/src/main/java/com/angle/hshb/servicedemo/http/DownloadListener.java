package com.angle.hshb.servicedemo.http;

/**
 * Created by angle
 * 2017/11/28.
 */

public interface DownloadListener {

    //通知当前下载进度
    void onProgress(int progress);

    //通知下载失败
    void onSuccess();

    //通知下载失败
    void onFailed();

    //通知下载暂停
    void onPause();

    //通知下载取消
    void onCanceled();
}
