package com.zhixing.work.zhixin.event;

import java.util.Set;

/**
 * Created by lhj on 2018/8/7.
 * Description:
 */

public class SelectedStaffsEvent {

    public Set<Integer> getIntegers() {
        return integers;
    }

    public void setIntegers(Set<Integer> integers) {
        this.integers = integers;
    }

    private Set<Integer> integers;
}
