package com.astter.in;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import java.util.List;

import jnr.ffi.annotations.In;

public class SocialAdapter extends RecyclerView.Adapter<SocialAdapter.Holder>{
    Context mContext;
    List<SocialItem> items;

    SocialAdapter(Context mContext, List<SocialItem> items){
        this.mContext = mContext;
        this.items = items;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SocialAdapter.Holder(LayoutInflater.from(mContext).inflate(R.layout.item_social, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        SocialItem i = items.get(position);
        holder.setItem(i);

        holder.social_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext, "HELLO", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, ProfileActivity.class);
                intent.putExtra("address", i.getOwner_address());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView social_name, social_owner;
        ImageView social_img, follow;
        public Holder(@NonNull View itemView) {
            super(itemView);
            social_img = itemView.findViewById(R.id.social_img);
            social_name = itemView.findViewById(R.id.social_name);
            social_owner = itemView.findViewById(R.id.social_owner);
        }

        public void setItem(SocialItem item){
            social_owner.setText(item.getOwner());
            social_name.setText(item.getName());
            Picasso.with(mContext).load(item.getImg_url()).into(social_img);
        }
    }
}
