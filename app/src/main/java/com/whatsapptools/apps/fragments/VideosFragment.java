package com.whatsapptools.apps.fragments;


import android.content.Context;
import android.content.UriPermission;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.whatsapptools.apps.R;
import com.whatsapptools.apps.adapter.VideoAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class VideosFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    String path, name;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<DocumentFile> list;
    private VideoAdapter adapter;
    DocumentFile[] files;
    private ImageView imageView;
    private TextView textView;
    private LinearLayout layout;

    public VideosFragment(String name, String path) {
        this.name = name;
        this.path = path;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_videos, container, false);
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

        DocumentFile dir;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            List<UriPermission> uriList = requireActivity().getContentResolver().getPersistedUriPermissions();
            if (uriList.size() < 1)
                return;

            dir = DocumentFile.fromTreeUri(requireActivity(), uriList.get(0).getUri());
        } else {
            dir = DocumentFile.fromFile(new File(path));
        }

        if (dir == null) {
            Toast.makeText(requireContext(), "Some Error Occurred", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dir.exists()) {
            layout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            files = dir.listFiles();
            if (files.length != 0) {
                for (DocumentFile file : files) {
                    if (Objects.requireNonNull(file.getName()).endsWith(".mp4") || file.getName().endsWith(".wbem"))
                        list.add(file);
                }
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            } else {
                layout.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                displayError(getContext(), R.drawable.new_404, "OOPS!\nNo videos available, check some status on " + name + " and come back.");
            }
        } else {
            layout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
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

}
