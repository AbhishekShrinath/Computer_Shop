package com.abhishekshrinath.computershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abhishekshrinath.computershop.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import io.paperdb.Paper;

public class ConfirmFinalOrderActivity extends AppCompatActivity {

    private EditText Shipment_name,Shipment_number,Shipment_address,Shipment_city;
    private Button confirm_btn;
    private String total_price="";
    ShimmerTextView txtmsg;
    Shimmer shimmer;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_confirm_final_order);

        Shipment_name=findViewById(R.id.ship_name);
        Shipment_number=findViewById(R.id.Ship_Phone_Number_id);
        Shipment_address=findViewById(R.id.shipment_address_id);
        Shipment_city=findViewById(R.id.Shipment_City_id);
        confirm_btn=findViewById(R.id.Confirm_btn_id);

        txtmsg=findViewById(R.id.txtmsg);
        shimmer=new Shimmer();
        shimmer.start(txtmsg);

        total_price=getIntent().getStringExtra("totalprice");
        Toast.makeText(this, "Total Price:"+total_price, Toast.LENGTH_SHORT).show();

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                check();

            }
        });

    }

    private void check()
    {
        if(TextUtils.isEmpty((Shipment_name.getText().toString())))
        {
            Toast.makeText(this, "Please Provide Your Full Name", Toast.LENGTH_SHORT).show();
        }

       else if(TextUtils.isEmpty((Shipment_number.getText().toString())))
        {
            Toast.makeText(this, "Please Provide Your Number", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty((Shipment_address.getText().toString())))
        {
            Toast.makeText(this, "Please Provide Your Correct Address", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty((Shipment_city.getText().toString())))
        {
            Toast.makeText(this, "Please Provide Your City Name", Toast.LENGTH_SHORT).show();
        }
        else
        {
            confirmorder();
        }
    }

    private void confirmorder()
    {
        String savecurrentdate, savecurrenttime;
        Calendar calendarfordate = Calendar.getInstance();
        SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd, yyyy");
        savecurrentdate = currentdate.format(calendarfordate.getTime());

        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss a");
        savecurrenttime = currenttime.format(calendarfordate.getTime());

        String user= Paper.book().read(Prevalent.UsernameKey);
        final DatabaseReference orderref= FirebaseDatabase.getInstance("https://computer-shop-26dfd-default-rtdb.firebaseio.com/").getReference().child("Orders").child(user);
        HashMap<String ,Object> ordermap=new HashMap<>();

        ordermap.put("Total Amount", total_price);
        ordermap.put("Name",Shipment_name.getText().toString());
        ordermap.put("PhoneNumber", Shipment_number.getText().toString());
        ordermap.put("Address", Shipment_address.getText().toString());
        ordermap.put("City", Shipment_city.getText().toString());
        ordermap.put("pDate", savecurrentdate);
        ordermap.put("pTime", savecurrenttime);
        ordermap.put("State","Not Shipped");
        orderref.updateChildren(ordermap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    FirebaseDatabase.getInstance().getReference().child("Cart View").child("User View")
                            .child(user).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(ConfirmFinalOrderActivity.this, "Your Final Order Has been Place Successfully", Toast.LENGTH_SHORT).show();
                                Intent in=new Intent(ConfirmFinalOrderActivity.this,homepage.class);
                                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(in);
                                finish();
                            }
                        }
                    });
                }
            }
        });

    }
}