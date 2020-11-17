package com.example.volleyimage;

public class Item {

    private String imageUrl;
    private String title;
    private String like;

    public Item(String imageUrl, String title, String like) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.like = like;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getLike() {
        return like;
    }
}
