package com.zhixing.work.zhixin.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/6/8.
 */

public class Department implements Serializable{


    /**
     * CompanyId : 1
     * DepartmentId : 7
     * DepartmentName : XXX有限公司
     */

    private int CompanyId;
    private int DepartmentId;
    private String DepartmentName;

    public int getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(int CompanyId) {
        this.CompanyId = CompanyId;
    }

    public int getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(int DepartmentId) {
        this.DepartmentId = DepartmentId;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String DepartmentName) {
        this.DepartmentName = DepartmentName;
    }
}
