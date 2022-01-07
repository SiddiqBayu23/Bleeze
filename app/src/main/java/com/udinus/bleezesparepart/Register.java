package com.udinus.bleezesparepart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {
    EditText etNama, etHp, etEmail, etPass;
    FirebaseDatabase database;
    DatabaseReference reff;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etNama = findViewById(R.id.et_txt_nama);
        etHp = findViewById(R.id.et_txt_nohp);
        etEmail = findViewById(R.id.et_txt_email);
        etPass = findViewById(R.id.et_txt_pass);

        database = FirebaseDatabase.getInstance("https://bleezesparepart-default-rtdb.firebaseio.com/");
    }

    public void clickLogin(View view) {
        Intent i = new Intent(Register.this, Login.class);
        startActivity(i);
    }
    
    public void clickRegister(View view) {
        String email = etEmail.getText().toString();
        String password = etPass.getText().toString();
        String nohp = etHp.getText().toString();
        String nama = etNama.getText().toString();
        if(TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "email kosong!", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "password kosong!", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(nohp)){
            Toast.makeText(getApplicationContext(), "no hp kosong!", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(nama)) {
            Toast.makeText(getApplicationContext(), "nama kosong!", Toast.LENGTH_SHORT).show();
        } else {
            reff = database.getReference("akun");
            reff.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int index = 0;
                    for(DataSnapshot ds: dataSnapshot.getChildren()) {
                        if(ds.child("email").getValue(String.class).equals(email)) {
                            Toast.makeText(getApplicationContext(), "email sudah terdaftar!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        index++;
                    }
                    reff.child(String.valueOf(index)).child("email").setValue(email);
                    reff.child(String.valueOf(index)).child("password").setValue(password);
                    reff.child(String.valueOf(index)).child("nama").setValue(nama);
                    reff.child(String.valueOf(index)).child("nohp").setValue(nohp);

                    Intent i = new Intent(Register.this, Login.class);
                    startActivity(i);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}