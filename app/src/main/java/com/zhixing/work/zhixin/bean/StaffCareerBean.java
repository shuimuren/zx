package com.zhixing.work.zhixin.bean;

/**
 * Created by lhj on 2018/8/23.
 * Description:
 */

public class StaffCareerBean {

    private float scort;
    private String companyName;
    private String companyAvator;

    public float getScort() {
        return scort;
    }

    public void setScort(float scort) {
        this.scort = scort;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAvator() {
        return companyAvator;
    }

    public void setCompanyAvator(String companyAvator) {
        this.companyAvator = companyAvator;
    }



    public StaffCareerBean(float scort, String companyName, String companyAvator) {
        this.scort = scort;
        this.companyName = companyName;
        this.companyAvator = companyAvator;
    }

}
