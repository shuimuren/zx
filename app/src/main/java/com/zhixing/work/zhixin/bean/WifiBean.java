package com.zhixing.work.zhixin.bean;

import java.io.Serializable;

/**
 * Created by lhj on 2018/8/7.
 * Description:
 */

public class WifiBean implements Serializable{




    private String Name;
    private String Bssid;
    private boolean isSelected;

    public WifiBean(String name, String bssid, boolean isSelected) {
        Name = name;
        Bssid = bssid;
        this.isSelected = isSelected;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBssId() {
        return Bssid;
    }

    public void setBssId(String bssid) {
        Bssid = bssid;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
//    public WifiBean(String name, String bssId, boolean isSelected) {
//        this.name = name;
//        this.bssId = bssId;
//        this.isSelected = isSelected;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getBssId() {
//        return bssId;
//    }
//
//    public void setBssId(String bssId) {
//        this.bssId = bssId;
//    }
//
//    public boolean isSelected() {
//        return isSelected;
//    }
//
//    public void setSelected(boolean selected) {
//        isSelected = selected;
//    }

}
