package com.example.shado.buylist;

import java.io.Serializable;

public class Attachment implements Serializable{
    int type;
    String address;

    public Attachment(int type, String address) {
        this.type = type;
        this.address = address;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
