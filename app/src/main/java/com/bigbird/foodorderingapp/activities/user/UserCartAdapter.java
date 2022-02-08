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
import com.bigbird.foodorderingapp.utils.IOnItemClickListener;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserCartAdapter extends RecyclerView.Adapter<UserCartAdapter.MyViewHolder> {

    ArrayList<ProductItemModel> productItemModelArrayList;
    Context myContext;
    IOnItemClickListener addClickListener;
    IOnItemClickListener removeClickListener;

    public void setAddClickListener(IOnItemClickListener addClickListener) {
        this.addClickListener = addClickListener;
    }

    public void setRemoveClickListener(IOnItemClickListener removeClickListener) {
        this.removeClickListener = removeClickListener;
    }

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
        Glide.with(myContext).load(itemModel.getImage()).into(holder.imageView);
        holder.tvCounts.setText(itemModel.getCount() + "");
        holder.kitchenName.setText(itemModel.getKitchenName() + "");
        holder.tvIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addClickListener.onItemClick(position);
            }
        });
        holder.tvDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeClickListener.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productItemModelArrayList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvIncrease, tvDecrease, tvCounts, kitchenName;
        CircleImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.nameTextView);
            tvPrice = itemView.findViewById(R.id.priceTextView);
            tvIncrease = itemView.findViewById(R.id.increaseTv);
            tvDecrease = itemView.findViewById(R.id.decreaseTv);
            tvCounts = itemView.findViewById(R.id.counterTv);
            kitchenName = itemView.findViewById(R.id.kitchenName);
            imageView = itemView.findViewById(R.id.profile_image);
        }
    }
}
