package com.zhixing.work.zhixin.bean;

import java.util.List;

/**
 * Created by lhj on 2018/8/27.
 * Description: 月凭
 */

public class MonthStatementBean {


    private String department;
    private List<StaffStatementBean> staffs;

    public MonthStatementBean(String department, List<StaffStatementBean> staffs) {
        this.department = department;
        this.staffs = staffs;
    }



    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<StaffStatementBean> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<StaffStatementBean> staffs) {
        this.staffs = staffs;
    }
}
