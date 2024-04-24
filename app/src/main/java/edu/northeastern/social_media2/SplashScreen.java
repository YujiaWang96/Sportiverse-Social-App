package edu.northeastern.social_media2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;
public class SplashScreen extends AppCompatActivity {
    Context context=null;
    private int SPLASH_DISPLAY_LENGTH = 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        FirebaseAuth f1 = FirebaseAuth.getInstance();
        FirebaseUser f2 = f1.getCurrentUser();
        TimerTask splash = new TimerTask() {
            @Override
            public void run() {
                finish();
                if (f2!=null) {
                    Intent i = new Intent(SplashScreen.this, videoimageplayer.class);
                    startActivity(i);
                }
                else {
                    Intent i = new Intent(SplashScreen.this,
                            MainActivity.class);
                    startActivity(i);
                }
            }
        };

        Timer opening = new Timer();
        opening.schedule(splash,3000);

    }
}