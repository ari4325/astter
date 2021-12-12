package com.astter.in;

import java.util.List;

public class User {
    String username, address;
    List<SocialItem> items;

    User(){}

    User(String username, String address, List<SocialItem> items){
        this.username = username;
        this.address = address;
        this.items = items;
    }

    public List<SocialItem> getItems() {
        return items;
    }

    public String getAddress() {
        return address;
    }

    public String getUsername() {
        return username;
    }
}
