package com.zhixing.work.zhixin.event;

import com.zhixing.work.zhixin.bean.IndustryType;

import java.util.List;

/**
 * 公司选择行业
 */
public class CompanyIndustryEvent {


    public IndustryType getIndustryType() {
        return industryType;
    }

    public void setIndustryType(IndustryType industryType) {
        this.industryType = industryType;
    }

    public CompanyIndustryEvent(IndustryType industryType) {
        this.industryType = industryType;
    }

    private  IndustryType industryType;
}
