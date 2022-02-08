package com.bigbird.foodorderingapp.activities.kitchen_owner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bigbird.foodorderingapp.R;
import com.bigbird.foodorderingapp.models.ProductItemModel;
import com.bigbird.foodorderingapp.utils.IOnItemClickListener;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class KitchenAdapter extends RecyclerView.Adapter<KitchenAdapter.MyViewHolder> {

    ArrayList<ProductItemModel> productItemModelArrayList;
    Context myContext;
    IOnItemClickListener iOnItemClickListener;

    public void setiOnItemClickListener(IOnItemClickListener iOnItemClickListener) {
        this.iOnItemClickListener = iOnItemClickListener;
    }

    public KitchenAdapter(ArrayList<ProductItemModel> productItemModelArrayList, Context myContext) {
        this.productItemModelArrayList = productItemModelArrayList;
        this.myContext = myContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dish, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ProductItemModel itemModel = productItemModelArrayList.get(position);
        holder.tvName.setText(itemModel.getName());
        holder.tvPrice.setText("Price: " + itemModel.getPrice().toString());
        Glide.with(myContext).load(itemModel.getImage()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iOnItemClickListener.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productItemModelArrayList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
       CircleImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.nameTextView);
            tvPrice = itemView.findViewById(R.id.priceTextView);
            imageView=itemView.findViewById(R.id.profile_image);

        }
    }
}
