package com.example.studyedge;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class amitanha extends AppCompatActivity {
    EditText name, contact, dob;
    Button insert, update, delete, view;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_amitanha);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        name = findViewById(R.id.name);
        dob = findViewById(R.id.dob);
        contact=findViewById(R.id.contact);
        insert = findViewById(R.id.btnInsert);
        update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.btnDelete);
        view = findViewById(R.id.btnView);
        db =new DBHelper(this);


        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTXT =name.getText().toString();
                String contactTXT =contact.getText().toString();
                String dobTXT =dob.getText().toString();
                Boolean checkinsertdata = db.insertuserdata(nameTXT,contactTXT,dobTXT);
                if(checkinsertdata==true){
                    Toast.makeText(amitanha.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(amitanha.this, "New Entry  Not Inserted", Toast.LENGTH_SHORT).show();

                }
            }
        });



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTXT =name.getText().toString();
                String contactTXT =contact.getText().toString();
                String dobTXT =dob.getText().toString();
                Boolean checkupdatedata = db.Updateuserdata(nameTXT,contactTXT,dobTXT);
                if(checkupdatedata==true){
                    Toast.makeText(amitanha.this, "Entry Updated", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(amitanha.this, "New Entry  Not Updated", Toast.LENGTH_SHORT).show();

                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTXT =name.getText().toString();

                Boolean checkdeletedata = db.deletedata(nameTXT);
                if(checkdeletedata==true){
                    Toast.makeText(amitanha.this, "Entry Deleted", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(amitanha.this, "New Entry  Not Deleted", Toast.LENGTH_SHORT).show();

                }
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = db.getdata();
                if(res.getCount()==0){
                    Toast.makeText(amitanha.this,"No Entry Exist",Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Name:"+res.getString(0)+" \n");
                    buffer.append("Contact:"+res.getString(1)+"\n ");
                    buffer.append("DOB:"+res.getString(2)+"\n\n ");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(amitanha.this);
                builder.setCancelable(true);
                builder.setTitle("User Entries");
                builder.setMessage(buffer.toString());
                builder.show();

            }
        });

    }
}