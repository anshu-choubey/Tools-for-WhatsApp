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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.whatsapptools.apps.R;
import com.whatsapptools.apps.activities.ImageViewer;
import com.whatsapptools.apps.activities.VideoViewer;
import com.whatsapptools.apps.utils.FileConfig;
import com.whatsapptools.apps.utils.ShareFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryHolder> {

    List<File> list;
    Context context;

    public GalleryAdapter(Context context, List<File> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public GalleryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gallery_views, parent, false);
        return new GalleryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GalleryHolder holder, final int position) {
        final File file = list.get(position);
        holder.setImageView(file.getAbsolutePath());

        if (holder.isImage(file))
            holder.showImgBtn(View.GONE);
        else
            holder.showImgBtn(View.VISIBLE);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.isImage(file)) {
                    ArrayList<String> files = new ArrayList<>();
                    int a = 0, b = 0;
                    for (int i = 0; i < list.size(); i++) {
                        if (holder.isImage(list.get(i))) {
                            files.add(list.get(i).getAbsolutePath());
                            if (file == list.get(i))
                                b = a;
                            a++;
                        }
                    }
                    Intent intent = new Intent(context, ImageViewer.class);
                    intent.putStringArrayListExtra("files", files);
                    intent.putExtra("position", b);
                    intent.putExtra("args", "gallery");
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, VideoViewer.class);
                    intent.putExtra("video", file.getAbsolutePath());
                    intent.putExtra("args", "gallery");
                    context.startActivity(intent);
                }
            }
        });

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(context, VideoViewer.class);
                    intent.putExtra("video", file.getAbsolutePath());
                    intent.putExtra("args", "gallery");
                    context.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.fbShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.isImage(file))
                    ShareFile.getInstance(context).share("image/*", file.getAbsolutePath());
                else
                    ShareFile.getInstance(context).share("video/*", file.getAbsolutePath());
            }
        });

        holder.fbDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileConfig.getInstance(context).deleteFile(file);
                    list.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, list.size());
                    Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class GalleryHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        RelativeLayout layout;
        FloatingActionButton fbShare, fbDelete;
        ImageButton imageButton;

        private GalleryHolder(@NonNull View itemView) {
            super(itemView);
            imageButton = itemView.findViewById(R.id.gallery_btn_play_img);
            imageView = itemView.findViewById(R.id.gallery_view_img);
            layout = itemView.findViewById(R.id.gallery_view_layout);
            fbShare = itemView.findViewById(R.id.gallery_view_share);
            fbDelete = itemView.findViewById(R.id.gallery_view_delete);
        }

        private void setImageView(String img) {
            Glide.with(context).load(img).into(imageView);
        }

        private void showImgBtn(int visibility) {
            imageButton.setVisibility(visibility);
        }

        private boolean isImage(File file) {
            return file.getName().endsWith(".jpg") ||
                    file.getName().endsWith(".png") ||
                    file.getName().endsWith(".gif") ||
                    file.getName().endsWith(".jpeg");
        }
    }
}
