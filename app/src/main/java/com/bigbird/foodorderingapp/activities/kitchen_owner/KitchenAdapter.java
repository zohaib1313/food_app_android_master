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

import java.util.ArrayList;

public class KitchenAdapter extends RecyclerView.Adapter<KitchenAdapter.MyViewHolder> {

    ArrayList<ProductItemModel> productItemModelArrayList;
    Context myContext;



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



    }

    @Override
    public int getItemCount() {
        return productItemModelArrayList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);


        }
    }
}
