package com.astter.in;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    ImageView backbtn;
    TextView username, nft_num, following_num, follower_num;
    RecyclerView nft_recycler;
    ProfileAdapter profileAdapter;
    List<SocialItem> socialItems;
    String account_address, user_id;
    TextView followBtn;
    boolean isFollowing = false;
    CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        socialItems = new ArrayList<>();
        Intent intent = getIntent();
        account_address = intent.getStringExtra("address");

        backbtn = findViewById(R.id.backBtn);
        username = findViewById(R.id.username);
        nft_recycler = findViewById(R.id.nft_recycler);
        nft_num = findViewById(R.id.nft_num);
        following_num = findViewById(R.id.following_num);
        follower_num = findViewById(R.id.followerNum);
        followBtn = findViewById(R.id.follow_btn);
        circleImageView = findViewById(R.id.circleImageView);
        circleImageView.setEnabled(false);

        if(account_address.equals(Account.address)){
            followBtn.setVisibility(View.GONE);
            circleImageView.setEnabled(true);
        }

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

        StringRequest request = new StringRequest(Request.Method.GET, Constants.getUser(account_address), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responseObject = new JSONObject(response);
                    //Toast.makeText(getApplicationContext(), responseObject.getBoolean("error")+"", Toast.LENGTH_SHORT).show();
                    List<SocialItem> items = new ArrayList<>();

                    JSONObject userObject = responseObject.getJSONObject("user");
                    user_id = userObject.getString("_id");
                    JSONArray jsonArray = userObject.getJSONArray("items");
                    JSONArray following = userObject.getJSONArray("following");
                    JSONArray follower = userObject.getJSONArray("follower");

                    for(int i = 0; i<follower.length(); i++){
                        String followerN = follower.getString(i);
                        Toast.makeText(getApplicationContext(), followerN, Toast.LENGTH_SHORT).show();
                        if(followerN.equals(Account.user_id)){
                            isFollowing = true;
                        }
                    }

                    if(isFollowing) {
                        followBtn.setText("Following");
                        followBtn.setEnabled(false);
                    }

                    username.setText(userObject.getString("username"));

                    nft_num.setText(jsonArray.length()+"");
                    following_num.setText(following.length()+"");
                    follower_num.setText(follower.length()+"");

                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject nft = jsonArray.getJSONObject(i);
                        SocialItem item = new SocialItem(nft.getString("owner_address"),nft.getString("name"), nft.getString("owner"), nft.getString("img_url"));
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

        followBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest request = new StringRequest(Request.Method.PUT, Constants.startFollowing(user_id), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "User followed successfully", Toast.LENGTH_SHORT).show();
                        followBtn.setEnabled(false);
                        followBtn.setText("Following");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

                RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(request);
            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1001);
            }
        });

        //username.setText(Account.username);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {

            Uri imageUri = Objects.requireNonNull(data).getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            if (bitmap != null) {
                circleImageView.setImageBitmap(bitmap);
                byte[] imageBytes = imageToByteArray(bitmap);
                String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT); // actual conversion to Base64 String Image
                String img64 = encodedImage;
            }

        }
    }

    private byte[] imageToByteArray(Bitmap bitmapImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        return baos.toByteArray();
    }
}