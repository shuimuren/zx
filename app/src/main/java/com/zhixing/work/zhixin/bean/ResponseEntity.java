package com.zhixing.work.zhixin.bean;

/**
 *

 */

public class ResponseEntity {



    private int Code;
    private boolean Content;
    private String Message;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public boolean isContent() {
        return Content;
    }

    public void setContent(boolean Content) {
        this.Content = Content;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }
}
