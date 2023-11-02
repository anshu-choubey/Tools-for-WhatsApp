package com.whatsapptools.apps.utils;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


import static android.content.Context.CLIPBOARD_SERVICE;

public class CopyHandler {
    private Context context;

    public CopyHandler(Context context) {
        this.context = context;

    }

    public void copy(String data){
        if (data.isEmpty()) {
            Toast.makeText(context, "Enter some text", Toast.LENGTH_LONG).show();
            return;
        }
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        Toast.makeText(context, "Copied to clipboard! Your copied text is " + data, Toast.LENGTH_SHORT).show();
        ClipData clip = ClipData.newPlainText("simple text", data);
        if (clipboardManager != null) {
            clipboardManager.setPrimaryClip(clip);
        }

    }
    public void Share(String data)
    {
        if (data.isEmpty()) {
            Toast.makeText(context, "Enter some text", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_TEXT, data);
            context.startActivity(Intent.createChooser(i, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }


    }


    public void copysi(String data){
        if (data.isEmpty()) {
            Toast.makeText(context, "Enter some text", Toast.LENGTH_LONG).show();
            return;
        }
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        Toast.makeText(context, "Copied to clipboard!", Toast.LENGTH_SHORT).show();
        ClipData clip = ClipData.newPlainText("simple text", data);
        if (clipboardManager != null) {
            clipboardManager.setPrimaryClip(clip);
        }


}}
