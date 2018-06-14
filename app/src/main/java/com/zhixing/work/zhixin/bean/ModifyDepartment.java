package com.zhixing.work.zhixin.bean;

import java.util.List;

/**
 *
 */

public class ModifyDepartment {


    /**
     * Id : 9
     * ParentId : 8
     * Name : 后端
     * Hide : True
     * ManagerId : null
     * DepartmentIds : [10]
     * StaffIds : []
     */

    private int Id;
    private int ParentId;
    private String Name;
    private String Hide;
    private String ManagerId;
    private List<Integer> DepartmentIds;
    private List<Integer> StaffIds;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getParentId() {
        return ParentId;
    }

    public void setParentId(int ParentId) {
        this.ParentId = ParentId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getHide() {
        return Hide;
    }

    public void setHide(String Hide) {
        this.Hide = Hide;
    }

    public String getManagerId() {
        return ManagerId;
    }

    public void setManagerId(String ManagerId) {
        this.ManagerId = ManagerId;
    }

    public List<Integer> getDepartmentIds() {
        return DepartmentIds;
    }

    public void setDepartmentIds(List<Integer> DepartmentIds) {
        this.DepartmentIds = DepartmentIds;
    }

    public List<Integer> getStaffIds() {
        return StaffIds;
    }

    public void setStaffIds(List<Integer> StaffIds) {
        this.StaffIds = StaffIds;
    }
}
