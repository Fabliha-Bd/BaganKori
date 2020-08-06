package com.Fabliha.BaganKori.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Fabliha.BaganKori.R;
import com.Fabliha.BaganKori.retrofit.ResponseItem;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class HomeImageAdapter extends  RecyclerView.Adapter<HomeImageAdapter.HomeViewHolder>{

    private Context mContext;
    private List<ResponseItem> mUploads;


    public HomeImageAdapter(Context mContext, List<ResponseItem> mUploads) {
        this.mContext = mContext;
        this.mUploads = mUploads;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.home_item, parent, false);
        return new HomeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        final ResponseItem uploadCurrent = mUploads.get(position);
        Log.d("Home","ImageUrl "+uploadCurrent.getImageUrl());
        Log.d("Home","Name "+uploadCurrent.getName());
        Log.d("Home","Price "+uploadCurrent.getMPrice());
        holder.tvName.setText(uploadCurrent.getName());
        holder.tvPrice.setText("BDT "+uploadCurrent.getMPrice());

        Glide.with(mContext)
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                //.fitCenter()
                //.centerCrop()
                .into(holder.roundedImageView);
        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Home","Button Click "+position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    class HomeViewHolder extends RecyclerView.ViewHolder{
        ImageButton btn_add;
        RoundedImageView roundedImageView;
        TextView tvName;
        TextView tvPrice;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            roundedImageView= itemView.findViewById(R.id.homecard);
            btn_add= itemView.findViewById(R.id.btn_add);
            tvName=itemView.findViewById(R.id.tvName);
            tvPrice= itemView.findViewById(R.id.tvPrice);
        }
    }
}
