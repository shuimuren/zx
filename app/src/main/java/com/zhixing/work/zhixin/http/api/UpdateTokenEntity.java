package com.zhixing.work.zhixin.http.api;

import java.io.Serializable;

/**
 * 项目名称:HardTask
 * Created by lovezh
 * CreatedData: on 2018/4/17.
 */

public class UpdateTokenEntity implements Serializable {

    /**
     * Code : 10000
     * Content : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJFeHBpcmVzIjoxNTI0MDM3ODI0LCJJZCI6NCwiUGhvbmVOdW0iOiIxODY3Njc0MDg3NSIsIlJvbGUiOjEwfQ.PDdBWTnB3TRgEGRKGIZWS07MvkYJaC6WvOJBarXWy-0
     * Message :
     */

    private int Code;
    private String Content;
    private String Message;

    public int getCode() {

        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }
}
