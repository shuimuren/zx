package com.zhixing.work.zhixin.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 *
 * CreatedData: on 2018/4/8.
 */

public class UserTokenEntity implements Parcelable {

    /**
     * code : 200
     * userId : jlk456j5
     * token : sfd9823ihufi
     */

    private int code;
    private String userId;
    private String token;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeString(this.userId);
        dest.writeString(this.token);
    }

    public UserTokenEntity() {
    }

    protected UserTokenEntity(Parcel in) {
        this.code = in.readInt();
        this.userId = in.readString();
        this.token = in.readString();
    }

    public static final Parcelable.Creator<UserTokenEntity> CREATOR = new Parcelable.Creator<UserTokenEntity>() {
        @Override
        public UserTokenEntity createFromParcel(Parcel source) {
            return new UserTokenEntity(source);
        }

        @Override
        public UserTokenEntity[] newArray(int size) {
            return new UserTokenEntity[size];
        }
    };
}
