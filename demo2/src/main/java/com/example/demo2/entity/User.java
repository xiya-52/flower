package com.example.demo2.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("user")
public class User {

    @TableField(value = "id")
    private int id;

    @TableField(value = "logstatu")
    private int logstatu;

    public int getLogstatu() {
        return logstatu;
    }

    public void setLogstatu(int logstatu) {
        this.logstatu = logstatu;
    }


    @TableField(value = "phone")
    private String phone;

    @TableField(value = "name")
    private String name;

    @TableField(value = "password")
    private String password;

    @TableField(value = "cell")
    private String cell;

    @TableField(value = "receiver")
    private String receiver;

    @TableField(value = "addr")
    private String addr;

    @TableField(value = "isadmin")
    private int isAdmin;




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

}
