package com.zhixing.work.zhixin.bean;

import java.io.Serializable;

/**
 * Created by lhj on 2018/8/15.
 * Description:
 */

public class WorkBgsBean implements Serializable{


    /**
     * CompanyName : 痛
     * StartDate : 2018-06-07T00:00:00
     * EndDate : 2018-07-10T00:00:00
     */

    private String CompanyName;
    private String StartDate;
    private String EndDate;
    private String Id;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String StartDate) {
        this.StartDate = StartDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String EndDate) {
        this.EndDate = EndDate;
    }
}
