package com.zhixing.work.zhixin.bean;

import java.util.List;

/**
 * Created by lhj on 2018/7/9.
 * Description:
 */

public class AuthenticateBean {


    /**
     * AuthenticatesType : 10
     * List : [{"AuthenticatesId":1055,"AuthenticatesType":10,"AuthenticatesStatus":0,"ApplicationTime":"2018-07-07 14:05:01","VerifyTime":null,"Message":null}]
     */

    private int AuthenticatesType;
    private List<ListBean> List;

    public int getAuthenticatesType() {
        return AuthenticatesType;
    }

    public void setAuthenticatesType(int AuthenticatesType) {
        this.AuthenticatesType = AuthenticatesType;
    }

    public List<ListBean> getList() {
        return List;
    }

    public void setList(List<ListBean> List) {
        this.List = List;
    }

    public static class ListBean {
        /**
         * AuthenticatesId : 1055
         * AuthenticatesType : 10
         * AuthenticatesStatus : 0
         * ApplicationTime : 2018-07-07 14:05:01
         * VerifyTime : null
         * Message : null
         */

        private int AuthenticatesId;
        private int AuthenticatesType;
        private int AuthenticatesStatus;
        private String ApplicationTime;
        private String VerifyTime;
        private String Message;

        public int getAuthenticatesId() {
            return AuthenticatesId;
        }

        public void setAuthenticatesId(int AuthenticatesId) {
            this.AuthenticatesId = AuthenticatesId;
        }

        public int getAuthenticatesType() {
            return AuthenticatesType;
        }

        public void setAuthenticatesType(int AuthenticatesType) {
            this.AuthenticatesType = AuthenticatesType;
        }

        public int getAuthenticatesStatus() {
            return AuthenticatesStatus;
        }

        public void setAuthenticatesStatus(int AuthenticatesStatus) {
            this.AuthenticatesStatus = AuthenticatesStatus;
        }

        public String getApplicationTime() {
            return ApplicationTime;
        }

        public void setApplicationTime(String ApplicationTime) {
            this.ApplicationTime = ApplicationTime;
        }

        public String getVerifyTime() {
            return VerifyTime;
        }

        public void setVerifyTime(String VerifyTime) {
            this.VerifyTime = VerifyTime;
        }

        public String getMessage() {
            return Message;
        }

        public void setMessage(String Message) {
            this.Message = Message;
        }
    }
}
