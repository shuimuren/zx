package com.zhixing.work.zhixin.event;


public class CardCompleteEvent {
    public CardCompleteEvent() {
        super();
    }



    public void setComplete(boolean complete) {
        Complete = complete;
    }

    public boolean isComplete() {
        return Complete;
    }

    public boolean Complete;

    public CardCompleteEvent(boolean complete) {
        this.Complete = complete;


    }
}
