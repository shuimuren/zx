package com.zhixing.work.zhixin.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 项目名称:HardTask
 * Created by lovezh
 * CreatedData: on 2018/4/18.
 */

//                                               @PATCH("Role")String Role,
//                                               @PATCH("NewPassword")String NewPassword ,
//                                               @PATCH("SmsCode")String SmsCode
public class UpdateBean {
    @SerializedName("PhoneNum")
    public String PhoneNum;

    @SerializedName("Role")
    public String Role;

    @SerializedName("NewPassword")
    public String NewPassword;

    @Override
    public String toString() {
        return "UpdateBean{" +
                "PhoneNum='" + PhoneNum + '\'' +
                ", Role='" + Role + '\'' +
                ", NewPassword='" + NewPassword + '\'' +
                ", SmsCode='" + SmsCode + '\'' +
                '}';
    }

    @SerializedName("SmsCode")
    public String SmsCode;


}
