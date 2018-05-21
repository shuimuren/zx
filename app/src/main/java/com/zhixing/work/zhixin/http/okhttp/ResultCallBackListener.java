package com.zhixing.work.zhixin.http.okhttp;


import com.zhixing.work.zhixin.bean.EntityObject;

/**
 *
 */

public interface  ResultCallBackListener<T> {
    void onFailure(int errorId, String msg);

   void onSuccess(EntityObject<T> response);
}
