package com.bigbird.foodorderingapp.activities.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bigbird.foodorderingapp.R;
import com.bigbird.foodorderingapp.models.ProductItemModel;

import java.util.ArrayList;

public class UserCartAdapter extends RecyclerView.Adapter<UserCartAdapter.MyViewHolder> {

    ArrayList<ProductItemModel> productItemModelArrayList;
    Context myContext;



    public UserCartAdapter(ArrayList<ProductItemModel> productItemModelArrayList, Context myContext) {
        this.productItemModelArrayList = productItemModelArrayList;
        this.myContext = myContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cart_user, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ProductItemModel itemModel = productItemModelArrayList.get(position);
        holder.tvName.setText(itemModel.getName());
        holder.tvPrice.setText("Price: " + itemModel.getPrice().toString());
        holder.tvCounts.setText(itemModel.getCount() + "");
       holder.tvIncrease.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               
           }
       });
        holder.tvDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return productItemModelArrayList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvIncrease, tvDecrease, tvCounts;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.nameTextView);
            tvPrice = itemView.findViewById(R.id.priceTextView);
            tvIncrease = itemView.findViewById(R.id.increaseTv);
            tvDecrease = itemView.findViewById(R.id.decreaseTv);
            tvCounts = itemView.findViewById(R.id.counterTv);

        }
    }
}
