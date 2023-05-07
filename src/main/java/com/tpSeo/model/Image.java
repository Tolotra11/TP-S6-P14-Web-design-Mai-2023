package com.tpSeo.model;

import java.sql.Timestamp;

import com.tpSeo.DAO.ObjectBDD;

public class Image extends ObjectBDD{
    private String image;
    private Integer articleId;
    private Timestamp dateImage;
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public Integer getArticleId() {
        return articleId;
    }
    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }
    public Timestamp getDateImage() {
        return dateImage;
    }
    public void setDateImage(Timestamp dateImage) {
        this.dateImage = dateImage;
    }
    public void init(){
        this.setNomDeTable("image");
    }
    public Image() {
        this.init();
    }
    
}
