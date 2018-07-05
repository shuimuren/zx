package com.zhixing.work.zhixin.network.response;

/**
 * Created by lhj on 2018/7/5.
 * Description: 用于图片上传结果
 */

public class ImageUploadResult {


    public int code ;//识别码
    public boolean success; //是否上传成功

    public ImageUploadResult(int code, boolean success) {
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




}
