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

public class Add_To_Cart extends AppCompatActivity
{

    TextView product_name,product_price;
    ImageView product_image;
    Button floatingActionButton;
    FloatingActionButton floatingActionButton1;
    ElegantNumberButton elegantNumberButton;
    private String state="Normal";
    String user= Paper.book().read(Prevalent.UsernameKey);


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide(); //hide the default actionbar

        setContentView(R.layout.activity_add__to__cart);
        product_name=findViewById(R.id.product_name_details);
        product_price=findViewById(R.id.product_price_details);
        product_image=findViewById(R.id.product_image_details);
        floatingActionButton=findViewById(R.id.Add_to_cart_btn);
        floatingActionButton1=findViewById(R.id.View_cart_btn);
        elegantNumberButton=findViewById(R.id.quentity);


        Bundle bundle=getIntent().getExtras();
        String forfbdb=bundle.getString("name");

        String str=bundle.getString("name");
        product_name.setText("Product Name: "+str);

        String productprice_fbdb=bundle.getString("price");

        String str2=bundle.getString("price");
        product_price.setText("Price: ₹"+str2);
//-----------------------------------------------------------------//
        Bundle bundle1=getIntent().getExtras();
        String forfbdb1=bundle.getString("laptop_name");

        String str1=bundle.getString("laptop_name");
        product_name.setText("Product Name: "+str);

        String productprice_fbdb1=bundle.getString("getL_price");

        String str21=bundle.getString("getL_price");
        product_price.setText("Price: ₹"+str2);
//====================================================================//


        if(getIntent().hasExtra("bitArray"))
        {
            Bitmap bitM = BitmapFactory.decodeByteArray( getIntent().getByteArrayExtra("bitArray"),0,getIntent().getByteArrayExtra("bitArray").length);
            product_image.setImageBitmap(bitM);
        }

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Add_To_Cart.this,cartActivity.class);
                startActivity(in);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(state.equals("order Placed"))
                {
                    Toast.makeText(Add_To_Cart.this, "You can Add purchase more Product,Once Your order is Shipped or Confirmed", Toast.LENGTH_LONG).show();
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
                        cartmap.put("pname", product_name.getText().toString());
                        cartmap.put("pprice",productprice_fbdb.toString());
                        cartmap.put("pQuantity", elegantNumberButton.getNumber());
                        cartmap.put("pDate", savecurrentdate);
                        cartmap.put("pTime", savecurrenttime);

                        cartref.child("User View").child(user).child("Products").child(forfbdb)
                                .updateChildren(cartmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                cartref.child("Admin View").child(user).child("Products").child(forfbdb)
                                        .updateChildren(cartmap).addOnCompleteListener(new OnCompleteListener<Void>()
                                {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task)
                                    {

                                        Toast.makeText(Add_To_Cart.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                                        Intent in=new Intent(Add_To_Cart.this,homepage.class);
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
        DatabaseReference orderref=FirebaseDatabase.getInstance("https://computer-shop-26dfd-default-rtdb.firebaseio.com/").getReference().child("Orders").child(user);
        orderref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(snapshot.exists())
                {
                    String ShippmentState=snapshot.child("State").getValue().toString();
                    if(ShippmentState.equals("Shipped"))
                    {
                        state="order Shipped";
                    }
                    if(ShippmentState.equals("Not Shipped"))
                    {
                        state="order Placed";
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