package com.whatsapptools.apps.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import java.net.IDN;


public class DirectChatFragment extends Fragment {

    EditText message;
    EditText number;
    AppCompatButton SendMessage;
    CountryCodePicker CcP;
    SharedPreferences preference;
    Activity activity;
    RadioGroup radioGroup;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_direct_chat, container, false);
        message = view.findViewById(R.id.msg);
        number = view.findViewById(R.id.input_text);
        radioGroup = view.findViewById(R.id.whatsRadio);
        CcP = view.findViewById(R.id.ccp);
        SendMessage = view.findViewById(R.id.go);
        preference = PreferenceManager.getDefaultSharedPreferences(getContext());
        CcP.setCountryForNameCode(Helper.getCurrentLocale(getContext()));
        CcP.setOnCountryChangeListener(new btnCcpListner());
        if (getActivity().getIntent().getStringExtra("number") != null) {
            number.setText(activity.getIntent().getStringExtra("number"));
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (R.id.whatsappRadio == checkedId){
                    SendMessage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String messege = message.getText().toString();
                            String number1 = number.getText().toString();
                            String mainNumber = CcP.getSelectedCountryCode() + number1;
                            if (messege.length() == 0) {
                                Toast.makeText(getContext(), "Please enter message", Toast.LENGTH_SHORT).show();
                            } else if (number1.length() == 0) {
                                Toast.makeText(getContext(), R.string.app_name, Toast.LENGTH_SHORT).show();
                            } else if (number1.length() < 7) {
                                Toast.makeText(getContext(), R.string.app_name, Toast.LENGTH_SHORT).show();
                            } else {
                                messege.length();
                                try {
                                    PackageManager packageManager = getActivity().getPackageManager();
                                    Intent intent = new Intent("android.intent.action.VIEW");
                                    try {
                                        String str3 = "https://api.whatsapp.com/send?phone=" + mainNumber + "&text=" + messege;
                                        intent.setPackage("com.whatsapp");
                                        intent.setData(Uri.parse(str3));
                                        if (intent.resolveActivity(packageManager) != null) {
                                            startActivity(intent);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } catch (Exception e2) {
                                    Toast.makeText(getContext(), "Error/n" + e2.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    });
                }
                if (R.id.bwhatsappRadio ==  checkedId){

                    SendMessage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String messege = message.getText().toString();
                            String number1 = number.getText().toString();
                            String mainNumber = CcP.getSelectedCountryCode() + number1;
                            if (messege.length() == 0) {
                                Toast.makeText(getContext(), "Please enter message", Toast.LENGTH_SHORT).show();
                            } else if (number1.length() == 0) {
                                Toast.makeText(getContext(), R.string.app_name, Toast.LENGTH_SHORT).show();
                            } else if (number1.length() < 7) {
                                Toast.makeText(getContext(), R.string.app_name, Toast.LENGTH_SHORT).show();
                            } else {
                                messege.length();
                                try {
                                    PackageManager packageManager = getActivity().getPackageManager();
                                    Intent intent = new Intent("android.intent.action.VIEW");
                                    try {
                                        String str3 = "https://api.whatsapp.com/send?phone=" + mainNumber + "&text=" + messege;
                                        intent.setPackage("com.whatsapp.w4b");
                                        intent.setData(Uri.parse(str3));
                                        if (intent.resolveActivity(packageManager) != null) {
                                            startActivity(intent);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } catch (Exception e2) {
                                    Toast.makeText(getContext(), "Error/n" + e2.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    });
                }
            }
        });
        return view;
    }
    //Send Message without saving number method
    private class btnSendMessageListner implements View.OnClickListener {
        public void onClick(View v) {
            String messege = message.getText().toString();
            String number1 = number.getText().toString();
            String mainNumber = CcP.getSelectedCountryCode() + number1;
            if (messege.length() == 0) {
                Toast.makeText(activity, "Please enter message", Toast.LENGTH_SHORT).show();
            } else if (number1.length() == 0) {
                Toast.makeText(activity, R.string.app_name, Toast.LENGTH_SHORT).show();
            } else if (number1.length() < 7) {
                Toast.makeText(activity, R.string.app_name, Toast.LENGTH_SHORT).show();
            } else {
                messege.length();
                try {
                    PackageManager packageManager = activity.getPackageManager();
                    Intent intent = new Intent("android.intent.action.VIEW");
                    try {
                        String str3 = "https://api.whatsapp.com/send?phone=" + mainNumber + "&text=" + messege;
                        intent.setPackage("com.whatsapp");
                        intent.setData(Uri.parse(str3));
                        if (intent.resolveActivity(packageManager) != null) {
                           startActivity(intent);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e2) {
                    Toast.makeText(getContext(), "Error/n" + e2.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private class btnSendMessageListnerB implements View.OnClickListener {
        public void onClick(View v) {
            String messege = message.getText().toString();
            String number1 = number.getText().toString();
            String mainNumber = CcP.getSelectedCountryCode() + number1;
            if (messege.length() == 0) {
                Toast.makeText(activity, "Please enter message", Toast.LENGTH_SHORT).show();
            } else if (number1.length() == 0) {
                Toast.makeText(activity, R.string.app_name, Toast.LENGTH_SHORT).show();
            } else if (number1.length() < 7) {
                Toast.makeText(activity, R.string.app_name, Toast.LENGTH_SHORT).show();
            } else {
                messege.length();
                try {
                    PackageManager packageManager = activity.getPackageManager();
                    Intent intent = new Intent("android.intent.action.VIEW");
                    try {
                        String str3 = "https://api.whatsapp.com/send?phone=" + mainNumber + "&text=" + messege;
                        intent.setPackage("com.whatsapp.w4b");
                        intent.setData(Uri.parse(str3));
                        if (intent.resolveActivity(packageManager) != null) {
                            startActivity(intent);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e2) {
                    Toast.makeText(getContext(), "Error/n" + e2.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //Country Code Picker method for selecting country
    private class btnCcpListner implements CountryCodePicker.OnCountryChangeListener {
        public void onCountrySelected() {
            CcP.setCountryPreference(CcP.getSelectedCountryNameCode());
            preference.edit().putString("last_locale",CcP.getSelectedCountryCode()).apply();
        }
    }
}