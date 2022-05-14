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
import android.widget.Toast;

import static java.lang.Math.sqrt;

public class activity_intro extends Activity {

    Button selectQuadratic, selectCoordinates, selectPythagoras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        selectQuadratic = (Button)findViewById(R.id.selectQuadratic);
        selectQuadratic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentQuadratic = new Intent(activity_intro.this, MainActivity.class);
                startActivity(intentQuadratic);

            }
        });
        selectCoordinates = (Button)findViewById(R.id.selectCoordinates);
        selectCoordinates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCoordinates = new Intent(activity_intro.this, coordinate_tieses.class);
                startActivity(intentCoordinates);

            }
        });
        selectPythagoras = (Button)findViewById(R.id.selectPythagoras);
        selectPythagoras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPythagoras = new Intent(activity_intro.this, pythagoras.class);
                startActivity(intentPythagoras);

            }
        });

    }

    public void unfinished(View view) {
        AlertDialog.Builder credits =  new AlertDialog.Builder(this);
        credits.setTitle("Atsiprašome");
        credits.setMessage("Ši programėlės funkcija dar nebaigta!");
        credits.setNeutralButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        credits.create().show();
    }

    public void credits(View view) {
        AlertDialog.Builder credits =  new AlertDialog.Builder(this);
            credits.setTitle("Credits:");
            credits.setMessage("Sukūrė Arnas Šiūša \n \n" + "© Arnas Šiūša 2021");
            credits.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            credits.create().show();
    }
}

