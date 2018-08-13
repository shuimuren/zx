package com.zhixing.work.zhixin.event;

/**
 * Created by lhj on 2018/8/10.
 * Description:
 */

public class HandlerApplyEvent {

    public HandlerApplyEvent(boolean handlerSuccess) {
        this.handlerSuccess = handlerSuccess;
    }

    private boolean handlerSuccess;
}
