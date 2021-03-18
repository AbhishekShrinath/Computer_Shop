package com.abhishekshrinath.computershop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class homepage extends AppCompatActivity {

    Button storagebtn1,pcbtn1,displaybtn1,softwarebtn1;
    FloatingActionButton cartviewbtn1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_homepage);
        getSupportActionBar().hide(); //hide the default actionbar



        cartviewbtn1=(FloatingActionButton) findViewById(R.id.shopingcartbtn1);

        storagebtn1=(Button) findViewById(R.id.btnstorage1);
        pcbtn1=(Button)findViewById(R.id.btnpc1);
        displaybtn1=(Button)findViewById(R.id.btndisplay1);
        softwarebtn1=(Button)findViewById(R.id.btnsoftware1);


        storagebtn1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent in=new Intent(homepage.this,storage.class);
                startActivity(in);
                Toast.makeText(homepage.this, "Clicked On Storages devices", Toast.LENGTH_SHORT).show();
            }
        });
        pcbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent in = new Intent(homepage.this, desktop_laptop.class);
                startActivity(in);
            }
        });
        displaybtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent in = new Intent(homepage.this, display.class);
                startActivity(in);
                Toast.makeText(homepage.this, "Clicked On Display", Toast.LENGTH_SHORT).show();
            }
        });

        softwarebtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent in = new Intent(homepage.this, software.class);
                startActivity(in);
                Toast.makeText(homepage.this, "Clicked On Software", Toast.LENGTH_SHORT).show();
            }
        });

        cartviewbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(homepage.this, cartActivity.class);
                startActivity(in);
            }
        });



    }
}