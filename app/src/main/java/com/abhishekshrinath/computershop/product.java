package com.abhishekshrinath.computershop;

public class product

{
    private String pid;
    private String l_name;
    private String l_rating;
    private String l_price;
    private String l_info;
    private String l_imgurl;

    public product() {
    }

    public product(String l_name, String l_rating, String l_price, String l_info, String l_imgurl) {
        this.l_name = l_name;
        this.l_rating = l_rating;
        this.l_price = l_price;
        this.l_info = l_info;
        this.l_imgurl = l_imgurl;
        this.pid = pid;

    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getL_name() {
        return l_name;
    }

    public String getL_rating() {
        return l_rating;
    }

    public String getL_price() {
        return l_price;
    }

    public String getL_info() {
        return l_info;
    }

    public String getL_imgurl() {
        return l_imgurl;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public void setL_rating(String l_rating) {
        this.l_rating = l_rating;
    }

    public void setL_price(String l_price) {
        this.l_price = l_price;
    }

    public void setL_info(String l_info) {
        this.l_info = l_info;
    }

    public void setL_imgurl(String l_imgurl) {
        this.l_imgurl = l_imgurl;
    }
}
