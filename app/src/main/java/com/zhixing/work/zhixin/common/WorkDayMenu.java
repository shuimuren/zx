package com.zhixing.work.zhixin.common;

/**
 * Created by lhj on 2018/8/7.
 * Description:
 */

public enum  WorkDayMenu {

    MONDAY("星期一",1),Tuesday("星期二",2),Wednesday("星期三",4),
    Thursday("星期四",8),Friday("星期五",16) ,Saturday("星期六",32),
    Sunday("星期日",64);



    private String name;
    private int index;

    WorkDayMenu(String work, int i) {
        this.name = work;
        this.index = i;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


    // 普通方法
    public static String getName(int index) {
        for (WorkDayMenu c : WorkDayMenu.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
}
