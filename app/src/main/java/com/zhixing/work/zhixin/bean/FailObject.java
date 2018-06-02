package com.zhixing.work.zhixin.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/9.
 */

public class FailObject implements Serializable {
    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }





    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    private int Code;

    public Object getContent() {
        return Content;
    }

    public void setContent(Object content) {
        Content = content;
    }

    private Object Content;
    private String Message;
}
