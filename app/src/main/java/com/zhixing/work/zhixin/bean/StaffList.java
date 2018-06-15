package com.zhixing.work.zhixin.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/14.
 */

public class StaffList   extends  Staffs{


    public List<StaffList> getChildOrgs() {
        return childOrgs;
    }
    public void setChildOrgs(List<StaffList> childOrgs) {
        this.childOrgs = childOrgs;
    }
    //下级组织列表
    private List<StaffList> childOrgs;

}
