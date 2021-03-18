package com.abhishekshrinath.computershop;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.abhishekshrinath.computershop.Prevalent.Prevalent;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class signin extends Fragment implements View.OnClickListener
{

    String userid="",passw="",usernameID;
    EditText username, password;
    TextInputLayout a,b;
    Button loginbtnid;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    ProgressBar bar;
    CheckBox checkBoxrememberme;


    float n=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.signin,container,false);


        username =v. findViewById(R.id.user11);
        password =v. findViewById(R.id.pass);
        loginbtnid =v. findViewById(R.id.loginbtnid);
        checkBoxrememberme=v.findViewById(R.id.checkbox_id);

        a =v. findViewById(R.id.qweqw);
        b =v. findViewById(R.id.asdxc);

        a.setTranslationX(800);
        a.setAlpha(n);
        a.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

        b.setTranslationX(800);
        b.setAlpha(n);
        b.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();


        username.setTranslationX(800);
        username.setAlpha(n);
        username.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

        password.setTranslationX(800);
        password.setAlpha(n);
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

        loginbtnid.setTranslationX(800);
        loginbtnid.setAlpha(n);
        loginbtnid.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

        checkBoxrememberme.setTranslationX(800);
        checkBoxrememberme.setAlpha(n);
        checkBoxrememberme.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

        return v;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        username =getActivity(). findViewById(R.id.user11);
        password =getActivity(). findViewById(R.id.pass);
        loginbtnid =getActivity(). findViewById(R.id.loginbtnid);
        bar=getActivity().findViewById(R.id.pb);
        checkBoxrememberme=getActivity().findViewById(R.id.checkbox_id);
        Paper.init(getActivity());
        bar.setVisibility(View.GONE);


       loginbtnid.setOnClickListener((View.OnClickListener)this);
    }

    @Override
    public void onClick(View v)
    {
        if(!isConnected(this))
        {

            ShowCustomDialog();
        }

        bar.setVisibility(View.VISIBLE);

        final String str_username,str_password;

        str_username =(String) username.getText().toString();
        str_password =(String) password.getText().toString();

        firebaseDatabase = FirebaseDatabase.getInstance("https://computer-shop-26dfd-default-rtdb.firebaseio.com/");
        databaseReference = firebaseDatabase.getReference();
        usernameID = "user-"+str_username;
        DatabaseReference childBR = databaseReference.child(usernameID);

        // Read from the database
        childBR.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                userid= (String) dataSnapshot.child("username").getValue();
                passw=(String)dataSnapshot.child("Password").getValue();


                if (str_username.equals(userid) && str_password.equals(passw))
                {
                    if(checkBoxrememberme.isChecked())
                    {
                        Paper.book().write(Prevalent.UsernameKey,userid);
                        Paper.book().write(Prevalent.UserpasswordKey,passw);
                    }

                    Paper.book().write(Prevalent.UsernameKey,userid);

                    bar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "login Successfull", Toast.LENGTH_SHORT).show();
                    Intent in=new Intent(getActivity(),MainActivity2.class);
                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(in);

                }
//                else if (TextUtils.isEmpty(userid) && TextUtils.isEmpty(passw))
//                {
//                    Toast.makeText(MainActivity.this, "Username or password is empty!!", Toast.LENGTH_SHORT).show();
//                }
                else
                {
                    bar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "invalid login", Toast.LENGTH_SHORT).show();

                }
            }


            @Override
            public void onCancelled(DatabaseError error)
            {
                // Failed to read value
                Toast.makeText(getActivity(), "database error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void ShowCustomDialog()
    {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setMessage("Please Connect to Internet To Proceed Further").setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        bar.setVisibility(View.GONE);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                getActivity().finish();
            }

        });
        builder.show();
    }


    private boolean isConnected(signin signin)
    {
        ConnectivityManager connectivityManager=(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiinfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo Mobileinfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if((wifiinfo !=null && wifiinfo.isConnected()) || (Mobileinfo !=null && Mobileinfo.isConnected()))
        {
            bar.setVisibility(View.GONE);
            return true;
        }
        else
        {
            bar.setVisibility(View.GONE);
            return false;
        }

    }


}
