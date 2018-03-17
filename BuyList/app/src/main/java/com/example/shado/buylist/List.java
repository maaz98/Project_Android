package com.example.shado.buylist;


import java.util.ArrayList;

public class List {
    String name;
    int totalItems;
    int checkedItems;
    long id;
    ArrayList<Item> itemsList;

    public List(String name, int totalItems, int checkedItems, long id, ArrayList<Item> itemsList) {
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
        this.itemsList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getCheckedItems() {
        int n=0;
        for( int i=0;i< itemsList.size();i++){
            if(itemsList.get(i).isChecked){
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

    public ArrayList<Item> getItemsList() {
        return itemsList;
    }

    public void setItemsList(ArrayList<Item> itemsList) {
        this.itemsList = itemsList;
    }

    @Override
    public String toString() {
        return "List{" +
                "name='" + name + '\'' +
                ", totalItems=" + totalItems +
                ", checkedItems=" + checkedItems +
                ", id=" + id +
                ", itemsList=" + itemsList.toString() +
                '}';
    }
}
