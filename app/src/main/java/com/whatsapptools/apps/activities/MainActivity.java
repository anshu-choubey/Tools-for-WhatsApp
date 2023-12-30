package com.whatsapptools.apps.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.skyfishjy.library.RippleBackground;
import com.whatsapptools.apps.R;

public class MainActivity extends AppCompatActivity {
    RippleBackground rippleBackground;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rippleBackground = findViewById(R.id.content);
        textView = findViewById(R.id.text_main_initialize);
        textView.setVisibility(View.VISIBLE);

        rippleBackground.postDelayed(() -> {
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
        }, 2000);
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
