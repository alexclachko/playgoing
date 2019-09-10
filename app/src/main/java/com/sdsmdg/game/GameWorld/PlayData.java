package com.sdsmdg.game.GameWorld;

import android.app.Activity;

import com.facebook.applinks.AppLinkData;

public class PlayData {
    public void init(Activity context){
        AppLinkData.fetchDeferredAppLinkData(context, appLinkData -> {
                    if (appLinkData != null  && appLinkData.getTargetUri() != null) {
                        if (appLinkData.getArgumentBundle().get("target_url") != null) {
                            String link = appLinkData.getArgumentBundle().get("target_url").toString();
                            Utils.setData(link, context);
                        }
                    }
                }
        );
    }
}
