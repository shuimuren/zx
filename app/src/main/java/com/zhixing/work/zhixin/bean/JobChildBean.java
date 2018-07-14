package com.zhixing.work.zhixin.bean;

import java.util.List;

/**
 * Created by lhj on 2018/7/11.
 * Description:
 */

public class JobChildBean {
    /**
     * Id : 3
     * ParentId : 2
     * Name : 销售代表
     * Child : []
     */

    private int Id;
    private int ParentId;
    private String Name;
    private List<?> Child;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private boolean isChecked;

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

    public List<?> getChild() {
        return Child;
    }

    public void setChild(List<?> Child) {
        this.Child = Child;
    }
}
