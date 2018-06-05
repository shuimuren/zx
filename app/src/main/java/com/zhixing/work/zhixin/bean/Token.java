package com.zhixing.work.zhixin.bean;

/**
 * Created by Administrator on 2018/6/4.
 */

public class Token {

    /**
     * exp : 1528946263
     * Id : 4
     * PhoneNum : 18565728308
     * Role : 10
     * CompanyId : 0
     * CompanyRole : 0
     */
    private int exp;
    private int Id;
    private String PhoneNum;
    private int Role;
    private int CompanyId;
    private int CompanyRole;
    /**
     * StaffId : 0
     */

    private int StaffId;

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

    public int getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(int CompanyId) {
        this.CompanyId = CompanyId;
    }

    public int getCompanyRole() {
        return CompanyRole;
    }

    public void setCompanyRole(int CompanyRole) {
        this.CompanyRole = CompanyRole;
    }

    public int getStaffId() {
        return StaffId;
    }

    public void setStaffId(int StaffId) {
        this.StaffId = StaffId;
    }
}
