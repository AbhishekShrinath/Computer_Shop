package com.abhishekshrinath.computershop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;

public class Sigin_Signup extends AppCompatActivity
{
    TabLayout tabLayout;
    ViewPager viewPager;
    float v=0;
//    Stop get to previous Activity
//    @Override
//    public void onBackPressed()
//    {
//        moveTaskToBack(false);
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        getSupportActionBar().hide(); //hide the default actionbar

        setContentView(R.layout.activity_sigin__signup);
        tabLayout=findViewById(R.id.tablayout);
        viewPager=findViewById(R.id.viewpager);

        final Sign_Signup_Adapter adapter=new Sign_Signup_Adapter(getSupportFragmentManager(),this,tabLayout.getTabCount());

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setTranslationY(300);
        tabLayout.setAlpha(v);
        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();

    }
}