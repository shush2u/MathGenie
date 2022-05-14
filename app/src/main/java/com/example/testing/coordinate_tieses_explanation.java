package com.example.testing;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import static java.lang.Math.sqrt;

public class coordinate_tieses_explanation extends Activity {
    Button exit, continue1, continue2;
    double x1, y1, x2, y2, preRootDistance, distance, xIlgis, yIlgis;
    TextView xKrastinesIlgis, yKrastinesIlgis, atstumas2, atstumas, xVidurioTaskas, yVidurioTaskas, VidurioTaskas;

    TextView textView16, textView19;
    ImageView coordinatesExample2, vidurioTaskasExample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coordinate_tieses_explanation);

        Bundle extras = getIntent().getExtras(); // gets values from the previous activity
        if (extras != null) {
            x1 = extras.getDouble("x1");
            y1 = extras.getDouble("y1");
            x2 = extras.getDouble("x2");
            y2 = extras.getDouble("y2");
            preRootDistance = extras.getDouble("preRootDistance");
            distance = extras.getDouble("distance");

        }
        exit = (Button)findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(coordinate_tieses_explanation.this, coordinate_tieses.class);
                startActivity(intent);
            }
        });
        xKrastinesIlgis = (TextView)findViewById(R.id.xKrastinesIlgis);
        yKrastinesIlgis = (TextView)findViewById(R.id.yKrastinesIlgis);
        if(x1-x2 > 0)
        {
            if(x2 >= 0)
                xKrastinesIlgis.setText("x kraštinė = |" + x1 + " - " + x2 + "| = " + (x1-x2));
                else
                    xKrastinesIlgis.setText("x kraštinė = |" + x1 + " - (" + x2 + ")| = " + (x1-x2));
            xIlgis = (x1-x2);
        }
            else
            {
                if(x2 >= 0)
                    xKrastinesIlgis.setText("x kraštinė = |" + x1 + " - " + x2 + "| = " + (x1-x2)*-1);
                    else
                        xKrastinesIlgis.setText("x kraštinė = |" + x1 + " - (" + x2 + ")| = " + (x1-x2)*-1);
                xIlgis = (x1-x2)*-1;
            }
        if(y1-y2 > 0)
        {
            if(y2 >= 0)
                yKrastinesIlgis.setText("y kraštinė = |" + y1 + " - " + y2 + "| = " + (y1-y2));
                else
                    yKrastinesIlgis.setText("y kraštinė = |" + y1 + " - (" + y2 + ")| = " + (y1-y2));
            yIlgis = (y1-y2);
        }
            else
            {
                if(y2 >= 0)
                    yKrastinesIlgis.setText("y kraštinė = |" + y1 + " - " + y2 + "| = " + (y1-y2)*-1);
                    else
                        yKrastinesIlgis.setText("y kraštinė = |" + y1 + " - (" + y2 + ")| = " + (y1-y2)*-1);
                yIlgis = (y1-y2)*-1;
            }

        atstumas2 = (TextView)findViewById(R.id.atstumas2);
        atstumas2.setText("Atstumas² = " + xIlgis + "² + " + yIlgis + "² = " + preRootDistance);
        atstumas = (TextView)findViewById(R.id.atstumas);
        atstumas.setText("Atstumas = √" + preRootDistance + " = " + distance);
        xVidurioTaskas = (TextView)findViewById(R.id.xVidurioTaskas);
        yVidurioTaskas = (TextView)findViewById(R.id.yVidurioTaskas);
        VidurioTaskas = (TextView)findViewById(R.id.VidurioTaskas);
        xVidurioTaskas.setText("x = (" + x1 + " + " + x2 + ") / 2 = " + (x1+x2)/2);
        yVidurioTaskas.setText("y = (" + y1 + " + " + y2 + ") / 2 = " + (y1+y2)/2);
        VidurioTaskas.setText("Vidurio taškas = (" + (x1+x2)/2 + "; " + (y1+y2)/2 + ")");

        // c1
        textView16 = (TextView)findViewById(R.id.textView16);
        textView16.setVisibility(View.INVISIBLE);
        coordinatesExample2 = (ImageView) findViewById(R.id.coordinatesExample2);
        coordinatesExample2.setVisibility(View.INVISIBLE);
        atstumas2.setVisibility(View.INVISIBLE);
        atstumas.setVisibility(View.INVISIBLE);
        // c1 end
        // c2
        textView19 = (TextView)findViewById(R.id.textView19);
        textView19.setVisibility(View.INVISIBLE);
        xVidurioTaskas.setVisibility(View.INVISIBLE);
        yVidurioTaskas.setVisibility(View.INVISIBLE);
        VidurioTaskas.setVisibility(View.INVISIBLE);
        vidurioTaskasExample = (ImageView) findViewById(R.id.vidurioTaskasExample);
        vidurioTaskasExample.setVisibility(View.INVISIBLE);
        // c2 end

        continue1 = (Button)findViewById(R.id.continue1);
        continue1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                continue1.setVisibility(view.GONE);
                continue2.setVisibility(view.VISIBLE);
                textView16.setVisibility(View.VISIBLE);
                coordinatesExample2.setVisibility(View.VISIBLE);
                atstumas2.setVisibility(View.VISIBLE);
                atstumas.setVisibility(View.VISIBLE);
            }
        });
        continue2 = (Button)findViewById(R.id.continue2);
        continue2.setVisibility(View.INVISIBLE);
        continue2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                continue2.setVisibility(view.GONE);
                textView19.setVisibility(View.VISIBLE);
                xVidurioTaskas.setVisibility(View.VISIBLE);
                yVidurioTaskas.setVisibility(View.VISIBLE);
                VidurioTaskas.setVisibility(View.VISIBLE);
                vidurioTaskasExample.setVisibility(View.VISIBLE);
            }
        });
    }

}
