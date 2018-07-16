package com.zhixing.work.zhixin.event;

/**
 * Created by lhj on 2018/7/14.
 * Description:
 */

public class EvaluationEvent {

    public EvaluationEvent(boolean refresh) {
        this.refresh = refresh;
    }

    public boolean isRefresh() {
        return refresh;
    }

    public void setRefresh(boolean refresh) {
        this.refresh = refresh;
    }

    private boolean refresh;
}
