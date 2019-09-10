package com.sdsmdg.game.GameWorld;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsForPlauerbord {
    private static String data = "datapalyer";
    private SharedPreferences preferences;

    public PrefsForPlauerbord(Context context){
        String NAME = "dataplayer";
        preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public void setData(String data){
        preferences.edit().putString(PrefsForPlauerbord.data, data).apply();
    }

    public String getData(){
        return preferences.getString(data, "");
    }
}
