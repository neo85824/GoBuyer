package com.ncu.neo.GoBuyer;

import java.io.Serializable;

/**
 * Created by Neo on 2017/1/3.
 */
public class Post implements Serializable {
    private static final long serialVersionUID = -7060210544600464481L;
    private String name ;
    private String price;
    private String number;  //商品數量
    private String country;
    private String catalog;
    private String weight;
    private String time;
    private String imageURL;
    private String key;  //post key
    private String user; //使用者帳戶
    private String description;  // 商品說明
    private String link;  //商品連結
    private String strName;  // 商店名稱
    private String receipt; //單據
    private String getProduct; //取貨方式
    private String date;
    private Boolean isOrder;

    public Post() {
    }

    public Post(String name, String price, String number, String country, String catalog, String weight, String time, String imageURL, String user) {
        this.name = name;
        this.price = price;
        this.number = number;
        this.country = country;
        this.catalog = catalog;
        this.weight = weight;
        this.time = time;
        this.imageURL = imageURL;
        this.user = user;
        this.isOrder = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price  = price;
    }

    public String getCountry() {return country;}

    public void setCountry(String country) {this.country = country;}

    public String getCatalog() {return catalog;}

    public void setCatalog(String catalog) {this.catalog = catalog;}

    public String getTime() {return time;}

    public void setTime(String time) {this.time = time;}

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Boolean getIsOrder() {
        return isOrder;
    }

    public void setIsOrder(Boolean isOrder) {
        this.isOrder = isOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getGetProduct() {
        return getProduct;
    }

    public void setGetProduct(String getProduct) {
        this.getProduct = getProduct;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
