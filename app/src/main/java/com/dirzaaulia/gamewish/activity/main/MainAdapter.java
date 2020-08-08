package com.dirzaaulia.gamewish.activity.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dirzaaulia.gamewish.R;
import com.dirzaaulia.gamewish.model.Wishlist;
import com.google.android.material.card.MaterialCardView;

import java.text.NumberFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.RecyclerViewAdapter> {

    private Context context;
    private List<Wishlist> wishlistList;
    private ItemClickListener itemClickListener;
    private int id = 1;

    MainAdapter(Context context, List<Wishlist> wishlistList, ItemClickListener itemClickListener) {
        this.context = context;
        this.wishlistList = wishlistList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_wishlist, parent, false);
        return new RecyclerViewAdapter(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter holder, int position) {

        Wishlist wishlist = wishlistList.get(position);

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

        if (wishlist.getPre_order().equals("Ya")){
            String pre_order = "Pre-Order : Rilis " + wishlist.getTanggal_rilis();
            holder.textViewPreOrder.setText(pre_order);
        } else {
            holder.textViewPreOrder.setVisibility(View.GONE);
        }

        holder.textViewID.setText(String.valueOf(id));
        holder.textViewNama.setText(wishlist.getNama());
        holder.textViewHarga.setText(numberFormat.format(Integer.parseInt(wishlist.getHarga())));
        holder.textViewToko.setText(wishlist.getToko());
        id = id + 1;
    }

    @Override
    public int getItemCount() {
        return wishlistList.size();
    }

    static class RecyclerViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewID, textViewNama, textViewHarga, textViewToko, textViewPreOrder;
        MaterialCardView materialCardView;
        ItemClickListener itemClickListener;

        RecyclerViewAdapter(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            textViewID = itemView.findViewById(R.id.id_wishlist);
            textViewNama = itemView.findViewById(R.id.nama_game);
            textViewHarga = itemView.findViewById(R.id.harga_game);
            textViewToko = itemView.findViewById(R.id.toko);
            textViewPreOrder = itemView.findViewById(R.id.pre_order);
            materialCardView = itemView.findViewById(R.id.card_wishlist);

            this.itemClickListener = itemClickListener;
            materialCardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

