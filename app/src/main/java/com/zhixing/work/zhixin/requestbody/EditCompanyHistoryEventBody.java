package com.zhixing.work.zhixin.requestbody;

/**
 * Created by lhj on 2018/7/26.
 * Description:
 */

public class EditCompanyHistoryEventBody {
    private String id;
    private String Name;
    private String Date;
    private String Intro;
    private String Image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getIntro() {
        return Intro;
    }

    public void setIntro(String intro) {
        Intro = intro;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

}
