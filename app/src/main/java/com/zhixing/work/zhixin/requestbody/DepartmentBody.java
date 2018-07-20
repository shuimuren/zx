package com.zhixing.work.zhixin.requestbody;

import java.util.List;

/**
 * Created by lhj on 2018/7/18.
 * Description:
 */

public class DepartmentBody {

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
    private boolean Hide;
    private Integer ManagerId;
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

    public boolean getHide() {
        return Hide;
    }

    public void setHide(boolean Hide) {
        this.Hide = Hide;
    }

    public Integer getManagerId() {
        return ManagerId;
    }

    public void setManagerId(Integer ManagerId) {
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
