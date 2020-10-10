
package com.burnmycalories.model;

import java.util.List;


public class Media {
    private int id;
    private String name;
    //封面url
    private String cover;
    private String introduction;
    ////作品别名
    //private List<String> aliases;


    //**非数据库内容，业务层展示热度使用******************
    private int hotPoint;
    //********************

    public int getHotPoint() {
        return hotPoint;
    }

    public void setHotPoint(int hotPoint) {
        this.hotPoint = hotPoint;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
