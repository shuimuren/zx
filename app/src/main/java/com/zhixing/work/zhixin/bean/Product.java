package com.zhixing.work.zhixin.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/6/7.
 */

public class Product  implements Serializable {

    /**
     * Id : 2
     * CompanyId : 1
     * Name : 产品1
     * Logo : null
     * Url : http://www.baidu.com
     * Intro : 简介
     */

    private int Id;
    private int CompanyId;
    private String Name;
    private String Logo;
    private String Url;
    private String Intro;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(int CompanyId) {
        this.CompanyId = CompanyId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String Logo) {
        this.Logo = Logo;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }

    public String getIntro() {
        return Intro;
    }

    public void setIntro(String Intro) {
        this.Intro = Intro;
    }
}
