package com.zhixing.work.zhixin.event;

/**
 * Created by lhj on 2018/8/14.
 * Description:
 */

public class RefreshStaffInfoEvent {
    /**
     *
     * @param isBasicInfo true 刷新基础信息,false 刷新高级信息
     */
    public RefreshStaffInfoEvent(boolean isBasicInfo) {
        this.isBasicInfo = isBasicInfo;
    }

    public boolean isBasicInfo() {
        return isBasicInfo;
    }

    public void setBasicInfo(boolean basicInfo) {
        isBasicInfo = basicInfo;
    }

    private boolean isBasicInfo;
}
