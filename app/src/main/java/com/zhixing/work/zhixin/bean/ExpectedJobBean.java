package com.zhixing.work.zhixin.bean;

/**
 * Created by lhj on 2018/7/13.
 * Description:
 */

public class ExpectedJobBean {


    private int id;
    private int position;
    private int groupPosition;
    private int childPosition;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getGroupPosition() {
        return groupPosition;
    }

    public void setGroupPosition(int groupPosition) {
        this.groupPosition = groupPosition;
    }

    public int getChildPosition() {
        return childPosition;
    }

    public void setChildPosition(int childPosition) {
        this.childPosition = childPosition;
    }


    public ExpectedJobBean(int id, int position, int groupPosition, int childPosition) {
        this.id = id;
        this.position = position;
        this.groupPosition = groupPosition;
        this.childPosition = childPosition;
    }



}
