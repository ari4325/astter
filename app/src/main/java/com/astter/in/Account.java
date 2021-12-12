package com.astter.in;

import android.content.Context;
import android.content.SharedPreferences;

public class Account {
    static String username;
    static String address;
    static boolean isLoggedIn;
    static String img_path;
    static Context mContext;
    static SharedPreferences sharedPreferences;

    static void setContext(Context mContext){
        Account.mContext = mContext;
        sharedPreferences = mContext.getSharedPreferences("user_login", Context.MODE_PRIVATE);
        load();
    }

    static void load(){
        username = sharedPreferences.getString("username", "");
        address = sharedPreferences.getString("address", "");
        isLoggedIn = sharedPreferences.getBoolean("login", false);
        img_path = sharedPreferences.getString("img_path", "");
    }

    static void createUser(String name, String address, String img_path){
        Account.username = name;
        Account.address = address;
        Account.img_path = img_path;
        Account.isLoggedIn = true;
        save();
    }

    static void save(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("login", isLoggedIn);
        editor.putString("username", Account.username);
        editor.putString("address", Account.address);
        editor.putString("img_path", Account.img_path);
        editor.apply();
    }
}
