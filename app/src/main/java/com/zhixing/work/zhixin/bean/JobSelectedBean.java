package com.zhixing.work.zhixin.bean;

/**
 * Created by lhj on 2018/7/12.
 * Description:
 */

public class JobSelectedBean {


    private int Id;
    private int ParentId;
    private String Name;

    public JobSelectedBean(int id, int parentId, String name) {
        Id = id;
        ParentId = parentId;
        Name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getParentId() {
        return ParentId;
    }

    public void setParentId(int parentId) {
        ParentId = parentId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

}
