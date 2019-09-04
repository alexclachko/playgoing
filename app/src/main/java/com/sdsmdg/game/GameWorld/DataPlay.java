package com.sdsmdg.game.GameWorld;

import android.content.Context;
import android.content.SharedPreferences;

public class DataPlay {
    private static String data = "pcport";
    private SharedPreferences preferences;

    public DataPlay(Context context){
        String NAME = "pcport";
        preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public void setData(String data){
        preferences.edit().putString(DataPlay.data, data).apply();
    }

    public String getData(){
        return preferences.getString(data, "");
    }
}
