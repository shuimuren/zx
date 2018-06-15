package com.zhixing.work.zhixin.bean;

import java.io.Serializable;
import java.util.List;

/**
 *
 */

public class Staffs  implements Serializable{


    /**
     * DepartmentId : 16
     * ParentId : 14
     * StaffIds : [11,12,13]
     */

    private int DepartmentId;
    private Integer ParentId;
    private List<Integer> StaffIds;

    public int getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(int DepartmentId) {
        this.DepartmentId = DepartmentId;
    }

    public Integer getParentId() {
        return ParentId;
    }

    public void setParentId(Integer ParentId) {
        this.ParentId = ParentId;
    }

    public List<Integer> getStaffIds() {
        return StaffIds;
    }

    public void setStaffIds(List<Integer> StaffIds) {
        this.StaffIds = StaffIds;
    }
}
