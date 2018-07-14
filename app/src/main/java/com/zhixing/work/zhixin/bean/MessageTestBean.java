package com.zhixing.work.zhixin.bean;

/**
 * Created by lhj on 2018/7/13.
 * Description:
 */

public class MessageTestBean {

    private int head;
    private String name;
    private int unRead;
    private String message;

    public MessageTestBean(int head, String name, int unRead, String message) {
        this.head = head;
        this.name = name;
        this.unRead = unRead;
        this.message = message;
    }

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUnRead() {
        return unRead;
    }

    public void setUnRead(int unRead) {
        this.unRead = unRead;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
