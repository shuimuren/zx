package com.zhixing.work.zhixin.bean;

import java.util.List;

/**
 * Created by lhj on 2018/8/24.
 * Description:
 */

public class AwardOrPunishmentBean {

    private String time;
    private List<CareerAwardPunishment> list;

    public AwardOrPunishmentBean(String time, List<CareerAwardPunishment> list) {
        this.time = time;
        this.list = list;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<CareerAwardPunishment> getList() {
        return list;
    }

    public void setList(List<CareerAwardPunishment> list) {
        this.list = list;
    }


}
