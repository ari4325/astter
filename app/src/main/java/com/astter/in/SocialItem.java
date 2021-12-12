package com.astter.in;

public class SocialItem {
    private String name, owner, img_url;

    SocialItem(){}

    SocialItem(String name, String owner, String img_url){
        this.name = name;
        this.owner = owner;
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getOwner() {
        return owner;
    }
}
