package com.projects.eventfinder;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.config.Config;
import com.libraries.usersession.UserAccessSession;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        setContentView(R.layout.activity_splash);
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                setDisplay();

            }
        }, 1000 * Config.SPLASH_DELAY_IN_SECONDS);
    }

    private void setDisplay() {
        boolean willNotShowWT = UserAccessSession.getInstance(this).willNotShowWalkthrough();
        if(willNotShowWT) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Intent intent = new Intent(SplashActivity.this, WTActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
