package com.abhishekshrinath.computershop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.imageview.ShapeableImageView;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerButton;
import com.romainpiel.shimmer.ShimmerTextView;

public class App_Info extends AppCompatActivity
{
    private ShimmerTextView title;
    private Shimmer shimmer,shimmer1;
    ShimmerButton app_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_app_info);

        title=findViewById(R.id.App_title);
        app_btn=findViewById(R.id.app_btn);

        shimmer=new Shimmer();
        shimmer1=new Shimmer();
        shimmer.start(title);
        shimmer1.start(app_btn);

    }
}