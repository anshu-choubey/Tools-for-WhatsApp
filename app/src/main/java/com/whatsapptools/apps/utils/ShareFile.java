package com.whatsapptools.apps.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.whatsapptools.apps.R;

import java.io.File;


public class ShareFile {
    private static ShareFile instance;
    private String type, path;
    private Context context;

    private ShareFile(Context context) {
        this.context = context;
    }

    public static synchronized ShareFile getInstance(Context context) {
        if (instance == null) {
            instance = new ShareFile(context);
        }
        return instance;
    }

    public void share(String type, String path) {
        this.type = type;
        this.path = path;
        shareVia();
    }

    private void shareVia() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(type);
        File file = new File(path);
        Uri uri = Uri.fromFile(file);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(Intent.createChooser(intent, "Share via"));
    }

    public void shareApp() {
        String text = context.getString(R.string.app_name);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "WhatsSave");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(intent, "Share WhatsSave"));

    }
}
