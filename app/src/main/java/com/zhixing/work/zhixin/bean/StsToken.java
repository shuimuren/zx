package com.zhixing.work.zhixin.bean;

/**
 * Created by Administrator on 2018/5/19.
 */

public class StsToken {
    /**
     * AccessKeyId : STS.JQbwK4ortHPR6Qka65VCnDhcc
     * AccessKeySecret : 893ruhQYy7TuJeBxb4BuLF5XT8dqvWQnb4gfoczaxczZ
     * SecurityToken : CAISiwJ1q6Ft5B2yfSjIq5LXPPGAgq1V/5K5NHfahTZgWsxCq63IgTz2IH5MdXRqCOEctvo3mWtZ6vkdlrh+W4NIX0rNaY5t9ZlN9wqkbtIwS1RyNfhW5qe+EE2/VjTZvqaLEcibIfrZfvCyESOm8gZ43br9cxi7QlWhKufnoJV7b9MRLGLaBHg8c7UwHAZ5r9IAPnb8LOukNgWQ4lDdF011oAFx+wgdgOadupTAtEeH3Q2llrdL/d6pfsOeApMybMslYbCcx/drc6fN6ilU5iVR+b1+5K4+ommf74zHUggLu0vebrGFroIwNnxwYqkrBqhDt+Pgkv51vOPekYntwgpKJ/tSVynP930fe1E+vYkagAFzFSJ8p7ssrCA/BDmm+lnaK1ZXe3gkzOe7qj8Rw0OnGWglZ8eYkREyE5ic2uOSiPty88YC7WPYDAIXglb1xlbmtEb/u8ndYA6Y7JWoXRv7Z05avohe9j3/xjSrhKxL9vK1cDVa7mIHqTgxZ8XYjv+dFF0EkMwWtflQ+aEKcRTKgA==
     * Expiration : 2018-04-27 15:23:14
     */
    private String AccessKeyId;
    private String AccessKeySecret;
    private String SecurityToken;
    private String Expiration;

    public String getAccessKeyId() {
        return AccessKeyId;
    }

    public void setAccessKeyId(String AccessKeyId) {
        this.AccessKeyId = AccessKeyId;
    }

    public String getAccessKeySecret() {
        return AccessKeySecret;
    }

    public void setAccessKeySecret(String AccessKeySecret) {
        this.AccessKeySecret = AccessKeySecret;
    }

    public String getSecurityToken() {
        return SecurityToken;
    }

    public void setSecurityToken(String SecurityToken) {
        this.SecurityToken = SecurityToken;
    }

    public String getExpiration() {
        return Expiration;
    }

    public void setExpiration(String Expiration) {
        this.Expiration = Expiration;
    }
}
