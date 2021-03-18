package com.abhishekshrinath.computershop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.abhishekshrinath.computershop.Prevalent.Prevalent;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.paperdb.Paper;

public class feedback extends AppCompatActivity
{
    private EditText feedback;
    private String user=Paper.book().read(Prevalent.UsernameKey);
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        feedback=findViewById(R.id.feedback_text);
        Paper.init(this);

    }

    public void sendFeedback(View view)
    {
        String feed=feedback.getText().toString();
        if(TextUtils.isEmpty(feed))
        {
            feedback.setError("This Item Cannot Be Empty");
        }
        else if(!TextUtils.isEmpty(feed))
         {
            RatingBar star=findViewById(R.id.rating_star);

            float rating=star.getRating();

            String id = user + " feedback";
            String feedbacksent = feedback.getText().toString();
            firebaseDatabase = FirebaseDatabase.getInstance("https://computer-shop-26dfd-default-rtdb.firebaseio.com/");
            databaseReference = firebaseDatabase.getReference(id);

            databaseReference.child("Feedback").setValue(feedbacksent);
             databaseReference.child("Rating").setValue(String.valueOf(rating));

             Toast.makeText(this, "Thanks For Your FeedBack", Toast.LENGTH_SHORT).show();
             Intent in = new Intent(feedback.this, MainActivity2.class);
            startActivity(in);

        }

    }
}