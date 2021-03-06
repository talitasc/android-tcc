package com.example.talit.projetotcc.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.utils.LocaleLanguage;
import com.facebook.drawee.backends.pipeline.Fresco;


public class SplashScreen extends AppCompatActivity {

    public static final String NOME_PREFERENCE = "IDIOMA";
    private static String lingua = "pt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash_screen);
        Fresco.initialize(this);
        //MultiDex.install(this);
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    Intent intent = new Intent(SplashScreen.this, WelcomeScreen.class);
                    startActivity(intent);

                }
            }
        };
        timerThread.start();


    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences(NOME_PREFERENCE, MODE_PRIVATE);
        lingua = prefs.getString("lingua", null);

    }
   protected void attachBaseContext(Context base){

        super.attachBaseContext(LocaleLanguage.onAttach(base,lingua));
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}
