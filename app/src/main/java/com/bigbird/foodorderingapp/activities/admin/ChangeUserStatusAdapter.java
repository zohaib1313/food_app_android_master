package com.bigbird.foodorderingapp.activities.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bigbird.foodorderingapp.R;
import com.bigbird.foodorderingapp.models.AdminViewsModel;
import com.bigbird.foodorderingapp.utils.AppConstant;
import com.bigbird.foodorderingapp.utils.IOnItemClickListener;
import com.bigbird.foodorderingapp.utils.IStatusChangeListener;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ChangeUserStatusAdapter extends RecyclerView.Adapter<ChangeUserStatusAdapter.MyViewHolder> {

    ArrayList<ChangeUserStatusModel> changeUserStatusModels;
    Context myContext;
    IStatusChangeListener iStatusChangeListener;

    public void setiStatusChangeListener(IStatusChangeListener iStatusChangeListener) {
        this.iStatusChangeListener = iStatusChangeListener;
    }

    public ChangeUserStatusAdapter(ArrayList<ChangeUserStatusModel> changeUserStatusModels, Context myContext) {
        this.changeUserStatusModels = changeUserStatusModels;
        this.myContext = myContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_chage_user_status, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ChangeUserStatusModel itemModel = changeUserStatusModels.get(position);
        holder.tvName.setText(itemModel.getUserName());
        holder.tvContact.setText(itemModel.getContact());
        holder.aSwitch.setChecked(itemModel.status);
        holder.aSwitch.setText(getStatus(itemModel.status));
        if (itemModel.userType.equals(AppConstant.UserTypeUser)) {
            Glide.with(myContext).load(itemModel.employCard).into(holder.imageView);
        } else if (itemModel.userType.equals(AppConstant.UserTypeKitchen)) {
            Glide.with(myContext).load(itemModel.cnicFront).into(holder.imageView);
        }
        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                  iStatusChangeListener.onItemClick(position,b,itemModel.userType,itemModel.getUserName());
            }
        });


    }

    private String getStatus(boolean status) {
        if (status)
            return "Status: Approved";
        else
            return "Status Not-Approved";
    }

    @Override
    public int getItemCount() {
        return changeUserStatusModels.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvContact;
        ImageView imageView;
        Switch aSwitch;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.nameTextView);
            tvContact = itemView.findViewById(R.id.priceTextView);
            imageView = itemView.findViewById(R.id.profile_image);
            aSwitch = itemView.findViewById(R.id.switch1);


        }
    }
}
