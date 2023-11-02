package com.whatsapptools.apps.utils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.textview.MaterialTextView;
import com.rawderm.whatsapptools.R;

import java.util.Objects;


public class TextRepeatFragment extends Fragment {
    String Maintext;
    int NoofRepeat;
    String RepeatText;
    ImageButton clearTxtBtn;
    Button convertButton;
    MaterialTextView convertedText;
    ImageButton btnCopy;
    EditText emojeeText;
    SwitchCompat imNewLine;
    EditText txtInput;
    boolean isNewLine = false;
    String no;
    ProgressDialog pDialog;
    ImageButton btnShare;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_text_repeat, container, false);
        pDialog = new ProgressDialog(getContext());
        imNewLine = view.findViewById(R.id.btnNewLine);
        if (isNewLine) {
            imNewLine.setChecked(true);
        } else {
            imNewLine.setChecked(false);
        }
        imNewLine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isNewLine) {
                    isNewLine = false;
                    imNewLine.setChecked(false);
                    return;
                }
                isNewLine = true;
                imNewLine.setChecked(true);
            }
        });
        txtInput = view.findViewById(R.id.inputText);
        emojeeText = view.findViewById(R.id.emojeeTxt);
        convertedText = view.findViewById(R.id.convertedEmojeeTxt);
        convertButton = view.findViewById(R.id.convertEmojeeBtn);
        btnCopy = view.findViewById(R.id.copyTxtBtn);
        btnShare = view.findViewById(R.id.shareTxtBtn);
        clearTxtBtn = view.findViewById(R.id.clearTxtBtn);
        convertButton.setOnClickListener(new btnConverListner());
        clearTxtBtn.setOnClickListener(new btnClearTextListner());
        convertedText.setOnClickListener(new btnConvertedTexListner());
        btnCopy.setOnClickListener(new btnCopyListner());
        btnShare.setOnClickListener(new btnShareListner());
        return view;
    }


    //Click event of Button Convert
    private class btnConverListner implements View.OnClickListener {
        public void onClick(View view) {
           convertedText.setText("");
            RepeatText = txtInput.getText().toString();
           no = emojeeText.getText().toString();
            try {
                NoofRepeat = Integer.parseInt(no);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
            if (txtInput.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Enter Repeat Text", Toast.LENGTH_SHORT).show();
            } else if (emojeeText.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Enter Number of Repeat Text", Toast.LENGTH_SHORT).show();
            } else if (NoofRepeat <= 10000) {
                new CreateRepeateText().execute();
            } else {
                Toast.makeText(getContext(), "Number of Repeter Text Limited Please Enter Limited Number", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Click event of Button Clear
    private class btnClearTextListner implements View.OnClickListener {
        public void onClick(View view) {
            convertedText.setText("");
        }
    }


    //Listener of while converted text
    private class btnConvertedTexListner implements View.OnClickListener {
        public void onClick(View view) {
            if (!convertedText.getText().toString().isEmpty()) {
                ((ClipboardManager) Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSystemService(Context.CLIPBOARD_SERVICE))).setPrimaryClip(ClipData.newPlainText(txtInput.getText().toString(), convertedText.getText().toString()));
                Toast.makeText(getContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Click event of Button Copy
    private class btnCopyListner implements View.OnClickListener {
        public void onClick(View view) {
            if (convertedText.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Convert text before copy", Toast.LENGTH_SHORT).show();
                return;
            }
            ((ClipboardManager) Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSystemService(Context.CLIPBOARD_SERVICE))).setPrimaryClip(ClipData.newPlainText(txtInput.getText().toString(), convertedText.getText().toString()));
            Toast.makeText(getContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();
        }
    }


    //Click event of Button Share
    private class btnShareListner implements View.OnClickListener {
        public void onClick(View view) {
            if (convertedText.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Please Convert text to share", Toast.LENGTH_LONG).show();
                return;
            }
            Intent shareIntent = new Intent();
            shareIntent.setAction("android.intent.action.SEND");
            shareIntent.setPackage("com.whatsapp");
            shareIntent.putExtra("android.intent.extra.TEXT", convertedText.getText().toString());
            shareIntent.setType("text/plain");
            startActivity(Intent.createChooser(shareIntent, "Select an app to share"));
        }
    }

    //Create repeat text in background
    @SuppressLint("StaticFieldLeak")
    private class CreateRepeateText extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Please Wait...");
            pDialog.setProgressStyle(0);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        public String doInBackground(String... strings) {
            int i;
            if (isNewLine) {
                for (i = 1; i <= NoofRepeat; i++) {
                    if (i == 1) {
                       Maintext = RepeatText;
                    } else {
                        Maintext += "\n" + RepeatText;
                    }
                }
            } else {
                for (i = 1; i <= NoofRepeat; i++) {
                    if (i == 1) {
                        Maintext = RepeatText;
                    } else {
                      Maintext += "\t" +RepeatText;
                    }
                }
            }
            return null;
        }

        public void onPostExecute(String result) {
          pDialog.dismiss();
          convertedText.setText(Maintext);
        }
    }



}