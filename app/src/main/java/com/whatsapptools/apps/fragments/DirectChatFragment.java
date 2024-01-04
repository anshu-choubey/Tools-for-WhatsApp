package com.whatsapptools.apps.fragments;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.hbb20.CountryCodePicker;
import com.whatsapptools.apps.R;
import com.whatsapptools.apps.utils.Helper;


public class DirectChatFragment extends Fragment {
    EditText messageEditText;
    EditText numberEditText;
    AppCompatButton SendMessage;
    CountryCodePicker CcP;
    Activity activity;
    RadioGroup radioGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_direct_chat, container, false);
        messageEditText = view.findViewById(R.id.msg);
        numberEditText = view.findViewById(R.id.input_text);
        radioGroup = view.findViewById(R.id.whatsRadio);
        CcP = view.findViewById(R.id.ccp);
        SendMessage = view.findViewById(R.id.go);
        CcP.setCountryForNameCode(Helper.getCurrentLocale(requireContext()));
        CcP.setOnCountryChangeListener(new btnCcpListener());
        if (requireActivity().getIntent().getStringExtra("number") != null) {
            numberEditText.setText(activity.getIntent().getStringExtra("number"));
        }

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (R.id.whatsappRadio == checkedId) {
                SendMessage.setOnClickListener(v -> sendMessage("WhatsApp", "com.whatsapp"));
            }
            if (R.id.bwhatsappRadio == checkedId) {
                SendMessage.setOnClickListener(v -> sendMessage("WhatsApp Business","com.whatsapp.w4b"));
            }
        });
        return view;
    }

    private void sendMessage(String whatsAppName, String whatsAppPackage) {
        String message = messageEditText.getText().toString();
        String number = numberEditText.getText().toString();
        String numberWithCountryCode = CcP.getSelectedCountryCode() + number;
        if (message.length() == 0) {
            Toast.makeText(getContext(), "Please enter message", Toast.LENGTH_SHORT).show();
        } else if (number.length() < 7) {
            Toast.makeText(getContext(), "Enter correct phone number", Toast.LENGTH_SHORT).show();
        } else {
            try {
                String str3 = "https://api.whatsapp.com/send?phone=" + numberWithCountryCode + "&text=" + message;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(str3));
                intent.setPackage(whatsAppPackage);
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getContext(), whatsAppName + " not installed", Toast.LENGTH_SHORT).show();
            } catch (Exception e){
                Toast.makeText(getContext(), "Error occurred", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Country Code Picker method for selecting country
    private class btnCcpListener implements CountryCodePicker.OnCountryChangeListener {
        public void onCountrySelected() {
            CcP.setCountryPreference(CcP.getSelectedCountryNameCode());
        }
    }
}