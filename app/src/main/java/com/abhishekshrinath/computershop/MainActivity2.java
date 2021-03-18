package com.abhishekshrinath.computershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import io.paperdb.Paper;

public class MainActivity2 extends AppCompatActivity
{
    private DrawerLayout dr;
    private ActionBarDrawerToggle abt;
    private Button cart,catagery,info,logout;

    //For Closeing App
    public void onBackPressed()
    {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main2);

        logout=findViewById(R.id.Logout_main2);
        info=findViewById(R.id.About_main2);
        cart=findViewById(R.id.cart_main2);
        catagery=findViewById(R.id.cartagoires_main2);


        catagery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(MainActivity2.this,homepage.class);
                startActivity(in);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(MainActivity2.this,cartActivity.class);
                startActivity(in);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().destroy();
                Intent in =new Intent(MainActivity2.this,Sigin_Signup.class);
                startActivity(in);
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(MainActivity2.this,App_Info.class);
                startActivity(in);
            }
        });

        dr=findViewById(R.id.draw);
        abt=new ActionBarDrawerToggle(this,dr,R.string.open,R.string.close);
        abt.setDrawerIndicatorEnabled(true);

        dr.addDrawerListener(abt);
        abt.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final NavigationView nav=findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                int id=item.getItemId();

                if(id==R.id.Catageries)
                {
                    Intent in=new Intent(MainActivity2.this,homepage.class);
                    startActivity(in);
                }
                if(id==R.id.cart)
                {
                    Intent in=new Intent(MainActivity2.this,cartActivity.class);
                    startActivity(in);
                }
                if(id==R.id.about)
                {
                    Intent in=new Intent(MainActivity2.this,App_Info.class);
                    startActivity(in);
                }

                if(id==R.id.logout)
                {
                    Paper.book().destroy();

                    Intent in=new Intent(MainActivity2.this,Sigin_Signup.class);
                    startActivity(in);
                }

                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    public void feedback(View view)
    {
        Intent in=new Intent(MainActivity2.this,feedback.class);
        startActivity(in);
    }
}