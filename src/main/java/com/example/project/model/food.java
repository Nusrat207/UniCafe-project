package com.example.project.model;

public class food {
    private String name;
    private String imgSrc;
    private int price;
    private String color;

    private String mealType, time;
    private int servingSize;

    private String shop;

    public food(){

    }

    public food(String n, int q, int p, String s, String m, String t){
        name=n;
        servingSize=q;
        price=p;
        shop=s;
        mealType=m;
        time=t;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServingSize() {
        return servingSize;
    }

    public void setServingSize(int s) {
        this.servingSize = s;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }



    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public void setTime(String t) {this.time=t; }

    public String getTime(){
        return time;
    }

}
