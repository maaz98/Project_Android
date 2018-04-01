package com.example.shado.buylist;

import android.support.annotation.NonNull;

import static com.example.shado.buylist.ListDetail.mode;

public class Item implements Comparable {
    String name;
    int quantity;
    int id;
    String listId;
    boolean isChecked;
    double price;
    int typeId;

    public Item(String name, int quantity, int id, String listId, boolean isChecked, double price, int typeId) {
        this.name = name;
        this.quantity = quantity;
        this.id = id;
        this.listId = listId;
        this.isChecked = isChecked;
        this.price = price;
        this.typeId = typeId;
    }

    public Item(String name, int id) {
        this.name = name;
        this.quantity = 0;
        this.id = id;
        this.listId = "dummy";
        this.isChecked = false;
        this.price = 0;
        this.typeId = 0;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", id=" + id +
                ", listId='" + listId + '\'' +
                ", isChecked=" + isChecked +
                ", price=" + price +
                ", typeId=" + typeId +
                '}';
    }

    @Override
    public int compareTo(@NonNull Object o) {
      if(mode == 0)  return this.name.compareTo(((Item)o).name);
      else return this.getId()-((Item)o).getId();
    }
}
