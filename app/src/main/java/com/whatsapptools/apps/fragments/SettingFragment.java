package com.whatsapptools.apps.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.rawderm.whatsapptools.R;


public class SettingFragment extends Fragment implements View.OnClickListener {

    Activity context;
    LinearLayout theme,mail,rate,policy,about,share;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        theme = view.findViewById(R.id.theme);
        mail = view.findViewById(R.id.mail);
        rate = view.findViewById(R.id.rate);
        policy = view.findViewById(R.id.policy);
        about = view.findViewById(R.id.about);
        share = view.findViewById(R.id.share);

        theme.setOnClickListener(this);
        mail.setOnClickListener(this);
        policy.setOnClickListener(this);
        rate.setOnClickListener(this);
        about.setOnClickListener(this);
        share.setOnClickListener(this);

        return view;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = (Activity) context;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.theme) {
        }
        if (id == R.id.share){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.review) + requireActivity().getPackageName());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
        if (id == R.id.rate){
            rateApp();
        }
        if (id == R.id.about) {
                  showAboutDialog();        }
        if (id == R.id.mail){
            ShareCompat.IntentBuilder.from(context)
                    .setType("message/rfc822")
                    .addEmailTo(getString(R.string.email))
                    .setSubject("Any Queries")
                    .setText("Type Here")
                    .startChooser();
        }
        if (id == R.id.policy){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(getString(R.string.email)));
            startActivity(browserIntent);
        }

    }

    private void showAboutDialog() {
        final Dialog dialog = new Dialog(context, R.style.DialogCustomTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.dialog_about);

        Button dialog_btn=dialog.findViewById(R.id.bt_close);
        Button dialog_btnrate=dialog.findViewById(R.id.bt_rate);

        dialog_btnrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateApp();
            }
        });

        dialog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void rateApp(){
        final String appName = requireActivity().getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id="
                            + appName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id="
                            + appName)));
        }
    }
}