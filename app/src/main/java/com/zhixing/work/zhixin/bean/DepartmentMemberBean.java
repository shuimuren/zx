package com.zhixing.work.zhixin.bean;

/**
 * Created by lhj on 2018/7/19.
 * Description: 组织架构下子成员
 */

public class DepartmentMemberBean {

    private String departmentId;
    private String departmentName;

    public DepartmentMemberBean(String departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
