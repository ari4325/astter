package com.astter.in;

public class Constants {
    private static final String base_url = "https://deep-index.moralis.io/api/v2/";
    private static final String base = "http://192.168.1.16:8080/user";

    public static String getNFTs(){
        return base + "/nfts";
    }

    public static String createUser(){
        return base + "/set";
    }

    public static String getUser(String account_address){
        return base + "/get?address="+account_address;
    }

    public static String startFollowing(String username){
        return base + "/follow?following="+username+"&follower="+Account.username;
    }
}
