package com.zhixing.work.zhixin.requestbody;

/**
 * Created by lhj on 2018/7/13.
 * Description:
 */

public class ExpectedJobBodyBean {

    private int JobTypeId;

    public ExpectedJobBodyBean(int industryTypeId) {
        JobTypeId = industryTypeId;
    }

    public int getIndustryTypeId() {
        return JobTypeId;
    }

    public void setIndustryTypeId(int industryTypeId) {
        JobTypeId = industryTypeId;
    }

}
