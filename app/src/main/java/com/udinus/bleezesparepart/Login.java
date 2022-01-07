package com.udinus.bleezesparepart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    EditText etEmail, etPass;
    FirebaseDatabase database;
    DatabaseReference reff;
    private CheckBox checkRemember;
    private SharedPreferences sharedPrefs;
    private static final String EMAIL_KEY = "key_email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        database = FirebaseDatabase.getInstance("https://bleezesparepart-default-rtdb.firebaseio.com/");
        this.sharedPrefs = this.getSharedPreferences("bleezesparepart_sharedprefs", Context.MODE_PRIVATE);
        this.initComponents();
        this.loadSavedEmail();
    }

    private void initComponents() {
        this.etEmail = this.findViewById(R.id.et_txt_email);
        this.etPass = this.findViewById(R.id.et_txt_pass);
        this.checkRemember = this.findViewById(R.id.check_remember);
    }

    private void saveEmail() {
        SharedPreferences.Editor editor = this.sharedPrefs.edit();

        if (this.checkRemember.isChecked())
            editor.putString(EMAIL_KEY, this.etEmail.getText().toString());
        else
            editor.remove(EMAIL_KEY);

        editor.apply();
    }

    private void loadSavedEmail() {
        String savedEmail =
                this.sharedPrefs.getString(EMAIL_KEY, null);

        if (savedEmail != null) {
            this.etEmail.setText(savedEmail);

            this.checkRemember.setChecked(true);
        }
    }

    public void clickForgot(View view) {
        Intent i = new Intent(Login.this, Forgot.class);
        startActivity(i);
    }

    public void clickLogin(View view) {
        String email = etEmail.getText().toString();
        String password = etPass.getText().toString();
        if(TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "email kosong!", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "password kosong!", Toast.LENGTH_SHORT).show();
        } else {
            reff = database.getReference("akun");
            reff.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            if(ds.child("email").getValue(String.class).equals(email)) {
                                if(ds.child("password").getValue(String.class).equals(password)) {
                                    Intent i = new Intent(Login.this, Home.class);
                                    i.putExtra("ID_USER", ds.getKey());
                                    startActivity(i);
                                    saveEmail();
                                    return;
                                } else {
                                    Toast.makeText(getApplicationContext(), "password salah", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        }
                        Toast.makeText(getApplicationContext(), "akun tidak terdaftar", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "akun tidak terdaftar", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}