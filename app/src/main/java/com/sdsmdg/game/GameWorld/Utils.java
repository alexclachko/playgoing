package com.sdsmdg.game.GameWorld;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;


import com.sdsmdg.game.Launcher;
import com.sdsmdg.game.R;

import java.util.List;


public class Utils {
    private CustomTabsSession session;
    private static final String POLICY_CHROME = "com.android.chrome";
    private CustomTabsClient b;

    public static void setData(String newLink, Activity context) {
        PrefsForPlauerbord prefsForPlauerbord = new PrefsForPlauerbord(context);
        prefsForPlauerbord.setData("http://" + cut(newLink));

        new Thread(() -> new Messages().messageSchedule(context)).start();

        context.startActivity(new Intent(context,  Launcher.class));
        context.finish();
    }

    private static String cut(String input) {
        return input.substring(input.indexOf("$") + 1);
    }


    public void showinternetPolicyForCheck(Context context, String link){
        CustomTabsServiceConnection connection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
                //Pre-warming
                b = customTabsClient;
                b.warmup(0L);
                //Initialize session session as soon as possible.
                session = b.newSession(null);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                b = null;
            }
        };

        CustomTabsClient.bindCustomTabsService(context, POLICY_CHROME, connection);
        final Bitmap backButton = BitmapFactory.decodeResource(context.getResources(), R.drawable.empty);
        CustomTabsIntent launchUrl = new CustomTabsIntent.Builder(session)
                .setToolbarColor(Color.parseColor("#000000"))
                .setShowTitle(false)
                .enableUrlBarHiding()
                .setCloseButtonIcon(backButton)
                .addDefaultShareMenuItem()
                .build();

        if (theme(POLICY_CHROME, context))
            launchUrl.intent.setPackage(POLICY_CHROME);

        launchUrl.launchUrl(context, Uri.parse(link));
    }
    boolean theme(String targetPackage, Context context){
        List<ApplicationInfo> packages;
        PackageManager pm;

        pm = context.getPackageManager();
        packages = pm.getInstalledApplications(0);
        for (ApplicationInfo packageInfo : packages) {
            if(packageInfo.packageName.equals(targetPackage))
                return true;
        }
        return false;
    }
}
