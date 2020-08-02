package com.Fabliha.BaganKori.shop.view;

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
import com.Fabliha.BaganKori.shop.model.Upload;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class ProductImageAdapter extends  RecyclerView.Adapter<ProductImageAdapter.ProductViewHolder>{

    private Context mContext;
    private List<Upload> mUploads;


    public ProductImageAdapter(Context mContext, List<Upload> mUploads) {
        this.mContext = mContext;
        this.mUploads = mUploads;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.home_item, parent, false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        final Upload uploadCurrent = mUploads.get(position);

        holder.tvName.setText(uploadCurrent.getName());
        //   holder.tvPrice.setText("BDT "+uploadCurrent.getmPrice());
        holder.tvPrice.setText("BDT "+uploadCurrent.getmPrice());

        Glide.with(mContext)
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                //.fitCenter()
                //.centerCrop()
                .into(holder.roundedImageView);
        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Product","Button Click "+position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{
        ImageButton btn_add;
        RoundedImageView roundedImageView;
        TextView tvName;
        TextView tvPrice;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            roundedImageView= itemView.findViewById(R.id.homecard);
            btn_add= itemView.findViewById(R.id.btn_add);
            tvName=itemView.findViewById(R.id.tvName);
            tvPrice= itemView.findViewById(R.id.tvPrice);
        }
    }
}
