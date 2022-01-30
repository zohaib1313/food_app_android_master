package com.bigbird.foodorderingapp.activities.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bigbird.foodorderingapp.R;
import com.bigbird.foodorderingapp.models.AdminViewsModel;
import com.bigbird.foodorderingapp.models.ProductItemModel;

import java.util.ArrayList;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.MyViewHolder> {

    ArrayList<AdminViewsModel> adminViewsModels;
    Context myContext;



    public AdminAdapter(ArrayList<AdminViewsModel> adminViewsModels, Context myContext) {
        this.adminViewsModels = adminViewsModels;
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

        AdminViewsModel itemModel = adminViewsModels.get(position);
        holder.tvName.setText(itemModel.getTitle());
        holder.tvPrice.setText(itemModel.getValue());



    }

    @Override
    public int getItemCount() {
        return adminViewsModels.size();
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
