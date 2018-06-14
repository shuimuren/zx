package com.zhixing.work.zhixin.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/6/4.
 */

public class Token  implements Serializable{

    /**
     * exp : 1529213903
     * Id : 4
     * PhoneNum : 18565728308
     * Role : 10
     * StaffId : 0
     * CompanyId : 0
     * StaffRole : 0
     */

    private int exp;
    private int Id;
    private String PhoneNum;
    private int Role;
    private int StaffId;
    private int CompanyId;
    private int StaffRole;

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getPhoneNum() {
        return PhoneNum;
    }

    public void setPhoneNum(String PhoneNum) {
        this.PhoneNum = PhoneNum;
    }

    public int getRole() {
        return Role;
    }

    public void setRole(int Role) {
        this.Role = Role;
    }

    public int getStaffId() {
        return StaffId;
    }

    public void setStaffId(int StaffId) {
        this.StaffId = StaffId;
    }

    public int getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(int CompanyId) {
        this.CompanyId = CompanyId;
    }

    public int getStaffRole() {
        return StaffRole;
    }

    public void setStaffRole(int StaffRole) {
        this.StaffRole = StaffRole;
    }
}
