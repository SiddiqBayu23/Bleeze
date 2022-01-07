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

public class Reset extends AppCompatActivity {
    EditText etNewPass, etConfirmPass;
    private String email, id_user;
    FirebaseDatabase database;
    DatabaseReference reff;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        Intent r = getIntent();
        email = r.getStringExtra("EMAIL");
        id_user = r.getStringExtra("ID_USER");

        etNewPass = findViewById(R.id.iv_newpass);
        etConfirmPass = findViewById(R.id.iv_conpass);
        database = FirebaseDatabase.getInstance("https://bleezesparepart-default-rtdb.firebaseio.com/");
    }

    public void clickSelesai(View view) {
        String newpass = etNewPass.getText().toString();
        String conpass = etConfirmPass.getText().toString();

        if(TextUtils.isEmpty(newpass)) {
            Toast.makeText(getApplicationContext(), "password kosong!", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(conpass)) {
            Toast.makeText(getApplicationContext(), "password konfirmasi kosong!", Toast.LENGTH_SHORT).show();
        } else if(!newpass.equals(conpass)) {
            Toast.makeText(getApplicationContext(), "password tidak sama!", Toast.LENGTH_SHORT).show();
        } else {
            reff = database.getReference("akun").child(String.valueOf(id_user));
            reff.child("password").setValue(newpass);
            Intent i = new Intent(Reset.this, Login.class);
            startActivity(i);
        }
    }
}