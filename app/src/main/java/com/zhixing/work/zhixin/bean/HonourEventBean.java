package com.zhixing.work.zhixin.bean;

/**
 * Created by lhj on 2018/8/27.
 * Description: 荣誉事件
 */

public class HonourEventBean {
    public HonourEventBean(String event, String time) {
        this.event = event;
        this.time = time;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String event;
    private String time;
}
