package com.astter.in;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mongodb.util.JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SocialActivity extends AppCompatActivity {
    RecyclerView socialRecycler;
    SocialAdapter socialAdapter;
    List<SocialItem> socialItems;
    CircleImageView profileImageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);

        init();

        StringRequest request = new StringRequest(Request.Method.GET, Constants.getNFTs(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responseObject = new JSONObject(response);
                    JSONArray nfts = responseObject.getJSONArray("nfts");
                    for(int i = 0; i<nfts.length(); i++){
                        JSONObject nft = nfts.getJSONObject(i);
                        SocialItem item = new SocialItem(nft.getString("name"), nft.getString("owner"), nft.getString("img_url"));
                        //Toast.makeText(getApplicationContext(), nft.getString("name"), Toast.LENGTH_SHORT).show();
                        socialItems.add(item);
                        socialAdapter.notifyDataSetChanged();
                    }
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

        profileImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocialActivity.this, ProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }

    void init(){
        socialItems = new ArrayList<>();
        socialRecycler = findViewById(R.id.social_recycler);
        socialAdapter = new SocialAdapter(getApplicationContext(), socialItems);
        profileImageBtn = findViewById(R.id.profileBtn);

        socialRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        socialRecycler.setAdapter(socialAdapter);
    }
}