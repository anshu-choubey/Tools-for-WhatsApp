package com.whatsapptools.apps.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.documentfile.provider.DocumentFile;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.whatsapptools.apps.R;
import com.whatsapptools.apps.activities.ImageViewer;
import com.whatsapptools.apps.utils.Config;
import com.whatsapptools.apps.utils.FileConfig;
import com.whatsapptools.apps.utils.ShareFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.viewHolder> {

    List<DocumentFile> list;
    Context context;

    public ImageAdapter(Context context, List<DocumentFile> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.images_view, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final DocumentFile file = list.get(position);
        holder.setImg(file.getUri().toString());
        holder.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File src = new File(file.getUri().toString());
                File dst = new File(Config.WhatsAppSaveStatus);
                try {
                    FileConfig.getInstance(context).saveFile(src, dst);
                } catch (IOException e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareFile.getInstance(context).share("image/*", file.getUri().toString());
            }
        });

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> files = new ArrayList<>();
                for (DocumentFile pic : list) {
                    files.add(pic.getUri().toString());
                }
                Intent intent = new Intent(context, ImageViewer.class);
                intent.putStringArrayListExtra("files", files);
                intent.putExtra("position", position);
                intent.putExtra("args", "image");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {
        RelativeLayout layout;
        ImageView img;
        FloatingActionButton btnShare, btnSave;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.images_view_layout);
            img = itemView.findViewById(R.id.images_view_img);
            btnShare = itemView.findViewById(R.id.images_view_share);
            btnSave = itemView.findViewById(R.id.images_view_download);
        }


        public void setImg(String pic) {
            Glide.with(context).load(pic).into(img);
        }
    }
}
