package com.itesm.healthchain.data;

import android.content.Context;
import android.content.SharedPreferences;
import com.itesm.healthchain.R;

public class SharedPreferencesManager {

    public static void setToken(Context context, String token) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preferences_key), Context.MODE_PRIVATE);
        // TODO: Encrypted shared data, clean up
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.token_key), token);
        editor.apply();
    }

    public static void setRole(Context context, String role) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preferences_key), Context.MODE_PRIVATE);
        // TODO: Encrypted shared data, clean up
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.role_key), role);
        editor.apply();
    }

    public static String getToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                context.getString(R.string.preferences_key), Context.MODE_PRIVATE);
        return preferences.getString(context.getString(R.string.token_key), "");
    }

    public static String getRole(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                context.getString(R.string.preferences_key), Context.MODE_PRIVATE);
        return preferences.getString(context.getString(R.string.role_key), "");
    }
}
