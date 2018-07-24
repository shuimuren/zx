package com.zhixing.work.zhixin.bean;

/**
 * Created by lhj on 2018/7/23.
 * Description:
 */

public class SelectedDepartmentBean {

    private int DepartmentId;
    private String DepartmentName;
    private boolean isUser;
    private String userAvatar;
    private String userId;

    public SelectedDepartmentBean(int departmentId, String departmentName, boolean isUser, String userAvatar, String userId) {
        DepartmentId = departmentId;
        DepartmentName = departmentName;
        this.isUser = isUser;
        this.userAvatar = userAvatar;
        this.userId = userId;
    }

    public int getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(int departmentId) {
        DepartmentId = departmentId;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public boolean isUser() {
        return isUser;
    }

    public void setUser(boolean user) {
        isUser = user;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
