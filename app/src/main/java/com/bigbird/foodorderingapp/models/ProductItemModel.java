package com.bigbird.foodorderingapp.models;

import java.io.Serializable;

public class ProductItemModel  implements Serializable {

    String id;
    String name;
    Double price;
    String image;
    int count;
    String ownerId;

    public ProductItemModel(String image,String id, String name, Double price, int count,String ownerId) {
        this.id = id;
        this.image=image;
        this.ownerId=ownerId;
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public ProductItemModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ProductItemModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", ownerId=" + ownerId +
                ", image=" + image +

                '}';
    }
}
