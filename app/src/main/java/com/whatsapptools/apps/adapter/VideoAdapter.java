package com.whatsapptools.apps.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.documentfile.provider.DocumentFile;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.whatsapptools.apps.R;
import com.whatsapptools.apps.activities.VideoViewer;
import com.whatsapptools.apps.utils.Config;
import com.whatsapptools.apps.utils.FileConfig;
import com.whatsapptools.apps.utils.ShareFile;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.viewHolder> {
    Context context;
    List<DocumentFile> list;

    public VideoAdapter(Context context, List<DocumentFile> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.videos_view, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, int position) {
        final DocumentFile file = list.get(position);
        holder.setImg(file.getUri().toString());
        holder.btnShare.setOnClickListener(view -> ShareFile.getInstance(context).share("video/*", file.getUri().toString()));
        holder.btnSave.setOnClickListener(view -> {
            File src = new File(file.getUri().toString());
            File dst = new File(Config.WhatsAppSaveStatus);
            try {
                FileConfig.getInstance(context).saveFile(src, dst);
                Toast.makeText(context, "Video Saved", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        holder.layout.setOnClickListener(view -> {
            Intent intent = new Intent(context, VideoViewer.class);
            intent.putExtra("video", file.getUri().toString());
            intent.putExtra("args", "video");
            context.startActivity(intent);
        });

        holder.imgBtn.setOnClickListener(view -> {
            Intent intent = new Intent(context, VideoViewer.class);
            intent.putExtra("video", file.getUri().toString());
            intent.putExtra("args", "video");
            context.startActivity(intent);
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
        ImageButton imgBtn;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.videos_view_layout);
            img = itemView.findViewById(R.id.videos_view_img);
            btnShare = itemView.findViewById(R.id.videos_view_share);
            btnSave = itemView.findViewById(R.id.videos_view_download);
            imgBtn = itemView.findViewById(R.id.videos_btn_play_img);
        }

        public void setImg(String pic) {
            Glide.with(context).load(pic).into(img);
        }
    }
}
