package com.whatsapptools.apps.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.google.android.material.textfield.TextInputEditText;
import com.whatsapptools.apps.R;
import com.whatsapptools.apps.adapter.FontAdapter;
import com.whatsapptools.apps.models.Font;
import com.whatsapptools.apps.utils.Constants;
import com.whatsapptools.apps.utils.CustomBottomSheet;
import com.whatsapptools.apps.utils.Utils;

import java.util.ArrayList;
import java.util.Objects;

public class FavouriteFontFragment extends Fragment {
    TextInputEditText etText;
    /* access modifiers changed from: private */
    public ArrayList<String> favouriteFontList;
    /* access modifiers changed from: private */
    public ArrayList<Font> fontList;
    /* access modifiers changed from: private */
    private RecyclerView rvStylishFonts;
    /* access modifiers changed from: private */
    public TextView tvError;
    Context context;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_stylish_font, viewGroup, false);
        etText = inflate.findViewById(R.id.et_font);
        tvError = (TextView) inflate.findViewById(R.id.tv_error);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.rv_stylish_text);
        rvStylishFonts = recyclerView;
        ((SimpleItemAnimator) Objects.requireNonNull(recyclerView.getItemAnimator())).setSupportsChangeAnimations(false);
        fontList = new ArrayList<>();


        ArrayList<String> favouriteFonts = Utils.getFavouriteFonts();

        favouriteFontList = favouriteFonts;
        if (favouriteFonts.size() == 0) {
           tvError.setVisibility(View.VISIBLE);
        }
        makeStylishOf("MadeInText.com");
        this.etText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.toString().isEmpty()) {
                    charSequence = "MadeInText.com";
                }
                StringBuilder sb = new StringBuilder();
                sb.append("onTextChanged: ");
                sb.append(charSequence);
                Log.d("STYLISHFONTLOG", sb.toString());
                FavouriteFontFragment.this.makeStylishOf(charSequence);
            }
        });


        return inflate;
    }




    /* access modifiers changed from: private */
    public void makeStylishOf(CharSequence charSequence) {
        char[] charArray = charSequence.toString().toLowerCase().toCharArray();
        String[] strArr = new String[Constants.styles.length];
        for (int i = 0; i < Constants.styles.length; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("applyStyle: num ");
            sb.append(i);
            Log.d("STYLISHFONTLOG", sb.toString());
            strArr[i] = applyStyle(charArray, Constants.styles[i]);
        }
        styleTheFont(strArr);
    }

    private String applyStyle(char[] cArr, String[] strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < cArr.length; i++) {
            if (cArr[i] - 'a' < 0 || cArr[i] - 'a' > 25) {
                stringBuffer.append(cArr[i]);
            } else {
                stringBuffer.append(strArr[cArr[i] - 'a']);
            }
        }
        return stringBuffer.toString();
    }

    private void styleTheFont(String[] strArr) {
        this.fontList.clear();
        for (int i = 0; i < this.favouriteFontList.size(); i++) {
            Font font = new Font();
            font.fontName = (String) this.favouriteFontList.get(i);
            font.fontText = strArr[Integer.parseInt(font.fontName) - 1];
            this.fontList.add(font);
        }
        this.rvStylishFonts.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL, false));
        FontAdapter fontAdapter = new FontAdapter(this.fontList, new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                new CustomBottomSheet(((Font) FavouriteFontFragment.this.fontList.get(i)).fontText).show(FavouriteFontFragment.this.getActivity().getSupportFragmentManager(), "customBottomSheetDialog");
            }
        }, true);
        fontAdapter.setFavItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                FavouriteFontFragment.this.favouriteFontList = Utils.getFavouriteFonts();
                if (FavouriteFontFragment.this.fontList.size() == 0) {
                    FavouriteFontFragment.this.tvError.setVisibility(View.VISIBLE);
                }
            }
        });
        this.rvStylishFonts.setAdapter(fontAdapter);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
