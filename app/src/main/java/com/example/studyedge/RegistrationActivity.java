package com.example.studyedge;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {
    EditText emailid, password;
    Button btn_reg;
    ProgressBar progressBar;
    TextView textView;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if the user is already signed in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Make sure this is required in your use case
        setContentView(R.layout.activity_registration);

        // Initialize UI elements
        emailid = findViewById(R.id.email1);
        password = findViewById(R.id.password1);
        btn_reg = findViewById(R.id.button1);
        progressBar = findViewById(R.id.progress1);
        textView = findViewById(R.id.newlog1);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Handle text view click to navigate to login screen
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Handle the registration button click
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE); // Show progress bar

                String email = emailid.getText().toString();
                String passwordid = password.getText().toString();

                // Validate input
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(RegistrationActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE); // Hide progress bar
                    return;
                }

                if (TextUtils.isEmpty(passwordid)) {
                    Toast.makeText(RegistrationActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE); // Hide progress bar
                    return;
                }

                // Create a new user with email and password
                mAuth.createUserWithEmailAndPassword(email, passwordid)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE); // Hide progress bar when done

                                if (task.isSuccessful()) {
                                    // Registration successful, navigate to MainActivity
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(RegistrationActivity.this, "Authentication success.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish(); // Close the registration screen
                                } else {
                                    // Registration failed
                                    Toast.makeText(RegistrationActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
