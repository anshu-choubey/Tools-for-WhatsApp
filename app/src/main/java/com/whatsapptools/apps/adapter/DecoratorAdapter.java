package com.whatsapptools.apps.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.whatsapptools.apps.R;
import com.whatsapptools.apps.models.Font;
import com.whatsapptools.apps.utils.CopyHandler;

import java.util.ArrayList;

public class DecoratorAdapter extends Adapter<DecoratorAdapter.DecoratorHolder> {
    private Context context;
    private ArrayList<Font> dataSet;
    public OnItemClickListener onItemClickListener;

    public DecoratorAdapter(Context context,ArrayList<Font> arrayList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.dataSet = arrayList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    public DecoratorHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new DecoratorHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.decorator_item_layout, viewGroup, false));
    }

    public void onBindViewHolder(final DecoratorHolder decoratorHolder, int i) {
        decoratorHolder.tvStylishText.setText(((Font) this.dataSet.get(decoratorHolder.getAdapterPosition())).fontText);
        decoratorHolder.tvStylishTextName.setText(((Font) this.dataSet.get(decoratorHolder.getAdapterPosition())).fontName);
        final CopyHandler copyHandler = new CopyHandler(context);
        final String data =  decoratorHolder.tvStylishText.getText().toString();
        decoratorHolder.ivShare.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                copyHandler.Share(data);
            }
        });

        decoratorHolder.itemView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                onItemClickListener.onItemClick(null, decoratorHolder.itemView, decoratorHolder.getAdapterPosition(), 0);
            }
        });
        decoratorHolder.ivCopy.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                copyHandler.copy(data);
            }
        });
    }

    public int getItemCount() {
        return this.dataSet.size();
    }

    public static class DecoratorHolder extends ViewHolder {
        ImageButton ivCopy;
        ImageButton ivShare;
        TextView tvStylishText;
        TextView tvStylishTextName;

        public DecoratorHolder(View view) {
            super(view);
            tvStylishTextName = view.findViewById(R.id.tv_style_name);
            tvStylishText = view.findViewById(R.id.tv_stylish_text);
            ivCopy = view.findViewById(R.id.iv_copy);
            ivShare = view.findViewById(R.id.iv_share);
        }
    }


}
