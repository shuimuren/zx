package com.zhixing.work.zhixin.event;

/**
 * Created by lhj on 2018/8/7.
 * Description:
 */

public class CreateAttendanceGroupSuccessEvent {
    public boolean isCreateSuccess;

    public boolean isCreateSuccess() {
        return isCreateSuccess;
    }

    public void setCreateSuccess(boolean createSuccess) {
        isCreateSuccess = createSuccess;
    }
}
