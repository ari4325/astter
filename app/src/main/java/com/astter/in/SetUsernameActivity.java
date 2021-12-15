package com.astter.in;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SetUsernameActivity extends AppCompatActivity {
    TextView username;
    Button continueBtn;
    Intent prevIntent;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_username);

        init();

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account.setContext(getApplicationContext());

                try{
                    //DatabaseHandler.getInstance(getApplicationContext()).createUser(Account.username, Account.address);

                    StringRequest request = new StringRequest(Request.Method.POST, Constants.createUser(address, username.getText().toString(), ""), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject responseObject = new JSONObject(response);
                                JSONObject userObject = responseObject.getJSONObject("user");

                                Account.createUser(userObject.getString("username"), userObject.getString("address"), userObject.getString("img"), userObject.getString("_id"));

                                Toast.makeText(getApplicationContext(), responseObject.getString("response"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SetUsernameActivity.this, SocialActivity.class));
                                overridePendingTransition(0, 0);
                                finish();
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
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();
                            map.put("username", Account.username);
                            map.put("address", Account.address);
                            return map;
                        }
                    };

                    RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(request);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    void init(){
        prevIntent = getIntent();
        address = prevIntent.getStringExtra("account_address");

        username = findViewById(R.id.username);
        continueBtn = findViewById(R.id.continueBtn);
    }
}