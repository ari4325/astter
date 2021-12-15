package com.astter.in;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    ConstraintLayout coinbase_login, address_login;
    TextView enter_address;
    String account_address;
    Button continueBtn;

    private static final String CLIENT_ID = "9bbdae1f28a4846aab57e377753e6ab647ce07a5471e79cca37eb4245b016d84";
    private static final String CLIENT_SECRET = "a68ab9deb28127954dbb24ea661e7e2da45390bbb1e778c9cb00c5148a4dd862";
    private static final String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        init();

        coinbase_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri coinbase_oauth = Uri.parse("https://www.coinbase.com/oauth/authorize?response_type=code&client_id="+CLIENT_ID+"" +
                        "&redirect_uri="+REDIRECT_URI+"&state=SECURE_RANDOM&scope=wallet:accounts:read");
                Intent browser = new Intent(Intent.ACTION_VIEW, coinbase_oauth);
                startActivity(browser);
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account.setContext(getApplicationContext());
                account_address = enter_address.getText().toString();

                StringRequest request = new StringRequest(Request.Method.GET, Constants.getUser(account_address), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseObject = new JSONObject(response);
                            //Toast.makeText(getApplicationContext(), responseObject.getBoolean("error")+"", Toast.LENGTH_SHORT).show();
                            if(!responseObject.getBoolean("error")){
                                List<SocialItem> items = new ArrayList<>();

                                JSONObject userObject = responseObject.getJSONObject("user");
                                JSONArray jsonArray = userObject.getJSONArray("items");

                                for(int i = 0; i<jsonArray.length(); i++){
                                    JSONObject nft = jsonArray.getJSONObject(i);
                                    SocialItem item = new SocialItem(nft.getString("owner_address"), nft.getString("name"), nft.getString("owner"), nft.getString("img_url"));
                                    items.add(item);
                                }
                                Account.createUser(userObject.getString("username"), userObject.getString("address"), "", userObject.getString("_id"));
                                Intent intent = new Intent(LoginActivity.this, SocialActivity.class);
                                startActivity(intent);
                                overridePendingTransition(0, 0);
                                finish();
                            }else {
                                Intent intent = new Intent(LoginActivity.this, SetUsernameActivity.class);
                                intent.putExtra("account_address", account_address);
                                startActivity(intent);
                                overridePendingTransition(0, 0);
                                finish();
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
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        map.put("address", account_address);
                        return map;
                    }
                };

                RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(request);
                //Toast.makeText(getApplicationContext(), "HELLO", Toast.LENGTH_SHORT).show();


            }
        });
    }

    void init(){
        coinbase_login = findViewById(R.id.coinbase_login);
        address_login = findViewById(R.id.address_login);
        enter_address = findViewById(R.id.enter_address);
        continueBtn = findViewById(R.id.continueBtn);

        Intent get = getIntent();
        if(get.getData() != null) {
            String auth = get.getData().getEncodedPath();
            Toast.makeText(getApplicationContext(), auth, Toast.LENGTH_SHORT).show();
        }
    }
}