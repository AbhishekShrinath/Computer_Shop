package com.abhishekshrinath.computershop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ViewTarget;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.io.ByteArrayOutputStream;

public class productdisplay_info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_productdisplay_info);

        getSupportActionBar().hide(); //hide the default actionbar

        //getting data
     //   String get_id=getIntent().getExtras().getString("id");
        String get_name=getIntent().getExtras().getString("laptop_name");
        String get_price=getIntent().getExtras().getString("laptop_price");
        String get_rating=getIntent().getExtras().getString("laptop_rating");
        String get_info=getIntent().getExtras().getString("laptop_info");
        String get_image=getIntent().getExtras().getString("laptop_image");

        //INITALIZE VIEW
        CollapsingToolbarLayout collapsingToolbarLayout=findViewById(R.id.collapsingtoolbar_id);
        collapsingToolbarLayout.setTitleEnabled(true);

        TextView tv_name=findViewById(R.id.pp_producttitle);
        TextView tv_price=findViewById(R.id.pp_productprice);
        TextView tv_rating=findViewById(R.id.pp_productrating);
        TextView tv_info=findViewById(R.id.pp_productinfo);
        ImageView l_imageView=findViewById(R.id.pp_thumbnail);
        Button button=findViewById(R.id.cart_btn_id);

        //setindd value in view
        tv_name.setText(get_name);
        tv_price.setText(get_price);
        tv_rating.setText(get_rating);
        tv_info.setText(get_info);

        collapsingToolbarLayout.setTitle(get_name);


        //seting img

        RequestOptions requestOptions=new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

        Glide.with(this).load(get_image).into(l_imageView);

        l_imageView.setDrawingCacheEnabled(true);
        Bitmap b=l_imageView.getDrawingCache();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in=new Intent(productdisplay_info.this,Add_To_Cart.class);
                in.putExtra("name",tv_name.getText().toString());
                in.putExtra("price",tv_price.getText().toString());

                Bitmap bitImg= l_imageView.getDrawingCache();
                ByteArrayOutputStream baoS = new ByteArrayOutputStream();
                bitImg.compress(Bitmap.CompressFormat.JPEG, 50, baoS);
                in.putExtra("bitArray", baoS.toByteArray());
                startActivity(in);
            }
        });

    }

}