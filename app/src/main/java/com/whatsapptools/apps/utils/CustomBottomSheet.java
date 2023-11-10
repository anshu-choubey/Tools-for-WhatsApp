package com.whatsapptools.apps.utils;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.whatsapptools.apps.R;

public class CustomBottomSheet extends BottomSheetDialogFragment implements OnClickListener {
    private String styledText;

    public CustomBottomSheet(String str) {
        this.styledText = str;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.bottom_preview, viewGroup, false);
        ImageButton copyBtn = inflate.findViewById(R.id.iv_bottonsheet_copy);
        ImageButton shareBtn = inflate.findViewById(R.id.iv_bottonsheet_share);
        TextView textView = inflate.findViewById(R.id.tv_bottomsheet_stylish_text);
        inflate.findViewById(R.id.iv_bottomsheet_back).setOnClickListener(this);
        copyBtn.setOnClickListener(this);
        shareBtn.setOnClickListener(this);
        textView.setOnClickListener(this);
        textView.setText(styledText);
        return inflate;
    }

    public void onClick(View view) {
        int id = view.getId();
        CopyHandler copyHandler = new CopyHandler(getContext());
        if (id != R.id.tv_bottomsheet_stylish_text) {
            switch (id) {
                case R.id.iv_bottomsheet_back:
                    dismiss();
                    return;
                case R.id.iv_bottonsheet_copy:
                    copyHandler.copy(styledText);
                    dismiss();
                    return;
                case R.id.iv_bottonsheet_share:
                    copyHandler.Share(styledText);
                    dismiss();
                    return;
                default:
            }
        } else {
            copyHandler.copy(styledText);
            dismiss();
        }
    }



}
