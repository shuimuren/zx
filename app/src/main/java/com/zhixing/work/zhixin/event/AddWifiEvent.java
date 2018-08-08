package com.zhixing.work.zhixin.event;

/**
 * Created by lhj on 2018/8/7.
 * Description:
 */

public class AddWifiEvent {

    private String wifiName;
    private String wifiDis;

    public AddWifiEvent(String wifiName, String wifiDis) {
        this.wifiName = wifiName;
        this.wifiDis = wifiDis;
    }

    public String getWifiName() {
        return wifiName;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }

    public String getWifiDis() {
        return wifiDis;
    }

    public void setWifiDis(String wifiDis) {
        this.wifiDis = wifiDis;
    }

}
