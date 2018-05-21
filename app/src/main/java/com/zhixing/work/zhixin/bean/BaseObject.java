package com.zhixing.work.zhixin.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/9.
 */

public class BaseObject<T> implements Serializable {
    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }



    public void setContent(String content) {
        Content = content;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    private int Code;

    public String getContent() {
        return Content;
    }

    private String Content;
    private String Message;
}
