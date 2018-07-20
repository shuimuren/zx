package com.zhixing.work.zhixin.bean;

import java.util.List;

/**
 * Created by lhj on 2018/7/19.
 * Description:
 */

public class DepartmentSettingInfoBean {


    /**
     * Id : 8
     * CompanyId : 1
     * ParentId : 7
     * ParentName : XXX有限公司
     * Name : 技术部
     * Hide : true
     * ManagerId : null
     * ManagerName : null
     * DepartmentIds : [{"Id":10,"Name":"技术部"}]
     * StaffIds : [{"Id":1,"Name":"www"}]
     */

    private int Id;
    private int CompanyId;
    private int ParentId;
    private String ParentName;
    private String Name;
    private boolean Hide;
    private Integer ManagerId;
    private String ManagerName;
    private List<DepartmentIdsBean> DepartmentIds;
    private List<StaffIdsBean> StaffIds;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(int CompanyId) {
        this.CompanyId = CompanyId;
    }

    public int getParentId() {
        return ParentId;
    }

    public void setParentId(int ParentId) {
        this.ParentId = ParentId;
    }

    public String getParentName() {
        return ParentName;
    }

    public void setParentName(String ParentName) {
        this.ParentName = ParentName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public boolean isHide() {
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

    public String getManagerName() {
        return ManagerName;
    }

    public void setManagerName(String ManagerName) {
        this.ManagerName = ManagerName;
    }

    public List<DepartmentIdsBean> getDepartmentIds() {
        return DepartmentIds;
    }

    public void setDepartmentIds(List<DepartmentIdsBean> DepartmentIds) {
        this.DepartmentIds = DepartmentIds;
    }

    public List<StaffIdsBean> getStaffIds() {
        return StaffIds;
    }

    public void setStaffIds(List<StaffIdsBean> StaffIds) {
        this.StaffIds = StaffIds;
    }

    public static class DepartmentIdsBean {
        /**
         * Id : 10
         * Name : 技术部
         */

        private int Id;
        private String Name;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }
    }

    public static class StaffIdsBean {
        /**
         * Id : 1
         * Name : www
         */

        private int Id;
        private String Name;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }
    }
}
