package com.zhixing.work.zhixin.bean;

import java.util.List;

/**
 * Created by lhj on 2018/7/11.
 * Description:
 */

public class JobChoice {


    /**
     * Id : 1
     * ParentId : 0
     * Name : 销售|客服|市场
     * Child : [{"Id":2,"ParentId":1,"Name":"销售业务","Child":[{"Id":3,"ParentId":2,"Name":"销售代表","Child":[]},{"Id":4,"ParentId":2,"Name":"客户代表","Child":[]},{"Id":5,"ParentId":2,"Name":"销售工程师","Child":[]},{"Id":6,"ParentId":2,"Name":"渠道/分销专员","Child":[]},{"Id":7,"ParentId":2,"Name":"区域销售专员/助理","Child":[]},{"Id":8,"ParentId":2,"Name":"业务拓展专员/助理","Child":[]},{"Id":9,"ParentId":2,"Name":"大客户销售代表","Child":[]},{"Id":10,"ParentId":2,"Name":"电话销售","Child":[]},{"Id":11,"ParentId":2,"Name":"网络/在线销售","Child":[]},{"Id":12,"ParentId":2,"Name":"团购业务员","Child":[]},{"Id":13,"ParentId":2,"Name":"销售业务跟单","Child":[]},{"Id":14,"ParentId":2,"Name":"医药代表","Child":[]},{"Id":15,"ParentId":2,"Name":"经销商","Child":[]},{"Id":16,"ParentId":2,"Name":"招商经理","Child":[]},{"Id":17,"ParentId":2,"Name":"招商主管","Child":[]},{"Id":18,"ParentId":2,"Name":"招商专员","Child":[]},{"Id":19,"ParentId":2,"Name":"会籍顾问","Child":[]},{"Id":20,"ParentId":2,"Name":"其他","Child":[]}]},{"Id":21,"ParentId":1,"Name":"销售管理","Child":[{"Id":22,"ParentId":21,"Name":"销售总监","Child":[]},{"Id":23,"ParentId":21,"Name":"销售经理","Child":[]},{"Id":24,"ParentId":21,"Name":"销售主管","Child":[]},{"Id":25,"ParentId":21,"Name":"客户总监","Child":[]},{"Id":26,"ParentId":21,"Name":"客户经理","Child":[]},{"Id":27,"ParentId":21,"Name":"客户主管","Child":[]},{"Id":28,"ParentId":21,"Name":"渠道/分销总监","Child":[]},{"Id":29,"ParentId":21,"Name":"渠道/分销经理/主管","Child":[]},{"Id":30,"ParentId":21,"Name":"区域销售总监","Child":[]},{"Id":31,"ParentId":21,"Name":"区域销售经理/主管","Child":[]},{"Id":32,"ParentId":21,"Name":"业务拓展经理/主管","Child":[]}
     */

    private int Id;
    private int ParentId;
    private String Name;
    private List<JobParentBean> Child;

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

    public List<JobParentBean> getChild() {
        return Child;
    }

    public void setChild(List<JobParentBean> Child) {
        this.Child = Child;
    }



}
