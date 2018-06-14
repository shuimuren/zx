package com.zhixing.work.zhixin.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/8.
 */

public class Intro {

    /**
     * Intro : 简介内容
     * Images : ["image1","image2"]
     */

    private String Intro;
    private List<String> Images;

    public String getIntro() {
        return Intro;
    }

    public void setIntro(String Intro) {
        this.Intro = Intro;
    }

    public List<String> getImages() {
        return Images;
    }

    public void setImages(List<String> Images) {
        this.Images = Images;
    }
}
