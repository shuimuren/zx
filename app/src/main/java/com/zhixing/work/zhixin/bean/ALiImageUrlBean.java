package com.zhixing.work.zhixin.bean;

/**
 * Created by lhj on 2018/7/24.
 * Description:
 */

public class ALiImageUrlBean  {

    private String url;
    private String baseUrl; //图片存储在服务器的地址

    public ALiImageUrlBean(String url, String baseUrl) {
        this.url = url;
        this.baseUrl = baseUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

}
