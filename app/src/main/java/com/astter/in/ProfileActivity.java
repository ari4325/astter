package com.astter.in;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.web3j.abi.datatypes.Int;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    ImageView backbtn;
    TextView username, nft_num, following_num;
    RecyclerView nft_recycler;
    ProfileAdapter profileAdapter;
    List<SocialItem> socialItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        socialItems = new ArrayList<>();

        backbtn = findViewById(R.id.backBtn);
        username = findViewById(R.id.username);
        nft_recycler = findViewById(R.id.nft_recycler);
        nft_num = findViewById(R.id.nft_num);
        following_num = findViewById(R.id.following_num);

        profileAdapter = new ProfileAdapter(getApplicationContext(), socialItems);

        nft_recycler.setAdapter(profileAdapter);
        nft_recycler.setLayoutManager(new GridLayoutManager(this, 2));

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, SocialActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        StringRequest request = new StringRequest(Request.Method.GET, Constants.getUser(Account.address), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responseObject = new JSONObject(response);
                    //Toast.makeText(getApplicationContext(), responseObject.getBoolean("error")+"", Toast.LENGTH_SHORT).show();
                    List<SocialItem> items = new ArrayList<>();

                    JSONObject userObject = responseObject.getJSONObject("user");
                    JSONArray jsonArray = userObject.getJSONArray("items");
                    JSONArray following = userObject.getJSONArray("followings");

                    nft_num.setText(jsonArray.length()+"");
                    following_num.setText(following.length()+"");

                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject nft = jsonArray.getJSONObject(i);
                        SocialItem item = new SocialItem(nft.getString("name"), nft.getString("owner"), nft.getString("img_url"));
                        items.add(item);
                    }

                    socialItems.addAll(items);
                    profileAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(request);

        username.setText(Account.username);
    }
}