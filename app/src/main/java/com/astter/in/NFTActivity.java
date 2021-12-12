package com.astter.in;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NFTActivity {

    Context context;
    String address;

    NFTActivity(Context context, String address){
        this.context = context;
        this.address = address;
    }

    void fetchNFTs() {
        StringRequest request = new StringRequest(Request.Method.GET, Constants.getNFTs(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responseObject = new JSONObject(response);
                    JSONArray results = responseObject.getJSONArray("result");
                    for(int i = 0; i<results.length(); i++){
                        JSONObject nftObject = results.getJSONObject(i);
                        String nft_url = nftObject.getString("token_uri");
                        fetchImageUrl(nft_url);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("X-API-Key", "UAne0BxcE0SXi9D7v45d2Qxa47BelgdmpkRTjV8bXRjycLNRj5OTXegebmKJVgBn");
                return headers;
            }
        };

        RequestHandler.getInstance(context).addToRequestQueue(request);
    }

    private void fetchImageUrl(String nft_url){
        StringRequest request = new StringRequest(Request.Method.GET, nft_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responseObject = new JSONObject(response);
                    String img_url = responseObject.getString("url");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestHandler.getInstance(context).addToRequestQueue(request);
    }
}