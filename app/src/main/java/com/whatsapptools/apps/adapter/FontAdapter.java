package com.whatsapptools.apps.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.whatsapptools.apps.R;
import com.whatsapptools.apps.models.Font;
import com.whatsapptools.apps.utils.Utils;

import java.util.ArrayList;


public class FontAdapter extends Adapter<FontAdapter.FontHolder> {
    public ArrayList<Font> dataSet;
    public OnItemClickListener favItemClickListener;
    public OnItemClickListener onItemClickListener;
    public boolean removeFavourite;

    public FontAdapter(ArrayList<Font> arrayList, OnItemClickListener onItemClickListener2, boolean z) {
        this.dataSet = arrayList;
        this.onItemClickListener = onItemClickListener2;
        this.removeFavourite = z;
    }
    @NonNull
    public FontHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new FontHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.font_item_layout, viewGroup, false));
    }

    public void onBindViewHolder(final FontHolder fontHolder, int i) {
        fontHolder.tvStylishText.setText(dataSet.get(fontHolder.getAdapterPosition()).fontText);
        fontHolder.tvStylishTextName.setText(dataSet.get(fontHolder.getAdapterPosition()).fontName);
        if (Utils.isFavourite(fontHolder.itemView.getContext(),dataSet.get(fontHolder.getAdapterPosition()))) {
            fontHolder.ivfav.setImageResource(R.drawable.ic_outline_favorite_24);
        } else {
            fontHolder.ivfav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }
        fontHolder.ivShare.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.putExtra("android.intent.extra.TEXT", fontHolder.tvStylishText.getText());
                fontHolder.itemView.getContext().startActivity(intent);
            }
        });
        fontHolder.ivWhatsapp.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                FontAdapter.this.onItemClickListener.onItemClick(null, fontHolder.itemView, fontHolder.getAdapterPosition(), 0);
            }
        });
        fontHolder.ivfav.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (Utils.addRemoveFromFavourites(fontHolder.itemView.getContext(), dataSet.get(fontHolder.getAdapterPosition())) != 0 || !removeFavourite) {
                    FontAdapter.this.notifyItemChanged(fontHolder.getAdapterPosition());
                } else {
                    FontAdapter.this.dataSet.remove(fontHolder.getAdapterPosition());
                    FontAdapter.this.notifyDataSetChanged();
                }
                if (FontAdapter.this.favItemClickListener != null) {
                    FontAdapter.this.favItemClickListener.onItemClick(null, fontHolder.ivfav, fontHolder.getAdapterPosition(), 0);
                }
            }
        });
        fontHolder.itemView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                FontAdapter.this.onItemClickListener.onItemClick(null, fontHolder.itemView, fontHolder.getAdapterPosition(), 0);
            }
        });
    }

    public int getItemCount() {
        return this.dataSet.size();
    }

    public void setFavItemClickListener(OnItemClickListener onItemClickListener2) {
        this.favItemClickListener = onItemClickListener2;
    }

    public static class FontHolder extends ViewHolder {
        ImageView ivShare;
        ImageView ivWhatsapp;
        ImageView ivfav;
        TextView tvStylishText;

        TextView tvStylishTextName;
        public FontHolder(View view) {
            super(view);
            tvStylishTextName = view.findViewById(R.id.tv_style_name);
            tvStylishText = view.findViewById(R.id.tv_stylish_text);
            ivWhatsapp = view.findViewById(R.id.iv_whatsapp);
            ivfav = view.findViewById(R.id.iv_fav);
            ivShare = view.findViewById(R.id.iv_share);
        }

    }
}
