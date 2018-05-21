package com.zhixing.work.zhixin.event;

/**安全退出app 通知关闭所有页面
 *
 */
public class CardRefreshEvent {
    public CardRefreshEvent(Boolean isRefresh) {
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
