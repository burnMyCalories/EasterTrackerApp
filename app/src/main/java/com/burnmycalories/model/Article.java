package com.burnmycalories.model;

import java.util.Date;

public class Article {

    private int id;
    private String content;
    private String title;
    private String imageUrl;
    private int userId;
    private Date publishTime;

//    private int recommendPoint;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

//    public int getRecommendPoint() {
//        return recommendPoint;
//    }
//
//    public void setRecommendPoint(int recommendPoint) {
//        this.recommendPoint = recommendPoint;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }



}
