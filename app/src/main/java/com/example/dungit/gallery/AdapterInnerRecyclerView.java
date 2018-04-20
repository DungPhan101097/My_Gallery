package com.example.dungit.gallery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by DUNGIT on 4/18/2018.
 */

class AdapterInnerRecyclerView extends RecyclerView.Adapter<AdapterInnerRecyclerView.InnerViewHolder> {

    private ArrayList<Photo> data;
    private Context context;

    public AdapterInnerRecyclerView(Context context, ArrayList<Photo> data) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterInnerRecyclerView.InnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_img_item,
                parent, false);
        return new InnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerViewHolder holder, int position) {
        Photo curPhoto = data.get(position);

        Glide.with(context).load(curPhoto.getUrl()).into(holder.ivItem);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class InnerViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivItem;

        public InnerViewHolder(View itemView) {
            super(itemView);

            ivItem = itemView.findViewById(R.id.iv_item);
        }
    }
}