package com.zhixing.work.zhixin.event;

/**
 * Created by lhj on 2018/7/14.
 * Description:
 */

public class UserBasicInfoSubmitEvent {

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    private boolean success;
    public UserBasicInfoSubmitEvent(boolean success) {
        this.success = success;
    }

}
