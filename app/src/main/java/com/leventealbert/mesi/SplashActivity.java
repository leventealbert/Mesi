package com.leventealbert.mesi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * activity for the splash screen
 *
 * @author Levente Albert
 */
public class SplashActivity extends BaseActivity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 500;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        /* New Handler to start the MainActivity or LoginActivity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                if (BaseApplication.getPref(getBaseContext(),"CurrentUserId").equals("")) {
                    // start login activity
                    Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    //start main activity
                    BaseApplication.setCurrentUserId(BaseApplication.getPref(getBaseContext(),"CurrentUserId"));
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

                //close
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}