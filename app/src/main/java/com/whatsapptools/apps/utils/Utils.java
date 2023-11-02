package com.whatsapptools.apps.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.whatsapptools.apps.models.Font;

import java.util.ArrayList;
import java.util.HashSet;

public class Utils {
    private static SharedPreferences sharedPreferences;

    private static void initSharedPrefs(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        }
    }

    public static int addRemoveFromFavourites(Context context, Font font) {
        initSharedPrefs(context);
        String str = "favourites";
        HashSet<String> hashSet = new HashSet<>(sharedPreferences.getStringSet(str, new HashSet<String>()));
        String str2 = font.fontName;
        if (hashSet.contains(str2)) {
            hashSet.remove(str2);
            sharedPreferences.edit().putStringSet(str, hashSet).apply();
            return 0;
        }
        hashSet.add(str2);
        sharedPreferences.edit().putStringSet(str, hashSet).apply();
        return 1;
    }

    public static ArrayList<String> getFavouriteFonts() {
        return new ArrayList<>(new HashSet<>(sharedPreferences.getStringSet("favourites", new HashSet<String>())));
    }

    public static boolean isFavourite(Context context, Font font) {
        initSharedPrefs(context);
        return new HashSet<>(sharedPreferences.getStringSet("favourites", new HashSet<String>())).contains(font.fontName);
    }
}
