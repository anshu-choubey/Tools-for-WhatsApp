package com.whatsapptools.apps.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.whatsapptools.apps.R;
import com.whatsapptools.apps.fragments.FavouriteFontFragment;
import com.whatsapptools.apps.fragments.StylishFontFragment;


public class StylishActivity extends AppCompatActivity {
    BottomNavigationView bmvMain;
    MaterialToolbar materialToolbar;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_stylish);
        materialToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(materialToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Stylish Text");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        bmvMain = findViewById(R.id.bnv_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new StylishFontFragment()).commit();
        bmvMain.setOnNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
                switch (menuItem.getItemId()) {
                    case R.id.stylishMenu:
                        beginTransaction.replace(R.id.frame_main, new StylishFontFragment()).commitAllowingStateLoss();
                        return true;
                    case  R.id.favouriteMenu :
                        beginTransaction.replace(R.id.frame_main, new FavouriteFontFragment()).commitAllowingStateLoss();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
}
