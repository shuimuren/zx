package com.zhixing.work.zhixin.bean;

/**
 * Created by lhj on 2018/7/10.
 * Description:
 */

public class CertificationBody {

    int AuthenticatesId;
    CertificationBean info;

    public int getAuthenticatesId() {
        return AuthenticatesId;
    }

    public void setAuthenticatesId(int authenticatesId) {
        AuthenticatesId = authenticatesId;
    }

    public CertificationBean getInfo() {
        return info;
    }

    public void setInfo(CertificationBean info) {
        this.info = info;
    }
}
