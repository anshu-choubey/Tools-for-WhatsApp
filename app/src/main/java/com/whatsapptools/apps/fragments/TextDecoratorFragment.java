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
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.whatsapptools.apps.R;
import com.whatsapptools.apps.adapter.DecoratorAdapter;
import com.whatsapptools.apps.models.Font;
import com.whatsapptools.apps.utils.Constants;
import com.whatsapptools.apps.utils.CustomBottomSheet;

import java.util.ArrayList;
import java.util.Objects;

public class TextDecoratorFragment extends Fragment {
    public ArrayList<Font> decorList;
    EditText etText;
    private RecyclerView rvDecorText;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_stylish_font, viewGroup, false);
        etText = inflate.findViewById(R.id.et_font);
        rvDecorText = inflate.findViewById(R.id.rv_stylish_text);
        decorList = new ArrayList<>();
        makeDecorOf(" MadeInText.com ");
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
                TextDecoratorFragment.this.makeDecorOf(charSequence);
            }
        });


        return inflate;
    }


    private void makeDecorOf(CharSequence charSequence) {
        this.decorList.clear();
        int i = 0;
        while (i < Constants.txtDecorStart.length) {
            Font font = new Font();
            StringBuilder sb = new StringBuilder();
            sb.append("Decoration ");
            int i2 = i + 1;
            sb.append(i2);
            font.fontName = sb.toString();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(Constants.txtDecorStart[i]);
            sb2.append(charSequence.toString().trim());
            sb2.append(Constants.txtDecorEnd[i]);
            font.fontText = sb2.toString();
            this.decorList.add(font);
            i = i2;
        }
        this.rvDecorText.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        this.rvDecorText.setAdapter(new DecoratorAdapter(getContext(),decorList, new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                new CustomBottomSheet(((Font) decorList.get(i)).fontText).show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "customBottomSheetDialog");
            }
        }));
    }
}
