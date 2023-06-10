package com.example.demo2.entity;

import com.baomidou.mybatisplus.annotation.TableField;

public class Category {

    @TableField(value = "id")
    private int id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "category_id")
    private int category_id;

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
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
}
