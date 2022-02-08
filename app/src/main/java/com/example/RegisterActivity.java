package com.example;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    private TextView profileTextView;
    private ImageView picImageView;
    private CheckBox maleCheckBox, femaleCheckBox, otherCheckbox;
    private EditText emailEditText, passwordEditText, fullNameEditText, collegeEditText;
    private EditText courseEditText, phoneEditText, passionsEditText;
    private Button registerButton;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private static final String USERS = "users";
    private String TAG = "RegisterActivity";
    private String username, fname, email, college, course, phone, passions;
    private String password;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        picImageView = findViewById(R.id.pic_imageview);
        maleCheckBox = findViewById(R.id.male_checkbox);
        femaleCheckBox = findViewById(R.id.female_checkbox);
        otherCheckbox = findViewById(R.id.other_checkbox);
        emailEditText = findViewById(R.id.email_edittext);
        passwordEditText = findViewById(R.id.password_edittext);
        fullNameEditText = findViewById(R.id.fullname_edittext);
        collegeEditText = findViewById(R.id.college_edittext);
        courseEditText = findViewById(R.id.course_edittext);
        phoneEditText =findViewById(R.id.phone_edittext);
        passionsEditText = findViewById(R.id.passions_edittext);
        registerButton = findViewById(R.id.register_button);
        profileTextView = findViewById(R.id.profile_textview);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USERS);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insert data into firebase database
                if(emailEditText.getText().toString() != null && passwordEditText.getText().toString() != null) {
                    fname = fullNameEditText.getText().toString();
                    email = emailEditText.getText().toString();
                    phone = phoneEditText.getText().toString();
                    college = collegeEditText.getText().toString();
                    course = courseEditText.getText().toString();
                    password = passwordEditText.getText().toString();
                    passions = passionsEditText.getText().toString();
                    User user = new User(email, password, fname, college, course, passions, phone);
                    registerUser();
                }
            }
        });

        profileTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(registerIntent);
            }
        });

    }



    public void registerUser() {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void updateUI(FirebaseUser currentUser) {
        String keyid = mDatabase.push().getKey();
        mDatabase.child(keyid).setValue(user); //adding user information to the database
        Intent loginIntent = new Intent(this, MainActivity.class);
        startActivity(loginIntent);
    }

}