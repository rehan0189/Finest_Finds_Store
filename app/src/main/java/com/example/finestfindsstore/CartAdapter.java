package com.example.finestfindsstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItemClass> cartItemList;
    private Context context;

    public CartAdapter(List<CartItemClass> cartItemList, Context context) {
        this.cartItemList = cartItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_single_item_design, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItemClass cartItem = cartItemList.get(position);

        holder.itemImageView.setImageResource(cartItem.getImageResource());
        holder.itemNameTextView.setText(cartItem.getItemName());
        holder.itemPriceTextView.setText(cartItem.getItemPrice());
        holder.itemCountTextView.setText(String.valueOf(cartItem.getItemCount()));

        // Add click listeners or any other logic you need for buttons in the item view
        // For example, handle the add and subtract buttons to update item count
        holder.btnCountAdd.setOnClickListener(v -> {
            // Handle add button click
            // Update item count and notify adapter
            cartItem.setItemCount(cartItem.getItemCount() + 1);
            notifyDataSetChanged();
        });

        holder.btnCountSubtract.setOnClickListener(v -> {
            // Handle subtract button click
            // Update item count and notify adapter
            if (cartItem.getItemCount() > 0) {
                cartItem.setItemCount(cartItem.getItemCount() - 1);
                notifyDataSetChanged();
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            // Handle delete button click
            // Remove item from the list and notify adapter
            cartItemList.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImageView;
        TextView itemNameTextView;
        TextView itemPriceTextView;
        TextView itemCountTextView;
        ImageButton btnCountAdd;
        ImageButton btnCountSubtract;
        ImageButton btnDelete;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImageView = itemView.findViewById(R.id.itemImageView);
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            itemPriceTextView = itemView.findViewById(R.id.itemPriceTextView);
            itemCountTextView = itemView.findViewById(R.id.itemCountTextView);
            btnCountAdd = itemView.findViewById(R.id.btnCountAdd);
            btnCountSubtract = itemView.findViewById(R.id.btnCountSubtract);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}

