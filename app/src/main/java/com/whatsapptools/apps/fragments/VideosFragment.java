package com.whatsapptools.apps.fragments;


import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.whatsapptools.apps.R;
import com.whatsapptools.apps.adapter.VideoAdapter;
import com.whatsapptools.apps.utils.Config;
import com.whatsapptools.apps.utils.PrefState;

import java.io.File;
import java.util.ArrayList;
import java.util.List;




public class VideosFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    String path, state;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<File> list;
    private VideoAdapter adapter;
    File dir;
    File[] files;
    private ImageView imageView;
    private TextView textView;
    private LinearLayout layout;

    public VideosFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_videos, container, false);
        state = PrefState.getInstance(getContext()).getWhatsAppState();
        if (TextUtils.isEmpty(state)) {
            path = Config.WhatsAppDirectoryPath;
        } else {
            path = state;
        }
        recyclerView = view.findViewById(R.id.fragment_videos_recycler_view);
        swipeRefreshLayout = view.findViewById(R.id.fragment_videos_swipe_refresh);
        layout = view.findViewById(R.id.fragment_videos_linear_layout);
        imageView = view.findViewById(R.id.fragment_videos_image_view);
        textView = view.findViewById(R.id.fragment_videos_text_view);
        list = new ArrayList<>();
        adapter = new VideoAdapter(getContext(), list);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadVideos();
    }

    private void loadVideos() {
        list.clear();
        swipeRefreshLayout.setRefreshing(true);
        dir = new File(path);
        if (dir.exists()) {
            layout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            files = dir.listFiles();
            if (files.length != 0) {
                for (File file : files) {
                    if (!file.getName().endsWith(".jpg") && !file.getName().endsWith(".png") && !file.getName().endsWith(".gif") && !file.getName().endsWith(".jpeg"))
                        list.add(file);
                }
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            } else {
                // Toast.makeText(getContext(), "No files found", Toast.LENGTH_SHORT).show();
                layout.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                displayError(getContext(), R.drawable.new_404, "OOPS!\nNo videos available, check some status on " + getName() + " and come back.");
            }
        } else {
            layout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            displayError(getContext(), R.drawable.new_404_2, "OOPS!\n" + getName() + " Not Installed.");
            // Toast.makeText(getContext(), "Install WhatsApp", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayError(Context context, int res, String s) {
        Glide.with(context).load(res).into(imageView);
        textView.setText(s);
    }

    @Override
    public void onRefresh() {
        loadVideos();
    }

    private String getName() {
        if (PrefState.getInstance(getContext()).getWhatsAppState().equals(Config.WhatsAppDirectoryPath))
            return "WhatsApp";
        else if (PrefState.getInstance(getContext()).getWhatsAppState().equals(Config.GBWhatsAppDirectoryPath))
            return "GB WhatsApp";
        else
            return "WhatsApp Business";
    }

}
