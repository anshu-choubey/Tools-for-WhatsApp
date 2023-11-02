package com.whatsapptools.apps.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefState {
    private static PrefState instance;
    private Context context;
    private SharedPreferences pref;

    private PrefState(Context context) {
        this.context = context;
        setPref();
    }

    public static synchronized PrefState getInstance(Context context) {
        if (instance == null)
            instance = new PrefState(context);
        return instance;
    }

    private void setPref() {
        pref = context.getSharedPreferences("whatsapp", Context.MODE_PRIVATE);
    }

    private SharedPreferences getPref() {
        return pref;
    }

    public String getWhatsAppState() {
        return getPref().getString("state", "");
    }

    public void setWhatsAppState(String state) {
        SharedPreferences.Editor editor = getPref().edit();
        editor.putString("state", state);
        editor.apply();
    }
}
