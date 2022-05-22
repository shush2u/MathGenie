package com.example.testing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class splashLogo extends Activity {

    private static int SPLASH_SCREEN = 4000;

    Animation topAnimation;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  // this line removes the STATUS BAR AT THE TOP (not the action bar)
        setContentView(R.layout.splash_logo);

        topAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein);

        image = findViewById(R.id.splashLogo);
        image.setAnimation(topAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splashLogo.this, quadraticMain.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}