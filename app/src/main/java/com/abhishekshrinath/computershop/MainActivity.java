package com.abhishekshrinath.computershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.abhishekshrinath.computershop.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    ImageView splashscreen;
    private static final int num_page=3;
    private ViewPager viewPager;
    private screenslidepageradapter pageradapter;


    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    if(!isConnected(this))
    {
        ShowCustomDialog();
    }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        getSupportActionBar().hide(); //hide the default actionbar

        Paper.init(this);
        splashscreen=findViewById(R.id.image1);

        viewPager=findViewById(R.id.pager);
        pageradapter=new screenslidepageradapter(getSupportFragmentManager());
        viewPager.setAdapter(pageradapter);

        splashscreen.animate().translationY(-2600).setDuration(1000).setStartDelay(3000);

        String UsernameKey=Paper.book().read(Prevalent.UsernameKey);
        String UserpasswordKey=Paper.book().read(Prevalent.UserpasswordKey);
        if(UsernameKey !="" && UserpasswordKey !="")
        {
            if(!TextUtils.isEmpty(UsernameKey) && !TextUtils.isEmpty(UserpasswordKey))
            {
                AllowAccess(UsernameKey,UserpasswordKey);
            }
        }


    }

    private void ShowCustomDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Please Connect to Internet To Proceed Further").setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                finish();
            }

        });
        builder.show();
    }

    private boolean isConnected(MainActivity mainActivity)
    {
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiinfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo Mobileinfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if((wifiinfo !=null && wifiinfo.isConnected()) || (Mobileinfo !=null && Mobileinfo.isConnected()))
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    private void AllowAccess(String usernameKey,String userpasswordKey)
    {
        final String str_username,str_password;

        firebaseDatabase = FirebaseDatabase.getInstance("https://computer-shop-26dfd-default-rtdb.firebaseio.com/");
        databaseReference = firebaseDatabase.getReference();
       String usernameID = "user-"+usernameKey;
        DatabaseReference childBR = databaseReference.child(usernameID);

        // Read from the database
        childBR.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
               String userid= (String) dataSnapshot.child("username").getValue();
               String passw=(String)dataSnapshot.child("Password").getValue();

                if (usernameKey.equals(userid) && userpasswordKey.equals(passw))
                {


                    Toast.makeText(MainActivity.this, "Welcome Back", Toast.LENGTH_SHORT).show();
                    Intent in=new Intent(MainActivity.this,MainActivity2.class);
                    startActivity(in);

                }

                else
                {
                    Toast.makeText(MainActivity.this, "invalid login", Toast.LENGTH_SHORT).show();

                }
            }


            @Override
            public void onCancelled(DatabaseError error)
            {
                // Failed to read value
                Toast.makeText(MainActivity.this, "database error", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private class screenslidepageradapter extends FragmentStatePagerAdapter
    {

        public screenslidepageradapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position)
        {
            switch (position)
            {
                case 0:
                    fragment_on_boarding1 tab1=new fragment_on_boarding1();
                    return tab1;

                case 1:
                    fragment_on_boarding0 tab0=new fragment_on_boarding0();
                    return tab0;

                case 2:
                    fragment_on_boarding2 tab2=new fragment_on_boarding2();
                    return tab2;
            }
            return null;
        }

        @Override
        public int getCount() {
            return num_page;
        }
    }
}