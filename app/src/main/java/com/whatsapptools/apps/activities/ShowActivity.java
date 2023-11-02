package com.whatsapptools.apps.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.rawderm.whatsapptools.R;
import com.whatsapptools.apps.utils.TextRepeatFragment;
import com.whatsapptools.apps.fragments.BlackMsgFragment;
import com.whatsapptools.apps.fragments.DirectChatFragment;
import com.whatsapptools.apps.fragments.EmojiFragment;
import com.whatsapptools.apps.fragments.TextDecoratorFragment;

public class ShowActivity extends AppCompatActivity {
    String KEY = "Home";
    MaterialToolbar materialToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        materialToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(materialToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        int itemid = getIntent().getIntExtra(KEY, 1);
        if (itemid == 3) {
            getSupportActionBar().setTitle("WhatsApp Direct Chat");
            loadFragment(new DirectChatFragment());
        }
        if (itemid == 5) {
            getSupportActionBar().setTitle("Decoration Text");
            loadFragment(new TextDecoratorFragment());
        }
        if (itemid == 7) {
            getSupportActionBar().setTitle("Text Repeater");
            loadFragment(new TextRepeatFragment());
        }
        if (itemid == 8) {
            getSupportActionBar().setTitle("Empty Text");
            loadFragment(new BlackMsgFragment());
        }
        if (itemid == 9) {
            getSupportActionBar().setTitle("Text to Emoji");
            loadFragment(new EmojiFragment());
        }
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.showFragment, fragment)
                .commit();

    }
}