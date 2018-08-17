package com.zhixing.work.zhixin.common;

/**
 * Created by lhj on 2018/8/14.
 * Description:
 */

public enum StaffStatusMenu {
    NO_status("无状态", 0), test_status("试用", 1), formal_status("正式", 2),
    leave_status("离职", 3);

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

    StaffStatusMenu(String name, int index) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(int index) {
        for (StaffStatusMenu c : StaffStatusMenu.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    public static int getIndex(String  name) {

        for (StaffStatusMenu c : StaffStatusMenu.values()) {
            if (c.getName().equals(name)) {
                return c.index;
            }
        }
        return 0;
    }

}
