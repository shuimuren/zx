package com.zhixing.work.zhixin.common;

/**
 * Created by lhj on 2018/8/14.
 * Description:
 */

public enum TestTimeEnum {
    ONE("一个月",1),TWO("两个月",2),THREE("三个月",3),
    FOUR("四个月",4),FIVE("五个月",5),SIX("六个月",6);

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

    TestTimeEnum(String fullTime, int i) {
        this.name = fullTime;
        this.index = i;
    }
    // 普通方法
    public static String getName(int index) {
        for (TestTimeEnum c : TestTimeEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    public static int getIndex(String  name) {

        for (TestTimeEnum c : TestTimeEnum.values()) {
            if (c.getName().equals(name)) {
                return c.index;
            }
        }
        return 0;
    }
}
