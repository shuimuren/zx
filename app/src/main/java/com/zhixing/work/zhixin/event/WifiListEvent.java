package com.zhixing.work.zhixin.event;

import com.zhixing.work.zhixin.bean.WifiBean;

import java.util.List;

/**
 * Created by lhj on 2018/8/7.
 * Description:
 */

public class WifiListEvent {

    public List<WifiBean> getWifis() {
        return wifis;
    }

    public void setWifis(List<WifiBean> wifis) {
        this.wifis = wifis;
    }

    private List<WifiBean> wifis;
}
