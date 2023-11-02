package com.whatsapptools.apps.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.google.android.material.textfield.TextInputEditText;
import com.rawderm.whatsapptools.R;
import com.whatsapptools.apps.adapter.FontAdapter;
import com.whatsapptools.apps.models.Font;
import com.whatsapptools.apps.utils.Constants;
import com.whatsapptools.apps.utils.CustomBottomSheet;

import java.util.ArrayList;

public class StylishFontFragment extends Fragment {
    TextInputEditText etText;
    ArrayList<Font> fontList;
    RecyclerView rvStylishFonts;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_stylish_font, viewGroup, false);
        this.etText = inflate.findViewById(R.id.et_font);
        this.rvStylishFonts = inflate.findViewById(R.id.rv_stylish_text);
        ((SimpleItemAnimator) this.rvStylishFonts.getItemAnimator()).setSupportsChangeAnimations(false);
        this.fontList = new ArrayList<>();
        makeStylishOf("MadeInText.com");
        this.etText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.toString().isEmpty()) {
                    charSequence = "Stylish Text";
                }
                StringBuilder sb = new StringBuilder();
                sb.append("onTextChanged: ");
                sb.append(charSequence);
                Log.d("STYLISHFONTLOG", sb.toString());
                StylishFontFragment.this.makeStylishOf(charSequence);
            }
        });

        return inflate;
    }



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
        int i = 0;
        while (i < Constants.styles.length) {
            Font font = new Font();
            StringBuilder sb = new StringBuilder();
            sb.append("");
            int i2 = i + 1;
            sb.append(i2);
            font.fontName = sb.toString();
            font.fontText = strArr[i];
            this.fontList.add(font);
            i = i2;
        }
        this.rvStylishFonts.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL, false));
        this.rvStylishFonts.setAdapter(new FontAdapter(this.fontList, new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                new CustomBottomSheet(StylishFontFragment.this.fontList.get(i).fontText).show(StylishFontFragment.this.getActivity().getSupportFragmentManager(), "customBottomSheetDialog");
            }
        }, false));
    }

}
