package com.udinus.bleezesparepart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ListViewHolder> {
    private ArrayList<SparePart> listSparePart;
    private OnItemClickCallback onItemClickCallback;
    Context context;
    private String id_user;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public Adapter(Context context, ArrayList<SparePart> list) {
        this.listSparePart = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_sparepart, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        SparePart sparePart = listSparePart.get(position);
        String nama = sparePart.getNama();
        String deskripsi = sparePart.getDeskripsi();
        String gambar = sparePart.getGambar();
        int id = sparePart.getId();
        int harga = sparePart.getHarga();
        int terjual = sparePart.getTerjual();


        holder.tvNama.setText(nama);
        Glide.with(context)
                .load(gambar)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.DATA))
                .into(holder.imgSparepart);
        holder.tvDeskripsi.setText(deskripsi);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(listSparePart.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSparePart.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSparepart;
        TextView tvNama, tvDeskripsi;

        public ListViewHolder(View itemView) {
            super(itemView);
            imgSparepart = itemView.findViewById(R.id.img_item);
            tvNama = itemView.findViewById(R.id.tv_item);
            tvDeskripsi = itemView.findViewById(R.id.tv_detail);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(SparePart sparePart);
    }
}
