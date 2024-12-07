package com.example.shop;

public class Item {

    public double price;
    public String type;
    public String name;
    public String link;


    public  Item(double price,String type,String name ,String link){
        this.price = price;
        this.type = type;
        this.name = name;
        this.link = link;
    }

    public Item() {
        this.price = 0.0;
        this.type = "";
        this.name = "";
        this.link = "";

    }

    public double getPrice() {
        return price;
    }
    @Override
    public String toString() {
        return "Item: " +
                "Price='" + price + '\''+
                ", Type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", Link='" + link + '\''
                ;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
