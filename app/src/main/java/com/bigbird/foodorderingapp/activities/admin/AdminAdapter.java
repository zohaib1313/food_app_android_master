package com.bigbird.foodorderingapp.activities.admin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bigbird.foodorderingapp.R;
import com.bigbird.foodorderingapp.models.Actions;
import com.bigbird.foodorderingapp.models.AdminViewsModel;
import com.bigbird.foodorderingapp.models.ProductItemModel;
import com.bigbird.foodorderingapp.utils.AppConstant;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_admin_views, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        AdminViewsModel itemModel = adminViewsModels.get(position);
        holder.tvTitle.setText(itemModel.getTitle());
        holder.tvValue.setText(itemModel.getValue());
        holder.tvTitle.setTextColor(myContext.getResources().getColor(itemModel.getTextColor()));
        holder.tvValue.setTextColor(myContext.getResources().getColor(itemModel.getTextColor()));
        holder.itemView.setBackgroundColor(myContext.getResources().getColor(itemModel.getBoxColor()));

         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent =new Intent(myContext,ChangeUserStatusActivity.class);
                 if(itemModel.getActions()== Actions.actionKitchen){
                     intent.putExtra(AppConstant.UserType,AppConstant.UserTypeKitchen);
                 }else  if(itemModel.getActions()== Actions.actionUsers){
                     intent.putExtra(AppConstant.UserType,AppConstant.UserTypeUser);
                 }
                 myContext.startActivity(intent);
             }
         });
    }

    @Override
    public int getItemCount() {
        return adminViewsModels.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvValue;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvValue = itemView.findViewById(R.id.tvValue);
//            tvPrice = itemView.findViewById(R.id.tvPrice);


        }
    }
}
