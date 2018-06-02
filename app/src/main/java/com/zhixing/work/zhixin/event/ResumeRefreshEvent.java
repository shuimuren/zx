package com.zhixing.work.zhixin.event;

/*
 *
 */
public class ResumeRefreshEvent {
    public ResumeRefreshEvent(Boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

    public Boolean getRefresh() {
        return isRefresh;
    }

    public void setRefresh(Boolean refresh) {
        isRefresh = refresh;
    }

    private  Boolean isRefresh;
}
