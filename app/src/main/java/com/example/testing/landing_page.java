package com.example.testing;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.util.Log;
import android.view.View.OnLayoutChangeListener;


import androidx.cardview.widget.CardView;

import java.io.Console;

public class landing_page extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // this line removes the STATUS BAR AT THE TOP (not the action bar)
        setContentView(R.layout.landing_page);
    }



    public void openQuadratic(View view) {
        Intent intentQuadratic = new Intent(landing_page.this, quadratic.class);
        startActivityForResult(intentQuadratic, 0);
    }
    public void openCoordinates(View view) {
        Intent intentCoordinates = new Intent(landing_page.this, coordinate_tieses.class);
        startActivity(intentCoordinates);
    }
    public void openPythagoras(View view) {
        Intent intentPythagoras = new Intent(landing_page.this, pythagoras.class);
        startActivityForResult(intentPythagoras, 0);
    }
    public void openTrigonometry(View view) {


    }
}
