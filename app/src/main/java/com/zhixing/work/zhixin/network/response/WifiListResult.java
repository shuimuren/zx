package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.network.BaseResult;

import java.util.List;

/**
 * Created by lhj on 2018/8/1.
 * Description:
 */

public class WifiListResult extends BaseResult {

    private List<ContentBean> Content;

    public List<ContentBean> getContent() {
        return Content;
    }

    public void setContent(List<ContentBean> Content) {
        this.Content = Content;
    }

    public static class ContentBean {
        /**
         * Name : hongyan
         * Bssid : f3-g4-g6-cv-d7-8h-s8
         */

        private String Name;
        private String Bssid;

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getBssid() {
            return Bssid;
        }

        public void setBssid(String Bssid) {
            this.Bssid = Bssid;
        }
    }
}
