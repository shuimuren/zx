package com.zhixing.work.zhixin.event;

/**
 * Created by lhj on 2018/7/23.
 * Description:
 */

public class SelectedDepartmentEvent {
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

    public SelectedDepartmentEvent(String departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    private String departmentId;
    private String departmentName;
}
