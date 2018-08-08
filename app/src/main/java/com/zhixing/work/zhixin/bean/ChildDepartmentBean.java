package com.zhixing.work.zhixin.bean;

import java.io.Serializable;

/**
 * Created by lhj on 2018/7/18.
 * Description:
 */

public class ChildDepartmentBean implements Serializable {
    /**
     * DepartmentId : 8
     * DepartmentName : 技术部
     */

    private int DepartmentId;
    private String DepartmentName;
    private int memberTotal;
    private int typeSelected; //0:正常.1:可选择
    private boolean isSelected; //true 被选中,false 未被选中
    private int selectedTotal; //被选中的总数
    private int parentDepartmentId; //上级departmentId;

    public int getSelectedTotal() {
        return selectedTotal;
    }

    public void setSelectedTotal(int selectedTotal) {
        this.selectedTotal = selectedTotal;
    }

    public int getParentDepartmentId() {
        return parentDepartmentId;
    }

    public void setParentDepartmentId(int parentDepartmentId) {
        this.parentDepartmentId = parentDepartmentId;
    }

    public int getSetlectedTotal() {
        return selectedTotal;
    }

    public void setSetlectedTotal(int setlectedTotal) {
        this.selectedTotal = setlectedTotal;
    }
    public int getTypeSelected() {
        return typeSelected;
    }

    public void setTypeSelected(int typeSelected) {
        this.typeSelected = typeSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public int getMemberTotal() {
        return memberTotal;
    }

    public void setMemberTotal(int memberTotal) {
        this.memberTotal = memberTotal;
    }

    public int getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(int DepartmentId) {
        this.DepartmentId = DepartmentId;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String DepartmentName) {
        this.DepartmentName = DepartmentName;
    }
}
