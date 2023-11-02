package com.whatsapptools.apps.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rawderm.whatsapptools.R;
import com.whatsapptools.apps.adapter.ImageAdapter;
import com.whatsapptools.apps.utils.Config;
import com.whatsapptools.apps.utils.PrefState;

import java.io.File;
import java.util.ArrayList;
import java.util.List;




public class ImagesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    ImageAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout layout;
    ImageView imageView;
    TextView textView;
    private RecyclerView recyclerView;
    private List<File> list;
     String path, state;
     File dir;
     File[] files;

    public ImagesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_images, container, false);
        state = PrefState.getInstance(getContext()).getWhatsAppState();
        if (TextUtils.isEmpty(state)) {
            path = Config.WhatsAppDirectoryPath;
        } else {
            path = state;
        }
        recyclerView = view.findViewById(R.id.fragment_images_recycler_view);
        swipeRefreshLayout = view.findViewById(R.id.fragment_images_swipe_refresh);
        imageView = view.findViewById(R.id.fragment_images_image_view);
        textView = view.findViewById(R.id.fragment_images_text_view);
        layout = view.findViewById(R.id.fragment_images_linear_layout);
        list = new ArrayList<>();
        adapter = new ImageAdapter(getContext(), list);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadImage();
    }

    private void loadImage() {
        list.clear();
        swipeRefreshLayout.setRefreshing(true);
        dir = new File(path);
        if (dir.exists()) {
            layout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            files = dir.listFiles();
            if (files.length != 0) {
                for (File file : files) {
                    if (file.getName().endsWith(".jpg") || file.getName().endsWith(".png") || file.getName().endsWith(".gif") || file.getName().endsWith(".jpeg"))
                        list.add(file);
                }
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            } else {
                layout.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                displayError(getContext(), R.drawable.new_404_2, "OOPS!\nNo pictures available, check some status on " + getName() + " and come back.");
            }
        } else {
            layout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            displayError(getContext(), R.drawable.new_404, "OOPS!\n" + getName() + " Not Installed.");
        }
    }

    private void displayError(Context context, int res, String s) {
        Glide.with(context).load(res).into(imageView);
        textView.setText(s);
    }

    @Override
    public void onRefresh() {
        loadImage();
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
