package com.whatsapptools.apps.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.whatsapptools.apps.R;
import com.whatsapptools.apps.adapter.TabAdapter;
import com.whatsapptools.apps.fragments.GalleryFragment;
import com.whatsapptools.apps.fragments.ImagesFragment;
import com.whatsapptools.apps.fragments.VideosFragment;
import com.whatsapptools.apps.utils.Config;

import java.io.File;
import java.util.Objects;


public class TabActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    File dir;
    MaterialToolbar toolbar;
    TabAdapter adapter;
    TabLayout tabLayout;
    ViewPager viewPager;
    String KEY = "Home";

    private ImagesFragment imagesFragment;
    private VideosFragment videosFragment;
    private GalleryFragment galleryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        int itemId = getIntent().getIntExtra(KEY, 1);
        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        adapter = new TabAdapter(getSupportFragmentManager());

        videosFragment = new VideosFragment();
        galleryFragment = new GalleryFragment();

        if (doesNotHavePermissions()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Storage Permission Required");
            builder.setMessage("Please allow storage permission to access the .Statuses folder which is required to show all the WhatsApp Statuses here");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", (dialogInterface, i) -> {
                askForMultiplePermissions();
                dialogInterface.dismiss();
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        if (itemId == 1) {
            Objects.requireNonNull(getSupportActionBar()).setTitle("WhatsApp Status");
            if (isExist(Config.StorageBasePath + Config.WhatsAppNewDirectoryPath)) {
                imagesFragment = new ImagesFragment(Config.StorageBasePath + Config.WhatsAppNewDirectoryPath);
            } else if (isExist(Config.StorageBasePath + Config.WhatsAppDirectoryPath)) {
                imagesFragment = new ImagesFragment(Config.StorageBasePath + Config.WhatsAppDirectoryPath);
            } else {
                viewDialog("WhatsApp Not Installed", "Install WhatsApp, see some status and check back.", false);
                return;
            }
        }
        if (itemId == 0) {
            Objects.requireNonNull(getSupportActionBar()).setTitle("GB WhatsApp Status");
            if (isExist(Config.StorageBasePath + Config.GBWhatsAppNewDirectoryPath)) {
                imagesFragment = new ImagesFragment(Config.StorageBasePath + Config.GBWhatsAppNewDirectoryPath);
            } else if (isExist(Config.StorageBasePath + Config.GBWhatsAppDirectoryPath)) {
                imagesFragment = new ImagesFragment(Config.StorageBasePath + Config.GBWhatsAppDirectoryPath);
            } else {
                viewDialog("GB WhatsApp Not Installed", "Install GB WhatsApp, see some status and check back.", true);
                return;
            }
        }
        if (itemId == 2) {
            Objects.requireNonNull(getSupportActionBar()).setTitle("WhatsApp Business Status");
            if (isExist(Config.StorageBasePath + Config.WhatsAppBusinessNewPath)) {
                imagesFragment = new ImagesFragment(Config.StorageBasePath + Config.WhatsAppBusinessNewPath);
            } else if (isExist(Config.StorageBasePath + Config.WhatsAppBusinessPath)) {
                imagesFragment = new ImagesFragment(Config.StorageBasePath + Config.WhatsAppBusinessPath);
            } else {
                viewDialog("WhatsApp Business Not Installed", "Install WhatsApp Business, see some status and check back.", true);
                return;
            }
        }

        adapter.addFragment(imagesFragment, "Images");
        adapter.addFragment(videosFragment, "Videos");
        adapter.addFragment(galleryFragment, "Gallery");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void viewDialog(String title, String msg, boolean cancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setCancelable(cancelable);
        builder.setPositiveButton("DISMISS", (dialogInterface, i) -> {
            dialogInterface.dismiss();
            finish();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean isExist(String path) {
        dir = new File(path);
        return dir.exists();
    }

    public boolean doesNotHavePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return getContentResolver().getPersistedUriPermissions().size() < 1;
        }

        for (String permissions : PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), permissions) != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    public void askForMultiplePermissions() {
        if (doesNotHavePermissions()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                String[] strings = {Config.WhatsAppNewDirectoryPath, Config.WhatsAppBusinessNewPath, Config.GBWhatsAppNewDirectoryPath, Config.WhatsAppSaveStatus};
                for (String string : strings) {
                    if (isExist(Config.StorageBasePath + string)) {
                        requestPermissionNew(string);
                    }
                }
            } else {
                ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_CODE);
            }
        }
    }

    // https://stackoverflow.com/a/67554693/17178385
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void requestPermissionNew(String directory) {
        StorageManager sm = (StorageManager) this.getSystemService(Context.STORAGE_SERVICE);
        Intent intent = sm.getPrimaryStorageVolume().createOpenDocumentTreeIntent();
        Uri uri = intent.getParcelableExtra("android.provider.extra.INITIAL_URI");
        if (uri != null) {
            String scheme = uri.toString();
            scheme = scheme.replace("/root/", "/document/");
            scheme += Uri.encode(":") + Uri.encode(directory);

            uri = Uri.parse(scheme);

            intent.putExtra("android.provider.extra.INITIAL_URI", uri);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);

            activityResultLauncher.launch(intent);
        }
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    assert data != null;

                    this.getContentResolver().takePersistableUriPermission(
                            Objects.requireNonNull(data.getData()),
                            Intent.FLAG_GRANT_READ_URI_PERMISSION |
                                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                    // No need to refresh as the fragments are loaded only after permissions are given
                }
            }
    );


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    askForMultiplePermissions();
                }
            }
        }
    }

}