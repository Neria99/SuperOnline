package com.example.shop;

public class Order {

    public String pay;
    public static String name = "";
    public static String address = "";
    public static String email = "";
    public static String tel = "";


    public Order(String pay, String name, String address , String email, String tel){
        this.pay = pay;
        this.name = name;
        this.address = address;
        this.email = email;
        this.tel = tel;
    }

    public Order() {
        this.pay = "";
        this.name = "";
        this.address = "";
        this.email = "";
        this.tel = "";
    }
    public String getPay() {
        return pay;
    }
    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public String getEmail() {
        return email;
    }
    public String getTel() {
        return tel;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    @Override
    public String toString() {
        return "Item: " +
                "Pay='" + pay + '\''+
                ", Name='" + name + '\'' +
                ", Address='" + address + '\'' +
                ", Email='" + email + '\''+
                ", Tel='" + tel + '\''
                ;
    }



}
