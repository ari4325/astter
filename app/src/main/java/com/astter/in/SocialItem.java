package com.astter.in;

public class SocialItem {
    private String owner_address, name, owner, img_url;

    SocialItem(){}

    SocialItem(String owner_address, String name, String owner, String img_url){
        this.owner_address = owner_address;
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

    public String getOwner_address() {
        return owner_address;
    }
}
