package com.udinus.bleezesparepart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference reff;
    private RecyclerView rvSparePart;
    private Adapter adapter;
    private ArrayList<SparePart> listSparePart = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        database = FirebaseDatabase.getInstance("https://bleezesparepart-default-rtdb.firebaseio.com/");

        rvSparePart = findViewById(R.id.rv_sparepart);
        rvSparePart.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false);
        rvSparePart.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this, listSparePart);
        rvSparePart.setAdapter(adapter);
        adapter.setOnItemClickCallback(new Adapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(SparePart sparePart) {
                Intent i = new Intent(Home.this, Detail.class);
                i.putExtra("NAMA", sparePart.getNama());
                i.putExtra("GAMBAR", sparePart.getGambar());
                i.putExtra("DESKRIPSI", sparePart.getDeskripsi());
                i.putExtra("HARGA", String.valueOf(sparePart.getHarga()));
                startActivity(i);
            }
        });

        reff = database.getReference("produk");
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listSparePart.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    SparePart sparePart = ds.getValue(SparePart.class);
                    sparePart.setId(Integer.parseInt(ds.getKey()));
                    listSparePart.add(sparePart);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void clickLogout(View view) {
        Intent i = new Intent(Home.this, Login.class);
        startActivity(i);
    }
}