package com.example;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.AuthResult;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView titleTextView, registerTextView, forgetPassTextView;
    private EditText userEditText, passwordEditText, emailEditText;
    private ImageView logoImageView;
    private Button loginButton;
    private FirebaseAuth mAuth;
    private static final String TAG = "MainActivity";
    private String email, password;
    Button btnHindi, btnEnglish;
    Context context;
    Resources resources;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleTextView = findViewById(R.id.title_textview);
        registerTextView = findViewById(R.id.register_textview);
        forgetPassTextView = findViewById(R.id.forgot_password_textview);
        userEditText = findViewById(R.id.user_edittext);
        passwordEditText = findViewById(R.id.password_edittext);
        logoImageView = findViewById(R.id.logo_imageview);
        loginButton = findViewById(R.id.button);
        emailEditText = findViewById(R.id.email_edittext);

        btnHindi = findViewById(R.id.btnHindi);
        btnEnglish = findViewById(R.id.btnEnglish);

        mAuth = FirebaseAuth.getInstance();

        //checking if user is logged in
        if (mAuth.getCurrentUser() != null) {
            updateUI(mAuth.getCurrentUser());
        }


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailEditText.getText().toString();
                password = passwordEditText.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });
            }

        });

        btnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context = LocaleHelper.setLocale(MainActivity.this, "en");
                resources = context.getResources();
                titleTextView.setText(resources.getString(R.string.language));
                registerTextView.setText(resources.getString(R.string.language));
                userEditText.setText(resources.getString(R.string.language));
                passwordEditText.setText(resources.getString(R.string.language));
                loginButton.setText(resources.getString(R.string.language));

            }
        });

        btnHindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context = LocaleHelper.setLocale(MainActivity.this, "hi");
                resources = context.getResources();
                titleTextView.setText(resources.getString(R.string.language));
                registerTextView.setText(resources.getString(R.string.language));
                userEditText.setText(resources.getString(R.string.language));
                passwordEditText.setText(resources.getString(R.string.language));
                loginButton.setText(resources.getString(R.string.language));

            }
        });


        registerTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI(currentUser);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI(currentUser);
        }
    }

    public void updateUI(FirebaseUser currentUser) {
        Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
        profileIntent.putExtra("email", currentUser.getEmail());
        Log.v("DATA", currentUser.getUid());
        startActivity(profileIntent);
    }

}