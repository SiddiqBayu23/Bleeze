package com.udinus.bleezesparepart;

import android.os.Parcel;
import android.os.Parcelable;

public class SparePart {
    private String nama, deskripsi, gambar;
    private int id, harga, terjual;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getTerjual() {
        return terjual;
    }

    public void setTerjual(int terjual) {
        this.terjual = terjual;
    }

}
