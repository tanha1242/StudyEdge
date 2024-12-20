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

import com.example.studyedge.MainActivity;
import com.example.studyedge.R;
import com.example.studyedge.RegistrationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText emailid, password;
    Button btn_log;
    ProgressBar progressBar;
    TextView textView;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is already signed in and update UI accordingly
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is already signed in, navigate to MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);  // Make sure this is necessary for your UI design
        setContentView(R.layout.activity_login);

        // Initialize UI elements
        emailid = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_log = findViewById(R.id.button);
        progressBar = findViewById(R.id.progress);
        textView = findViewById(R.id.newlog);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Redirect to RegistrationActivity when "newlog" is clicked
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Handle login button click
        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);  // Show progress bar while authenticating

                String email = emailid.getText().toString();
                String passwordid = password.getText().toString();

                // Validate input fields
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(LoginActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);  // Hide progress bar if input is empty
                    return;
                }
                if (TextUtils.isEmpty(passwordid)) {
                    Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);  // Hide progress bar if input is empty
                    return;
                }

                // Sign in with email and password
                mAuth.signInWithEmailAndPassword(email, passwordid)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);  // Hide progress bar after authentication

                                if (task.isSuccessful()) {
                                    // Sign-in successful
                                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Sign-in failed
                                    Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
