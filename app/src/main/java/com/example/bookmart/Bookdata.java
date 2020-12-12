package com.example.bookmart;

public class Bookdata {
    public String title;
    public String description;
    public byte[] image;
    public int price;
    public long contact;
    public String location;
    public String user;

    public Bookdata(String title, String description, byte[] image, int price, long contact, String location,String user) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.price = price;
        this.contact = contact;
        this.location = location;
        this.user=user;
    }

    public Bookdata() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public long getContact() {
        return contact;
    }

    public void setContact(long contact) {
        this.contact = contact;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public void setUser(String user){this.user=user;}
    public String getUser(){ return user;}
}
