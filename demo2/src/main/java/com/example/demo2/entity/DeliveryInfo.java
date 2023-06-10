package com.example.demo2.entity;



public class DeliveryInfo {

    private String cell;
    private String receiver;
    private String address;

    private String data;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getData() {
        return this.receiver+ " " +this.address + " " + this.cell;

    }
}
