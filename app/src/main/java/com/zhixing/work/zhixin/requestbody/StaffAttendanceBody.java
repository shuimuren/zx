package com.zhixing.work.zhixin.requestbody;

/**
 * Created by lhj on 2018/8/2.
 * Description: 员工打卡
 */

public class StaffAttendanceBody {
    private String WifiName;
    private String Bssid;
    private String ClientCode;

    public StaffAttendanceBody(String wifiName, String bssid, String clientCode) {
        WifiName = wifiName;
        Bssid = bssid;
        ClientCode = clientCode;
    }

    public String getWifiName() {
        return WifiName;
    }

    public void setWifiName(String wifiName) {
        WifiName = wifiName;
    }

    public String getBssid() {
        return Bssid;
    }

    public void setBssid(String bssid) {
        Bssid = bssid;
    }

    public String getClientCode() {
        return ClientCode;
    }

    public void setClientCode(String clientCode) {
        ClientCode = clientCode;
    }


}
