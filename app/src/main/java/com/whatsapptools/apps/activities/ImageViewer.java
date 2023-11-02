package com.whatsapptools.apps.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rawderm.whatsapptools.R;
import com.whatsapptools.apps.adapter.PicturePager;
import com.whatsapptools.apps.utils.Config;
import com.whatsapptools.apps.utils.FileConfig;
import com.whatsapptools.apps.utils.ShareFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class ImageViewer extends AppCompatActivity {

    FloatingActionButton btnShare, btnSave;
    int position;
    LinearLayout layout;
    ViewPager viewPager;
    PicturePager pager;
    ArrayList<String> file;
    String args;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_viewer);
        position = getIntent().getIntExtra("position", 0);
        file = getIntent().getStringArrayListExtra("files");
        args = getIntent().getStringExtra("args");
        btnShare = findViewById(R.id.images_viewer_share);
        btnSave = findViewById(R.id.images_viewer_download);
        layout = findViewById(R.id.images_viewer_layout);
        viewPager = findViewById(R.id.images_viewer_view_pager);
        pager = new PicturePager(this, file);
        viewPager.setAdapter(pager);
        viewPager.setCurrentItem(position);
        if (args.equalsIgnoreCase("gallery")) {
            btnSave.setVisibility(View.GONE);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pic = file.get(viewPager.getCurrentItem());
                File src = new File(pic);
                File dst = new File(Config.WhatsAppSaveStatus);
                try {
                    FileConfig.getInstance(ImageViewer.this).saveFile(src, dst);
                    Toast.makeText(ImageViewer.this, "Picture Saved", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(ImageViewer.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pic = file.get(viewPager.getCurrentItem());
                ShareFile.getInstance(ImageViewer.this).share("image/*", pic);
            }
        });
    }
}
