package com.zhixing.work.zhixin.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/9.
 */

public class EntityObject<T> implements Serializable {
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

    public T getContent() {
        return Content;
    }

    public void setContent(T content) {
        Content = content;
    }

    private T Content;
    private String Message;
}
