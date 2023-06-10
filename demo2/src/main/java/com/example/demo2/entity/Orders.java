package com.example.demo2.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("orders")
public class Orders {
    @TableField(value = "id")
    private int id;
    @TableField(value = "name")
    private String name;
    @TableField(value = "phone")
    private String phone;
    @TableField(value = "totalPrice")
    private float totalPrice;
    @TableField(value = "goods_id")
    private String goodsId;
    @TableField(value = "goodsDetail")
    private String goodsDetail;
    @TableField(value = "status")
    private int status;
    @TableField(value = "paytype")
    private int paytype;
    @TableField(value = "buyTime")
    private String buyTime;
    @TableField(value = "address")
    private String address;
    @TableField(value = "user_id")
    private int userId;
    @TableField(exist = false)
//    private List<DeliveryInfo> deliveryInfoList; // 添加deliveryInfoList属性
    private String deliveryInfo; // 添加deliveryInfoList属性

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPaytype() {
        return paytype;
    }

    public void setPaytype(int paytype) {
        this.paytype = paytype;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUser_id() {
        return userId;
    }

    public void setUser_id(int user_id) {
        this.userId = user_id;
    }

    public String getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(String deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }
}
