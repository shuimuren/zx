package com.zhixing.work.zhixin.requestbody;

import java.util.List;

/**
 * Created by lhj on 2018/7/13.
 * Description:
 */

public class ExpectedJobBody {

    public List<ExpectedJobBodyBean> getBodyBean() {
        return bodyBean;
    }

    public void setBodyBean(List<ExpectedJobBodyBean> bodyBean) {
        this.bodyBean = bodyBean;
    }

    private List<ExpectedJobBodyBean> bodyBean;
}
