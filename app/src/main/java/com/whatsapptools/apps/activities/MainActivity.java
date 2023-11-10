package com.whatsapptools.apps.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.whatsapptools.apps.R;
import com.skyfishjy.library.RippleBackground;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    RippleBackground rippleBackground;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rippleBackground = findViewById(R.id.content);
        textView = findViewById(R.id.text_main_initialize);
        askForMultiplePermissions();
        textView.setVisibility(View.VISIBLE);
    }

    public boolean hasPermission(String permission) {
        return ContextCompat.checkSelfPermission(getApplicationContext(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void askForMultiplePermissions() {
        String writeExternalStoragePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        String readExternalStoragePermission = Manifest.permission.READ_EXTERNAL_STORAGE;

        List<String> permissionList = new ArrayList<>();

        if (!hasPermission(writeExternalStoragePermission)) {
            permissionList.add(writeExternalStoragePermission);
        }
        if (!hasPermission(readExternalStoragePermission)) {
            permissionList.add(readExternalStoragePermission);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[0]);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
        } else {
            rippleBackground.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this, TabActivity.class));
                    finish();
                }
            }, 5000);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length != 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        rippleBackground.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(MainActivity.this, TabActivity.class));
                                finish();
                            }
                        }, 5000);
                    } else {
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                        askForMultiplePermissions();
                    }
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        rippleBackground.startRippleAnimation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        rippleBackground.stopRippleAnimation();
    }
}
