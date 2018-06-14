package com.zhixing.work.zhixin.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/6/7.
 */

public class Manager  implements Serializable{

    /**
     * Id : 1
     * CompanyId : 1
     * Name : wu
     * JotTitle : UFO
     * Intro : 就问你屌不屌
     * Avatar : null
     */

    private int Id;
    private int CompanyId;
    private String Name;
    private String JotTitle;
    private String Intro;
    private String Avatar;

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

    public String getJotTitle() {
        return JotTitle;
    }

    public void setJotTitle(String JotTitle) {
        this.JotTitle = JotTitle;
    }

    public String getIntro() {
        return Intro;
    }

    public void setIntro(String Intro) {
        this.Intro = Intro;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
    }
}
