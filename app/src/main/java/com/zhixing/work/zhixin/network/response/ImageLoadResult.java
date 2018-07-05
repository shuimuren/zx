package com.zhixing.work.zhixin.network.response;

/**
 * Created by lhj on 2018/7/5.
 * Description: 从阿里云加载图片
 */

public class ImageLoadResult {
    private String code;//识别码

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url; //图片URL

    public ImageLoadResult(String code, String url) {
        this.code = code;
        this.url = url;
    }






}
