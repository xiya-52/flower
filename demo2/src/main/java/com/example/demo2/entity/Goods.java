package com.example.demo2.entity;


import com.baomidou.mybatisplus.annotation.TableField;

import javax.persistence.Transient;

public class Goods {
    @TableField(value = "id")
    private int id;


    @TableField(value = "name")
    private String name;

    @TableField(value = "image")
    private String image;

    @TableField(value = "category")
    private String category;

    @TableField(value = "price")
    private float price;

    @TableField(value = "category_id")
    private int categoryid;

    @TableField(value = "isJoined")
    private int isJoined;

    @TableField(exist = false)
    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getCategory_id() {
        return categoryid;
    }

    public void setCategory_id(int category_id) {
        this.categoryid = category_id;
    }

    public int getIsJoined() {
        return isJoined;
    }

    public void setIsJoined(int isJoined) {
        this.isJoined = isJoined;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", category_id=" + categoryid +
                ", isJoined=" + isJoined +
                '}';
    }
}
