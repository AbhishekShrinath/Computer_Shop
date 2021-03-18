package com.abhishekshrinath.computershop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class desktop_laptop extends AppCompatActivity
{
    Button desktopbt,laptopbt;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        getSupportActionBar().hide(); //hide the default actionbar

        setContentView(R.layout.activity_desktop_laptop);
        desktopbt=findViewById(R.id.btndesktop);
        laptopbt=findViewById(R.id.btnlabtop);

        desktopbt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent in =new Intent(desktop_laptop.this,desktop.class);
                startActivity(in);
                Toast.makeText(desktop_laptop.this, "you click on desktop", Toast.LENGTH_SHORT).show();
            }
        });
        laptopbt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent in =new Intent(desktop_laptop.this,laptop.class);
                startActivity(in);
                Toast.makeText(desktop_laptop.this, "you click on laptop", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void desktopbtn(View view) {
    }
}