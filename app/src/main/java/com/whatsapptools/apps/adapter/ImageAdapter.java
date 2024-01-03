package com.whatsapptools.apps.adapter;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
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

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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
        holder.btnSave.setOnClickListener(view -> {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.MediaColumns.DISPLAY_NAME, file.getName());
                    values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + Config.WhatsAppSaveStatus);
                    values.put(MediaStore.MediaColumns.MIME_TYPE, "image/*");

                    Uri destinationUri = Objects.requireNonNull(context.getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values));
                    InputStream inputStream = Objects.requireNonNull(context.getContentResolver().openInputStream(file.getUri()));
                    OutputStream outputStream = context.getContentResolver().openOutputStream(destinationUri);
                    IOUtils.copy(inputStream, outputStream);
                } else {
                    File src = new File(file.getUri().toString());
                    File dst = new File(Config.WhatsAppSaveStatus);
                    FileConfig.getInstance(context).saveFile(src, dst);
                }
            } catch (IOException e) {
                Toast.makeText(context, "Exception while Saving", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnShare.setOnClickListener(view -> ShareFile.getInstance(context).share("image/*", file.getUri().toString()));

        holder.layout.setOnClickListener(view -> {
            ArrayList<String> files = new ArrayList<>();
            for (DocumentFile pic : list) {
                files.add(pic.getUri().toString());
            }
            Intent intent = new Intent(context, ImageViewer.class);
            intent.putStringArrayListExtra("files", files);
            intent.putExtra("position", position);
            intent.putExtra("args", "image");
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
