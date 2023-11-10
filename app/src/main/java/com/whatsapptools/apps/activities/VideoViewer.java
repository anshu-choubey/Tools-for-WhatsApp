package com.whatsapptools.apps.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.whatsapptools.apps.R;
import com.whatsapptools.apps.utils.Config;
import com.whatsapptools.apps.utils.ShareFile;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;


public class VideoViewer extends AppCompatActivity {

    FloatingActionButton btnShare, btnSave;
    VideoView videoView;
    String video, args;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_viewer);
        videoView = findViewById(R.id.videos_viewer_video);
        btnSave = findViewById(R.id.videos_viewer_download);
        btnShare = findViewById(R.id.videos_viewer_share);
        video = getIntent().getStringExtra("video");
        args = getIntent().getStringExtra("args");

        assert args != null;
        if (args.equalsIgnoreCase("gallery")) {
            btnSave.setVisibility(View.GONE);
        }
        position = 0;
        videoView.setVideoPath(video);
        videoView.start();

        MediaController controller = new MediaController(this);
        videoView.setMediaController(controller);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoView.seekTo(position);
                if (position == 0) {
                    videoView.start();
                } else {
                    videoView.pause();
                }
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareFile.getInstance(VideoViewer.this).share("video/*", video);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File src = new File(video);
                File dst = new File(Config.WhatsAppSaveStatus);
                try {
                    //android.os.FileUtils.copy(new FileInputStream(dst), new FileOutputStream(src));
                    FileUtils.copyFileToDirectory(src, dst);
                    Toast.makeText(VideoViewer.this, "Video Saved", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(VideoViewer.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("position", videoView.getCurrentPosition());
        videoView.pause();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        position = savedInstanceState.getInt("position");
        videoView.seekTo(position);
        videoView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        videoView.stopPlayback();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.seekTo(position);
        if (position == 0) {
            videoView.start();
        } else {
            videoView.resume();
        }
    }
}
