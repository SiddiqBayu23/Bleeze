package com.udinus.bleezesparepart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class Detail extends AppCompatActivity {
    TextView tvNama, tvDeskripsi, tvHarga;
    ImageView imgSparepart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvNama = findViewById(R.id.tv_nama);
        tvDeskripsi = findViewById(R.id.tv_deskripsi);
        tvHarga = findViewById(R.id.tv_hargatertulis);
        imgSparepart = findViewById(R.id.img_sparepart);


        Intent r = getIntent();
        String nama = r.getStringExtra("NAMA");
        String gambar = r.getStringExtra("GAMBAR");
        String deskripsi = r.getStringExtra("DESKRIPSI");
        String harga = r.getStringExtra("HARGA");


        tvNama.setText(nama);
        tvDeskripsi.setText(deskripsi);
        tvHarga.setText("Rp "+harga);
        Glide.with(this)
                .load(gambar)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.DATA))
                .into(imgSparepart);

    }

    public void clickBeli(View view) {
        Intent i = new Intent(Detail.this, SuccessOrder.class);
        startActivity(i);
    }
}