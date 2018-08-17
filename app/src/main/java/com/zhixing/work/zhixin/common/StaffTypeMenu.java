package com.zhixing.work.zhixin.common;

/**
 * Created by lhj on 2018/8/14.
 * Description:
 */

public enum StaffTypeMenu  {
    NO_TYPE("无类型",0),full_time("全职",1),part_time("兼职",2),
    DISPATCH("劳务派遣",3),RETIRED("退休返聘",4),OUTSOURCE("劳务外包",5);

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

    private String name;
    private int index;

    StaffTypeMenu(String fullTime, int i) {
        this.name = fullTime;
        this.index = i;
    }
    // 普通方法
    public static String getName(int index) {
        for (StaffTypeMenu c : StaffTypeMenu.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    public static int getIndex(String  name) {

        for (StaffTypeMenu c : StaffTypeMenu.values()) {
            if (c.getName().equals(name)) {
                return c.index;
            }
        }
        return 0;
    }
}
