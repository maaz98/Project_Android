package com.example.shado.buylist;


import java.util.ArrayList;
import java.util.HashMap;

public class List {
    String name;
    int totalItems;
    int checkedItems;
    long id;
    HashMap<String,Item> itemsList;
    int notificationId;

    public List(String name, int totalItems, int checkedItems, long id, HashMap<String,Item> itemsList) {
        this.name = name;
        this.totalItems = totalItems;
        this.checkedItems = checkedItems;
        this.id = id;
        this.itemsList = itemsList;
    }

    public List (String name, long id){
        this.name = name;
        this.totalItems = 0;
        this.checkedItems = 0;
        this.id = id;
        this.itemsList = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalItems() {
        return itemsList.size();
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getCheckedItems() {
        int n=0;
        ArrayList<Item> list = new ArrayList<Item>(itemsList.values());
        for( int i=0;i< list.size();i++){
            if(list.get(i).isChecked){
                n++;
            }
        }
        return n;
    }

    public void setCheckedItems(int checkedItems) {
        this.checkedItems = checkedItems;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public HashMap<String,Item> getItemsList() {
        return itemsList;
    }

    public void setItemsList(HashMap<String,Item> itemsList) {
        this.itemsList = itemsList;
    }

    @Override
    public String toString() {
        return "Name='" + name + '\'' +
                ", TotalItems=" + totalItems +
                ", CheckedItems=" + checkedItems +
                ", ItemsList=" + itemsList.toString();
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }
}
