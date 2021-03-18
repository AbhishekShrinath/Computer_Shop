package com.abhishekshrinath.computershop;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder>
{

    private Context mcontext;
    private List<product> mdata;

    public RecycleViewAdapter(Context mcontext, List<product> mdata) {
        this.mcontext = mcontext;
        this.mdata = mdata;
    }

    @NonNull
    @Override
    public RecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater=LayoutInflater.from(mcontext);
        view=inflater.inflate(R.layout.row_items,parent,false);
        MyViewHolder viewHolder=new MyViewHolder(view);
        viewHolder.view_container.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mcontext,productdisplay_info.class);
                i.putExtra("id",mdata.get(viewHolder.getAdapterPosition()).getPid());

                i.putExtra("laptop_name",mdata.get(viewHolder.getAdapterPosition()).getL_name());
                i.putExtra("laptop_price",mdata.get(viewHolder.getAdapterPosition()).getL_price());

                i.putExtra("laptop_rating",mdata.get(viewHolder.getAdapterPosition()).getL_rating());

                i.putExtra("laptop_info",mdata.get(viewHolder.getAdapterPosition()).getL_info());
                i.putExtra("laptop_image",mdata.get(viewHolder.getAdapterPosition()).getL_imgurl());
                mcontext.startActivity(i);

            }
        });

        viewHolder.Itembtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent i=new Intent(mcontext,List_Add_Product.class);

                i.putExtra("laptop_name",mdata.get(viewHolder.getAdapterPosition()).getL_name());
                i.putExtra("laptop_price",mdata.get(viewHolder.getAdapterPosition()).getL_price());
                i.putExtra("laptop_image",mdata.get(viewHolder.getAdapterPosition()).getL_imgurl());

                mcontext.startActivity(i);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapter.MyViewHolder holder, int position)
    {
        holder.pname.setText(mdata.get(position).getL_name());
        holder.pprice.setText(mdata.get(position).getL_price());
        holder.prating.setText(mdata.get(position).getL_rating());

        Glide.with(mcontext).load(mdata.get(position).getL_imgurl()).into(holder.pimage);
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView pname;
        TextView prating;
        TextView pprice;
        TextView pinfo;
        ImageView pimage;
        Button Itembtn;
        RelativeLayout view_container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pname=itemView.findViewById(R.id.producttitle);
            prating=itemView.findViewById(R.id.productrating);
            pprice=itemView.findViewById(R.id.productprice);
            pimage=itemView.findViewById(R.id.thumbnail);
            view_container=itemView.findViewById(R.id.container);

            Itembtn=itemView.findViewById(R.id.row_btn_lst);
        }
    }
}
