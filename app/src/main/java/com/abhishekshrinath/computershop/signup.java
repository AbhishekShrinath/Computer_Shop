package com.abhishekshrinath.computershop;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.abhishekshrinath.computershop.Model.Users;
import com.abhishekshrinath.computershop.Prevalent.Prevalent;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

import io.paperdb.Paper;

public class signup extends Fragment implements View.OnClickListener
{
    Button btn;
    EditText fname,lname,email,number,password,userid;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.signup,container,false);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        fname=getActivity().findViewById(R.id.fname);
        lname=getActivity().findViewById(R.id.lname);
        userid=getActivity().findViewById(R.id.usernameid);
        email=getActivity().findViewById(R.id.email);
        number=getActivity().findViewById(R.id.number);
        password=getActivity().findViewById(R.id.password);
        Paper.init(getActivity());

        fname.addTextChangedListener(textWatcher);
        lname.addTextChangedListener(textWatcher);
        userid.addTextChangedListener(textWatcher);
        email.addTextChangedListener(textWatcher);
        number.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);

        btn=getActivity().findViewById(R.id.btn);
        btn.setOnClickListener((View.OnClickListener) this);
    }
    private TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            String firstname=fname.getText().toString();
            String lastname=lname.getText().toString();
            String username=userid.getText().toString();
            String pass=password.getText().toString();
            String phone_num=number.getText().toString();
            String Email=email.getText().toString();

            Pattern validpassword=Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                   // "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 8 characters
                    "$");



            if(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())
            {
                email.setTextColor(Color.GREEN);
                if(!validpassword.matcher(pass).matches())
                {
                    password.setTextColor(Color.RED);
                    password.setError("password is to weak");
                }
                else
                {
                    password.setTextColor(Color.GREEN);
                    btn.setEnabled(!firstname.isEmpty() && !lastname.isEmpty()
                            && !phone_num.isEmpty() && !Email.isEmpty()
                            && !username.isEmpty() && !pass.isEmpty());
                }
            }
            else
            {
                email.setTextColor(Color.RED);
                number.setError("Invalid Number");
                email.setError("invalid Email");
            }


        }

        @Override
        public void afterTextChanged(Editable s)
        {


        }
    };

    @Override
    public void onClick(View v)
    {
        try {

            {
                String firstname =fname.getText().toString();
                String lastname =lname.getText().toString();
                String username =userid.getText().toString();
                String email_id =email.getText().toString();
                String mobile_number =number.getText().toString();
                String pass =password.getText().toString();

                String userid = "user-" + username;

                Paper.book().read(Prevalent.UserphoneKey,username);
                Paper.book().read(Prevalent.UserpasswordKey,pass);

                //root
                firebaseDatabase = FirebaseDatabase.getInstance("https://computer-shop-26dfd-default-rtdb.firebaseio.com/");
                databaseReference = firebaseDatabase.getReference(userid);

                //child

                databaseReference.child("Firstname").setValue(firstname);
                databaseReference.child("lastname").setValue(lastname);
                databaseReference.child("username").setValue(username);
                databaseReference.child("email_id").setValue(email_id);
                databaseReference.child("mobileNumber").setValue(mobile_number);
                databaseReference.child("Password").setValue(pass);
                Users users=new Users();
                users.setMobileNumber(mobile_number);
                users.setUsername(username);
                users.setPassword(pass);

                Paper.book().write(Prevalent.UsernameKey,userid);
                Paper.book().write(Prevalent.UserphoneKey,mobile_number);
                Paper.book().write(Prevalent.UserpasswordKey,pass);

                ((Sigin_Signup)getActivity()).viewPager.setCurrentItem(0); //position of Signin

                Toast.makeText(getActivity(), "SignUp Successfull", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}

