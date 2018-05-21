package com.zhixing.work.zhixin.http.okhttp;


import com.zhixing.work.zhixin.bean.BaseObject;
import com.zhixing.work.zhixin.bean.EntityObject;

/**
 *
 */

public interface BaseResultCallBackListener<T> {
    void onFailure(int errorId, String msg);

   void onSuccess(BaseObject<T> response);
}
