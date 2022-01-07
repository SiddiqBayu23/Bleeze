package com.udinus.bleezesparepart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Forgot extends AppCompatActivity {
    EditText etEmail;
    FirebaseDatabase database;
    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        database = FirebaseDatabase.getInstance("https://bleezesparepart-default-rtdb.firebaseio.com/");

        etEmail = findViewById(R.id.et_email);
    }

    public void clickReset(View view) {
        String email = etEmail.getText().toString();
        if(TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "email kosong!", Toast.LENGTH_SHORT).show();
        } else {
            reff = database.getReference("akun");
            reff.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds: dataSnapshot.getChildren()) {
                        if(ds.child("email").getValue(String.class).equals(email)) {
                            Intent i = new Intent(Forgot.this, Reset.class);
                            i.putExtra("EMAIL", email);
                            i.putExtra("ID_USER", ds.getKey());
                            startActivity(i);
                            return;
                        }
                    }
                    Toast.makeText(getApplicationContext(), "email tidak terdaftar!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}