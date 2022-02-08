package com.example;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    private TextView nameTxtView, collegeTxtView, courseTxtView, registerTextView;
    private TextView emailTxtView, phoneTxtView, passionsTxtView, facebookTxtView, twitterTxtView;
    private ImageView userImageView, emailImageView, phoneImageView, passionsImageView;
    private ImageView facebookImageView, twitterImageView;
    private String email, password;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private String userid;
    private static final String USER = "user";

    TextView myTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        nameTxtView = findViewById(R.id.name_textview);
        collegeTxtView = findViewById(R.id.college_textview);
        courseTxtView = findViewById(R.id.course_textview);
        emailTxtView = findViewById(R.id.email_textview);
        phoneTxtView = findViewById(R.id.phone_textview);
        passionsTxtView = findViewById(R.id.passions_textview);
        registerTextView = findViewById(R.id.register_textview);
      //  facebookTxtView = findViewById(R.id.facebook_textview);
      //  twitterTxtView = findViewById(R.id.twitter_textview)

        userImageView = findViewById(R.id.user_imageview);
        emailImageView = findViewById(R.id.email_imageview);
        phoneImageView = findViewById(R.id.phone_imageview);
        passionsImageView = findViewById(R.id.passions_imageview);
      //   facebookImageView = findViewById(R.id.facebook_imageview);
      //  twitterImageView = findViewById(R.id.twitter_imageview);

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference(USER);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if (ds.child("email").getValue().equals(email)) {
                        nameTxtView.setText(ds.child("fullName").getValue(String.class));
                        emailTxtView.setText(email);
                        collegeTxtView.setText(ds.child("college").getValue(String.class));
                        courseTxtView.setText(ds.child("course").getValue(String.class));
                        phoneTxtView.setText(ds.child("number").getValue(String.class));
                        passionsTxtView.setText(ds.child("passions").getValue(String.class));
                        break;
                }
            }

                registerTextView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                        startActivity(registerIntent);
                    }
                });

        }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

            });

    }
}