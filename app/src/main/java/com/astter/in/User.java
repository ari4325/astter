package com.astter.in;

import java.util.List;

public class User {
    String username, address, user_id;
    List<SocialItem> items;

    User(){}

    User(String username, String address, String user_id, List<SocialItem> items){
        this.username = username;
        this.address = address;
        this.items = items;
        this.user_id = user_id;
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
