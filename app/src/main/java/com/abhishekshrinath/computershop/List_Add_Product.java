package com.abhishekshrinath.computershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abhishekshrinath.computershop.Prevalent.Prevalent;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

import io.paperdb.Paper;

public class List_Add_Product extends AppCompatActivity
{

    TextView product_name2,product_price2;
    ImageView product_image2;
    Button floatingActionButton2;
    FloatingActionButton floatingActionButton12;
    ElegantNumberButton elegantNumberButton2;
    private String state2="Normal";
    String user2= Paper.book().read(Prevalent.UsernameKey);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        getSupportActionBar().hide(); //hide the default actionbar

        setContentView(R.layout.activity_list__add__product);
        product_name2=findViewById(R.id.list_product_name_details);
        product_price2=findViewById(R.id.list_product_price_details);
        product_image2=findViewById(R.id.list_product_image_details);
        floatingActionButton2=findViewById(R.id.list_Add_to_cart_btn);
        floatingActionButton12=findViewById(R.id.list_View_cart_btn);
        elegantNumberButton2=findViewById(R.id.list_quentity);

        String get_name=getIntent().getExtras().getString("laptop_name");
        String get_price=getIntent().getExtras().getString("laptop_price");
        String get_image=getIntent().getExtras().getString("laptop_image");
        RequestOptions requestOptions=new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
        Glide.with(this).load(get_image).into(product_image2);

        product_name2.setText("Product Name: "+get_name);
        product_price2.setText("Price: ₹"+get_price);

        floatingActionButton12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(List_Add_Product.this,cartActivity.class);
                startActivity(in);
            }
        });

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(state2.equals("order Placed"))
                {
                    Toast.makeText(List_Add_Product.this, "You can Add purchase more Product,Once Your order is Shipped or Confirmed", Toast.LENGTH_LONG).show();
                }
                else
                {
                    try
                    {
                        String savecurrentdate, savecurrenttime;
                        Calendar calendarfordate = Calendar.getInstance();
                        SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd, yyyy");
                        savecurrentdate = currentdate.format(calendarfordate.getTime());

                        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss a");
                        savecurrenttime = currenttime.format(calendarfordate.getTime());

                        String user=Paper.book().read(Prevalent.UsernameKey);
                        String number=Paper.book().read(Prevalent.UserphoneKey);

                        final DatabaseReference cartref=FirebaseDatabase.getInstance("https://computer-shop-26dfd-default-rtdb.firebaseio.com/").getReference().child("Cart View");

                        final HashMap<String, Object> cartmap = new HashMap<>();
                        cartmap.put("pname", get_name);
                        cartmap.put("pprice",get_price.toString());
                        cartmap.put("pQuantity", elegantNumberButton2.getNumber());
                        cartmap.put("pDate", savecurrentdate);
                        cartmap.put("pTime", savecurrenttime);

                        cartref.child("User View").child(user).child("Products").child(get_name)
                                .updateChildren(cartmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                cartref.child("Admin View").child(user).child("Products").child(get_name)
                                        .updateChildren(cartmap).addOnCompleteListener(new OnCompleteListener<Void>()
                                {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        Toast.makeText(List_Add_Product.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                                        Intent in=new Intent(List_Add_Product.this,homepage.class);
                                        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                        startActivity(in);
                                    }
                                });
                            }
                        });
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        checkOrderstate();
    }

    private void checkOrderstate()
    {
        DatabaseReference orderref=FirebaseDatabase.getInstance("https://computer-shop-26dfd-default-rtdb.firebaseio.com/").getReference().child("Orders").child(user2);
        orderref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(snapshot.exists())
                {
                    String ShippmentState=snapshot.child("State").getValue().toString();
                    if(ShippmentState.equals("Shipped"))
                    {
                        state2="order Shipped";
                    }
                    if(ShippmentState.equals("Not Shipped"))
                    {
                        state2="order Placed";
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }
}