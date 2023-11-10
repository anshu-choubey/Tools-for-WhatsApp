package com.whatsapptools.apps.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.whatsapptools.apps.R;
import com.whatsapptools.apps.activities.HomeActivity;
import com.whatsapptools.apps.adapter.TabAdapter;
import com.whatsapptools.apps.fragments.GalleryFragment;
import com.whatsapptools.apps.fragments.ImagesFragment;
import com.whatsapptools.apps.fragments.VideosFragment;
import com.whatsapptools.apps.utils.Config;
import com.whatsapptools.apps.utils.PrefState;

import java.io.File;
import java.util.Objects;


public class TabActivity extends AppCompatActivity {

    File dir;
    MaterialToolbar toolbar;
    TabAdapter adapter;
    TabLayout tabLayout;
    ViewPager viewPager;
    String KEY = "Home";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        int itemid = getIntent().getIntExtra(KEY,1);
        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new ImagesFragment(), "Images");
        adapter.addFragment(new VideosFragment(), "Videos");
        adapter.addFragment(new GalleryFragment(), "Gallery");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        if (itemid==1){
            Objects.requireNonNull(getSupportActionBar()).setTitle("WhatsApp Status");
            if (isExist(Config.WhatsAppDirectoryPath)) {
                if (!Config.WhatsAppDirectoryPath.equals(PrefState.getInstance(this).getWhatsAppState())) {
                    PrefState.getInstance(this).setWhatsAppState(Config.WhatsAppDirectoryPath);
                    finish();
                    startActivity(getIntent());
                }
            } else {
                viewDialog("Install WhatsApp, see some status and check back.");
            }        }
        if (itemid==0){
            Objects.requireNonNull(getSupportActionBar()).setTitle("GB WhatsApp Status");
            if (isExist(Config.GBWhatsAppDirectoryPath)) {
                if (!Config.GBWhatsAppDirectoryPath.equals(PrefState.getInstance(this).getWhatsAppState())) {
                    PrefState.getInstance(this).setWhatsAppState(Config.GBWhatsAppDirectoryPath);
                    finish();
                    startActivity(getIntent());
                }
            } else {
                viewDialog("Install GB WhatsApp, see some status and check back.");
            }    }
        if (itemid==2){
            Objects.requireNonNull(getSupportActionBar()).setTitle("WhatsApp Business Status");
            if (isExist(Config.WhatsAppBusinessPath)) {
                if (!Config.WhatsAppBusinessPath.equals(PrefState.getInstance(this).getWhatsAppState())) {
                    PrefState.getInstance(this).setWhatsAppState(Config.WhatsAppBusinessPath);
                    finish();
                    startActivity(getIntent());
                }
            } else {
                viewDialog("Install WhatsApp Business, see some status and check back.");
            }   }

    }



    private void viewDialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Not Installed");
        builder.setMessage(msg);
        builder.setCancelable(true);
        builder.setPositiveButton("DISMISS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean isExist(String path) {
        dir = new File(path);
        return dir.exists();
    }

}