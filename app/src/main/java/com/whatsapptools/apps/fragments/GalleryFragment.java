package com.whatsapptools.apps.fragments;


import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.whatsapptools.apps.R;
import com.whatsapptools.apps.adapter.GalleryAdapter;
import com.whatsapptools.apps.utils.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class GalleryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    TextView textView;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    List<File> list;
    GalleryAdapter adapter;
    String path;
    File dir;
    File[] files;
    LinearLayout layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        path = Config.StorageBasePath + Environment.DIRECTORY_DOWNLOADS + Config.WhatsAppSaveStatus;
        textView = view.findViewById(R.id.fragment_gallery_text_view);
        swipeRefreshLayout = view.findViewById(R.id.fragment_gallery_swipe_refresh);
        recyclerView = view.findViewById(R.id.fragment_gallery_recycler_view);
        layout = view.findViewById(R.id.fragment_gallery_linear_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        list = new ArrayList<>();
        adapter = new GalleryAdapter(getContext(), list);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        loadData();
        return view;
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    private void loadData() {
        swipeRefreshLayout.setRefreshing(true);
        list.clear();
        adapter.notifyDataSetChanged();
        dir = new File(path);
        if (dir.exists()) {
            layout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            files = dir.listFiles();
            if (files.length != 0) {
                Collections.addAll(list, files);
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            } else {
                layout.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        } else {
            layout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
