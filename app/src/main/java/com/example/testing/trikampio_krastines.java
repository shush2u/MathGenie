package com.example.testing; // cia unfinished, ateityje kai sugalvosiu kaip graziai sitam surinkt duomenis tai padarysiu

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static java.lang.Math.sqrt;

public class trikampio_krastines extends Activity {

    TextView txtOutputATxt;
    TextView txtOutputAAngleTxt;
    TextView txtOutputBTxt;
    TextView txtOutputBAngleTxt;
    TextView txtOutputCTxt;
    TextView txtOutputCAngleTxt;
    EditText editAInput;
    EditText editAAngleInput;
    EditText editBInput;
    EditText editBAngleInput;
    EditText editCInput;
    EditText editCAngleInput;
    Button confButton, exit;

    double inputA, inputAAngle, inputB, inputBAngle, inputC, inputCAngle;
    double A, B, C, AAngle, BAngle, CAngle;
    int data = 0;
    boolean possible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trikampio_krastines);

        txtOutputATxt = (TextView)findViewById(R.id.txtOutputATxt);
        txtOutputAAngleTxt = (TextView)findViewById(R.id.txtOutputAAngleTxt);
        txtOutputBTxt = (TextView)findViewById(R.id.txtOutputBTxt);
        txtOutputBAngleTxt = (TextView)findViewById(R.id.txtOutputBAngleTxt);
        txtOutputCTxt = (TextView)findViewById(R.id.txtOutputCTxt);
        txtOutputCAngleTxt = (TextView)findViewById(R.id.txtOutputCAngleTxt);
        editAInput = (EditText)findViewById(R.id.editAInput);
        editAAngleInput = (EditText)findViewById(R.id.editAAngleInput);
        editBInput = (EditText)findViewById(R.id.editBInput);
        editBAngleInput = (EditText)findViewById(R.id.editBAngleInput);
        editCInput = (EditText)findViewById(R.id.editCInput);
        editCAngleInput = (EditText)findViewById(R.id.editCAngleInput);
        confButton = (Button)findViewById(R.id.confButton);
        exit = (Button)findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputData = editAInput.getText().toString();
                //Intent intent = new Intent(trikampio_krastines.this, activity_intro.class);
                //startActivity(intent);
            }
        });
    }

    public void findSides(View view) {

        if (!TextUtils.isEmpty(editAInput.getText().toString()) && !TextUtils.isEmpty(editBInput.getText().toString()) && !TextUtils.isEmpty(editCInput.getText().toString()) && !TextUtils.isEmpty(editAAngleInput.getText().toString()) && !TextUtils.isEmpty(editBAngleInput.getText().toString()) && !TextUtils.isEmpty(editCAngleInput.getText().toString())) {
            inputA = Double.parseDouble(editAInput.getText().toString());
            inputB = Double.parseDouble(editBInput.getText().toString());
            inputC = Double.parseDouble(editCInput.getText().toString());
            inputAAngle = Double.parseDouble(editAInput.getText().toString());
            inputBAngle = Double.parseDouble(editBInput.getText().toString());
            inputCAngle = Double.parseDouble(editCInput.getText().toString());
        } else {
            inputA = 0;
            inputB = 0;
            inputC = 0;
            inputAAngle = 0;
            inputBAngle = 0;
            inputCAngle = 0;
        }


    }

    public void clear(View view){

        A = 0;
        B = 0;
        C = 0;
        AAngle = 0;
        BAngle = 0;
        CAngle = 0;
        txtOutputATxt.setText("");
        txtOutputAAngleTxt.setText("");
        txtOutputBTxt.setText("");
        txtOutputBAngleTxt.setText("");
        txtOutputCTxt.setText("");
        txtOutputCAngleTxt.setText("");
        editAInput.setText("");
        editBInput.setText("");
        editCInput.setText("");
        editAAngleInput.setText("");
        editBAngleInput.setText("");
        editCAngleInput.setText("");

    }
}