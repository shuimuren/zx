package com.zhixing.work.zhixin.bean;

import java.util.List;

/**
 * Created by lhj on 2018/7/20.
 * Description:
 */

public class DepartmentStaffsBean {
    /**
     * DepartmentId : 7
     * ParentId : 0
     * StaffIds : [1,2]
     */

    private int DepartmentId;
    private int ParentId;
    private List<Integer> StaffIds;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }



    public int getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(int DepartmentId) {
        this.DepartmentId = DepartmentId;
    }

    public int getParentId() {
        return ParentId;
    }

    public void setParentId(int ParentId) {
        this.ParentId = ParentId;
    }

    public List<Integer> getStaffIds() {
        return StaffIds;
    }

    public void setStaffIds(List<Integer> StaffIds) {
        this.StaffIds = StaffIds;
    }
}
