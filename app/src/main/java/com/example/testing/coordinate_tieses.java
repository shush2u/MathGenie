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
import android.widget.TextView;

import com.example.testing.databinding.ActivityCoordinatesBinding;

import static java.lang.Math.sqrt;

public class coordinate_tieses extends coordinate_tieses_explanation {

    ActivityCoordinatesBinding ActivityCoordinatesBinding;

    TextView txtOutputDistanceTxt;
    TextView txtOutputMiddleCoord;
    EditText editX1InputCoord;
    EditText editY1InputCoord;
    EditText editX2InputCoord;
    EditText editY2InputCoord;
    Button confButton, exit, explanationButton;

    double inputX1, inputY1, inputX2, inputY2, distance, x, y, preRootDistance;

    boolean anyValidInputReceived = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCoordinatesBinding = ActivityCoordinatesBinding.inflate(getLayoutInflater());
        setContentView(ActivityCoordinatesBinding.getRoot());

        txtOutputDistanceTxt = (TextView)findViewById(R.id.txtOutputDistanceTxt);
        txtOutputMiddleCoord = (TextView)findViewById(R.id.textViewMiddleCoord);
        editX1InputCoord = (EditText)findViewById(R.id.editX1InputCoord);
        editY1InputCoord = (EditText)findViewById(R.id.editY1InputCoord);
        editX2InputCoord = (EditText)findViewById(R.id.editX2InputCoord);
        editY2InputCoord = (EditText)findViewById(R.id.editY2InputCoord);
        confButton = (Button)findViewById(R.id.confButton);
        exit = (Button)findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputData = editX1InputCoord.getText().toString();
                //Intent intent = new Intent(coordinate_tieses.this, activity_intro.class);
                //startActivity(intent);
            }
        });
        explanationButton = (Button)findViewById(R.id.explanationButton);
        explanationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(anyValidInputReceived == true)
                {
                    Intent intent = new Intent(coordinate_tieses.this, coordinate_tieses_explanation.class);
                    startActivity(intent);
                    intent.putExtra("x1", inputX1);
                    intent.putExtra("y1", inputY1);
                    intent.putExtra("x2", inputX2);
                    intent.putExtra("y2", inputY2);
                    intent.putExtra("preRootDistance", preRootDistance);
                    intent.putExtra("distance", distance);
                    startActivity(intent);
                }
                else
                {
                    isEmptyAlert();
                }

            }
        });
    }

    public void findDistance(View view) {

        if (!TextUtils.isEmpty(editX1InputCoord.getText().toString()) && !TextUtils.isEmpty(editY1InputCoord.getText().toString()) && !TextUtils.isEmpty(editX2InputCoord.getText().toString()) && !TextUtils.isEmpty(editY2InputCoord.getText().toString()))
        {
            anyValidInputReceived = true;
            inputX1 = Double.parseDouble(editX1InputCoord.getText().toString());
            inputY1 = Double.parseDouble(editY1InputCoord.getText().toString());
            inputX2 = Double.parseDouble(editX2InputCoord.getText().toString());
            inputY2 = Double.parseDouble(editY2InputCoord.getText().toString());
            if(inputX1-inputX2 == 0 && inputY1-inputY2 == 0)
            {
                anyValidInputReceived = false;
                txtOutputDistanceTxt.setText("Įvedėte du tokius pačius taškus!");
            }
            else
            {
                anyValidInputReceived = true;
                distance = ((inputX2-inputX1)*(inputX2-inputX1)) + ((inputY2-inputY1)*(inputY2-inputY1));
                txtOutputDistanceTxt.setText("Atstumas = " + "√" + Math.round(distance) + " = " + sqrt(distance));
                preRootDistance = distance;
                distance = sqrt(distance);
                x = (inputX1+inputX2)/2;
                y = (inputY1+inputY2)/2;
                txtOutputMiddleCoord.setText("Vidurio Taškas: (" + x + "; " + y + ")");
            }

        }
        else
        {
            anyValidInputReceived = false;
            inputX1 = 0;
            inputY1 = 0;
            inputX2 = 0;
            inputY2 = 0;
            txtOutputDistanceTxt.setText("Įveskite taškus!");
            txtOutputMiddleCoord.setText("");
        }

    }

    public void clear(View view){

        anyValidInputReceived = false;
        distance = 0;
        x = 0;
        y = 0;
        txtOutputDistanceTxt.setText("");
        txtOutputMiddleCoord.setText("");
        editX1InputCoord.setText("");
        editY1InputCoord.setText("");
        editX2InputCoord.setText("");
        editY2InputCoord.setText("");
    }

    public void help(View view) {
        AlertDialog.Builder credits =  new AlertDialog.Builder(this);
        credits.setTitle("Kaip naudoti:");
        credits.setMessage("Ši skaičiuoklė skirta suskaičiuoti atstumą tarp dviejų koordinatės plokštumos taškų, bei rasti jų vidurio tašką. \n \n" + "Skaičiavimui reikia įvesti pirmo taško (x1 ir y1) ir antro taško koordinates (x2 ir y2).");
        credits.setNeutralButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        credits.create().show();
    }

    public void isEmptyAlert() {
        AlertDialog.Builder emptyAlert =  new AlertDialog.Builder(this);
        emptyAlert.setTitle("Dėmesio:");
        emptyAlert.setMessage("Teisingai įveskite visus duomenis!");
        emptyAlert.setNeutralButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        emptyAlert.create().show();
    }
}