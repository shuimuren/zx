package com.zhixing.work.zhixin.network.response;

/**
 * Created by lhj on 2018/7/5.
 * Description: 用于图片上传结果
 */

public class ImageUploadResult {
    private int code;//识别码
    private boolean success; //是否上传成功
    private String url; //上传成功后返回地址

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public ImageUploadResult(int code, String url, boolean success) {
        this.url = url;
        this.code = code;
        this.success = success;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


    @Override
    public String toString() {
        return "ImageUploadResult{" +
                "code=" + code +
                ", success=" + success +
                ", url='" + url + '\'' +
                '}';
    }


}
