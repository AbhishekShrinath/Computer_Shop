package com.abhishekshrinath.computershop.ViewHolder;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abhishekshrinath.computershop.Interface.ItemClickListner;
import com.abhishekshrinath.computershop.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtproductName,txtproductPrice,txtproductQuantity;
    private ItemClickListner itemClickListener;

    public CartViewHolder(@NonNull View itemView)
    {
        super(itemView);

        txtproductName=itemView.findViewById(R.id.product_name_cart);
        txtproductPrice=itemView.findViewById(R.id.product_price_cart);
        txtproductQuantity=itemView.findViewById(R.id.product_Quantity_cart);

    }

    @Override
    public void onClick(View v)
    {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }

    public void setItemClickListener(ItemClickListner itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
