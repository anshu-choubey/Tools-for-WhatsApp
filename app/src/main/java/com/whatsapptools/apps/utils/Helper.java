package com.whatsapptools.apps.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

public class Helper {

    //Get current localisation
   public static String getCurrentLocale(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null) {
            return tm.getNetworkCountryIso();
        }
        return null;
    }
}
