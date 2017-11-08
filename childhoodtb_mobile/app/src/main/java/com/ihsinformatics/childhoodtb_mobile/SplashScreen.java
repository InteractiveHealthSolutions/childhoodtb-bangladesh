package com.ihsinformatics.childhoodtb_mobile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

/**
 * Created by Shujaat on 8/16/2017.
 * not is use ...
 */

public class SplashScreen extends Activity {

    private Handler mHandler = new Handler();
    TextView childhoodText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setTheme(App.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        //childhoodText.startAnimation(AnimationUtils.loadAnimation(SplashScreen.this, android.R.anim.fade_in));
        timer();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        timer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        timer();
    }

    //timer
    private void timer() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
            }
        }, 3000); // 3 seconds
    }
}
