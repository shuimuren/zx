package com.zhixing.work.zhixin.http.okhttp;


/**有进度监听
 *   2017/5/23.
 */

public interface ReqProgressCallBack<T> {
    /**
     * 响应进度更新
     */
    void onProgress(float current, long total);
    void onFailure(String fileName, String msg);
    void onSuccess(T response);
}
