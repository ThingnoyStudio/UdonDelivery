package com.thingnoy.thingnoy500v3.dao;

/**
 * Created by HBO on 1/3/2561.
 */

public class ResReviewBody {
    private int id_restaurant;
    private int id_customer;
    private String res_comment;
    private double res_score;
    private String date;
    private String imgname;
    private String img;

    public ResReviewBody() {
    }

    public int getId_restaurant() {
        return id_restaurant;
    }

    public void setId_restaurant(int id_restaurant) {
        this.id_restaurant = id_restaurant;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public String getRes_comment() {
        return res_comment;
    }

    public void setRes_comment(String res_comment) {
        this.res_comment = res_comment;
    }

    public double getRes_score() {
        return res_score;
    }

    public void setRes_score(double res_score) {
        this.res_score = res_score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImgname() {
        return imgname;
    }

    public void setImgname(String imgname) {
        this.imgname = imgname;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
