package com.example.studyedge;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    Button logout;
    TextView textView;
    Button camera, gallery, amitanha, map;
    ImageView imageView;
    Button sensor;
    private final int REQUEST_IMAGE_CAPTURE = 1;
    private  final int REQUEST_GALLERY_CAPTURE = 2;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);  // Enables edge-to-edge mode for the activity.
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        logout = findViewById(R.id.out);
        user = auth.getCurrentUser();
        textView = findViewById(R.id.text);
        camera = findViewById(R.id.camera);
        gallery = findViewById(R.id.gallery);
        imageView = findViewById(R.id.image);
        sensor = findViewById(R.id.sensor);
        amitanha = findViewById(R.id.amitanha);
        map = findViewById(R.id.map);


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_GALLERY_CAPTURE);
            }
        });
        sensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SensorActivity.class);
                startActivity(intent);
            }
        });
        if (user == null) {
            // If the user is not logged in, redirect to the LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();  // Finish MainActivity so that the user cannot navigate back to it.
        } else {
            // If the user is logged in, display their info
            textView.setText("Welcome, " + user.getDisplayName());
        }

        // Handle logout functionality
        logout.setOnClickListener(v -> {
            auth.signOut();  // Sign out the current user
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);  // Redirect to login
            startActivity(intent);
            finish();  // Finish MainActivity so that the user cannot navigate back to it.
        });
        amitanha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, amitanha.class);
                startActivity(intent);
            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });



        // Adjust padding based on system bar insets to handle edge-to-edge mode properly
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int
            resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bitmap img = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(img);


            }
            if (requestCode == REQUEST_GALLERY_CAPTURE) {
                Bitmap img = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(img);
            }

        }
    }}
