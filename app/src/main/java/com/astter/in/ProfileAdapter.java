package com.astter.in;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.Holder>{
    Context mContext;
    List<SocialItem> items;

    ProfileAdapter(Context mContext, List<SocialItem> items){
        this.mContext = mContext;
        this.items = items;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProfileAdapter.Holder(LayoutInflater.from(mContext).inflate(R.layout.item_profile, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        SocialItem i = items.get(position);
        holder.setItem(i);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView social_img;
        public Holder(@NonNull View itemView) {
            super(itemView);
            social_img = itemView.findViewById(R.id.nft_img);
        }

        public void setItem(SocialItem item){
            Picasso.with(mContext).load(item.getImg_url()).fit().into(social_img);
        }
    }
}
