package com.zhixing.work.zhixin.bean;

import android.text.TextUtils;

import com.zhixing.work.zhixin.aliyun.ALiYunFileURLBuilder;

import java.io.Serializable;

/**
 * Created by lhj on 2018/7/18.
 * Description:
 */

public class DepartmentMemberInfoBean implements Serializable{

    /**
     * StaffId : 2
     * StaffNickName : dd
     * StaffAvatar : null
     * StaffJobType : 运营总监
     * StaffRole : 0
     */

    private int StaffId;
    private String StaffNickName;
    private String StaffAvatar;
    private String StaffJobType;
    private int StaffRole;
    private boolean isSelected;
    private int parentDepartmentId; //上级departmentId

    public int getParentDepartmentId() {
        return parentDepartmentId;
    }

    public void setParentDepartmentId(int parentDepartmentId) {
        this.parentDepartmentId = parentDepartmentId;
    }



    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }



    public int getStaffId() {
        return StaffId;
    }

    public void setStaffId(int StaffId) {
        this.StaffId = StaffId;
    }

    public String getStaffNickName() {
        return StaffNickName;
    }

    public void setStaffNickName(String StaffNickName) {
        this.StaffNickName = StaffNickName;
    }

    public String getStaffAvatar() {
        if (TextUtils.isEmpty(StaffAvatar)) {
            return "";
        } else {
            return ALiYunFileURLBuilder.PUBLIC_END_POINT + StaffAvatar;
        }

    }

    public void setStaffAvatar(String StaffAvatar) {
        this.StaffAvatar = StaffAvatar;
    }

    public String getStaffJobType() {
        return StaffJobType;
    }

    public void setStaffJobType(String StaffJobType) {
        this.StaffJobType = StaffJobType;
    }

    public int getStaffRole() {
        return StaffRole;
    }

    public void setStaffRole(int StaffRole) {
        this.StaffRole = StaffRole;
    }
}
