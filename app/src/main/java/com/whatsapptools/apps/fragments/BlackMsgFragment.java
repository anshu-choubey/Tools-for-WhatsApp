package com.whatsapptools.apps.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.whatsapptools.apps.R;


public class BlackMsgFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    Button sendBtn;
    Button copyBtn;
    String f1997p;
    Spinner spinner;
    CheckBox checkBox;
    int value;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_black_msg, container, false);
        sendBtn = view.findViewById(R.id.sendMessage);
        copyBtn = view.findViewById(R.id.copyMessage);
        checkBox = view.findViewById(R.id.checkBox);
        spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> createFromResource = ArrayAdapter.createFromResource(getContext(), R.array.count, android.R.layout.simple_spinner_item);
        createFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(createFromResource);
        spinner.setOnItemSelectedListener(this);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (m2740b("com.whatsapp")) {
                    m2736a("com.whatsapp");
                } else if (m2740b("com.whatsapp.w4b")) {
                   m2736a("com.whatsapp.w4b");
                } else {
                    Toast.makeText(getContext(), " WhatsApp not installed on your device", Toast.LENGTH_LONG).show();
                }
            }
        });
        copyBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                newLine();
                ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData newPlainText = ClipData.newPlainText("blankMessage",f1997p);
                if (clipboardManager != null) {
                    clipboardManager.setPrimaryClip(newPlainText);
                }
                Toast.makeText(getContext(), "blank message copied", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
    public void m2736a(String str) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.setPackage(str);
        newLine();
        intent.putExtra("android.intent.extra.TEXT", this.f1997p);
        startActivity(Intent.createChooser(intent, "Share With"));
    }
    public boolean m2740b(String str) {
        try {
            getActivity().getPackageManager().getPackageInfo(str, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void newLine() {
        if (checkBox.isChecked()) {
            f1997p = new String(new char[value]).replace("\u0000", "\n" + "   ");
        } else {
            f1997p = new String(new char[value]).replace("\u0000", "   ");
        }
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String obj = parent.getItemAtPosition(position).toString();
        char c = 65535;
        switch (obj.hashCode()) {
            case -1880154585:
                if (obj.equals("2000 Character")) {
                    c = 6;
                    break;
                }
                break;
            case -1374595960:
                if (obj.equals("1000 Character")) {
                    c = 5;
                    break;
                }
                break;
            case -1044501944:
                if (obj.equals("10 Character")) {
                    c = 0;
                    break;
                }
                break;
            case -528171068:
                if (obj.equals("50 Character")) {
                    c = 1;
                    break;
                }
                break;
            case -13442726:
                if (obj.equals("10000 Character")) {
                    c = 8;
                    break;
                }
                break;
            case 455925150:
                if (obj.equals("500 Character")) {
                    c = 4;
                    break;
                }
                break;
            case 898136836:
                if (obj.equals("5000 Character")) {
                    c = 7;
                    break;
                }
                break;
            case 1336134171:
                if (obj.equals("200 Character")) {
                    c = 3;
                    break;
                }
                break;
            case 1629537178:
                if (obj.equals("100 Character")) {
                    c = 2;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                value = 10;
                break;
            case 1:
                value = 50;
                break;
            case 2:
                value = 100;
                break;
            case 3:
                value = 200;
                break;
            case 4:
                value = 500;
                break;
            case 5:
                value = 1000;
                break;
            case 6:
                value = 200;
                break;
            case 7:
                value = 5000;
                break;
            case 8:
                value = 10000;
                break;
        }
        Toast.makeText(getContext(), value + "Character", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}