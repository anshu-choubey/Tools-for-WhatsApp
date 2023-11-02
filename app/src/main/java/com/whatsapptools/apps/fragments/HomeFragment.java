package com.whatsapptools.apps.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.rawderm.whatsapptools.R;
import com.whatsapptools.apps.activities.ShowActivity;
import com.whatsapptools.apps.activities.StylishActivity;
import com.whatsapptools.apps.activities.TabActivity;
import com.whatsapptools.apps.activities.WebWhatsAppActivity;


public class HomeFragment extends Fragment implements View.OnClickListener {


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
    Context context;
    LinearLayout item0,item1,item2,item3,item4,item5,item6,item7,item8,item9;
    Intent intent;
    String KEY = "Home";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        item0 = view.findViewById(R.id.item0);
        item1 = view.findViewById(R.id.item1);
        item2 = view.findViewById(R.id.item2);
        item3 = view.findViewById(R.id.item3);
        item4 = view.findViewById(R.id.item4);
        item5 = view.findViewById(R.id.item5);
        item6 = view.findViewById(R.id.item6);
        item7 = view.findViewById(R.id.item7);
        item8 = view.findViewById(R.id.item8);
        item9 = view.findViewById(R.id.item9);





        item0.setOnClickListener(this);
        item1.setOnClickListener(this);
        item2.setOnClickListener(this);
        item3.setOnClickListener(this);
        item4.setOnClickListener(this);
        item5.setOnClickListener(this);
        item6.setOnClickListener(this);
        item7.setOnClickListener(this);
        item8.setOnClickListener(this);
        item9.setOnClickListener(this);










        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == item1){
            intent = new Intent(context, TabActivity.class);
            intent.putExtra(KEY,1);
            startActivity(intent);        }
        if (v == item2){
            intent = new Intent(context,TabActivity.class);
            intent.putExtra(KEY,2);
            startActivity(intent);        }
        if (v == item3){
            intent = new Intent(context, ShowActivity.class);
            intent.putExtra(KEY,3);
            startActivity(intent);        }

        if (v == item0){
            intent = new Intent(context,TabActivity.class);
            intent.putExtra(KEY,0);
            startActivity(intent);       }
        if (v == item4){
            intent = new Intent(context, StylishActivity.class);
            startActivity(intent);         }
        if (v == item5){
            intent = new Intent(context,ShowActivity.class);
            intent.putExtra(KEY,5);
            startActivity(intent);       }
        if (v == item6){
            intent = new Intent(context, WebWhatsAppActivity.class);
            startActivity(intent);         }
        if (v == item7){
            intent = new Intent(context,ShowActivity.class);
            intent.putExtra(KEY,7);
            startActivity(intent);            }
        if (v == item8){
            intent = new Intent(context,ShowActivity.class);
            intent.putExtra(KEY,8);
            startActivity(intent);            }
        if (v == item9){
            intent = new Intent(context,ShowActivity.class);
            intent.putExtra(KEY,9);
            startActivity(intent);            }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}