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

public class pythagoras_explanation extends Activity {
    Button exit, continue1, continue2;
    TextView pythagorasA, pythagorasB, pythagorasSideCalculation, pythagorasHypotenuseCalculation, pythagorasSideCalculationRoot, pythagorasHypotenuseCalculationRoot;

    TextView textView6, textView7, textView8, textView9, textView10, textView11, textView12, textView13, textView14, pythagorasLeft, pythagorasRight;
    ImageView statineExample, izambineexample;

    double a, b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pythagoras_explanation);

        Bundle extras = getIntent().getExtras(); // gets values from the previous activity
        if (extras != null) {
            a = extras.getDouble("a");
            b = extras.getDouble("b");
        }
        exit = (Button)findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(pythagoras_explanation.this, pythagoras.class);
                startActivity(intent);
            }
        });

        // c1
        textView6 = (TextView)findViewById(R.id.textView6);
        textView6.setVisibility(View.INVISIBLE);
        textView7 = (TextView)findViewById(R.id.textView7);
        textView7.setVisibility(View.INVISIBLE);
        textView8 = (TextView)findViewById(R.id.textView8);
        textView8.setVisibility(View.INVISIBLE);
        textView9 = (TextView)findViewById(R.id.textView9);
        textView9.setVisibility(View.INVISIBLE);
        textView10 = (TextView)findViewById(R.id.textView10);
        textView10.setVisibility(View.INVISIBLE);
        statineExample = (ImageView)findViewById(R.id.statineExample);
        statineExample.setVisibility(View.INVISIBLE);
        izambineexample = (ImageView)findViewById(R.id.izambineexample);
        izambineexample.setVisibility(View.INVISIBLE);
        textView11 = (TextView)findViewById(R.id.textView11);
        textView11.setVisibility(View.INVISIBLE);
        textView12 = (TextView)findViewById(R.id.textView12);
        textView12.setVisibility(View.INVISIBLE);
        textView13 = (TextView)findViewById(R.id.textView13);
        textView13.setVisibility(View.INVISIBLE);
        textView14 = (TextView)findViewById(R.id.textView14);
        textView14.setVisibility(View.INVISIBLE);
        // c1 end
        // c2
        pythagorasA = (TextView)findViewById(R.id.pythagorasA);
        pythagorasA.setVisibility(View.INVISIBLE);
        pythagorasB = (TextView)findViewById(R.id.pythagorasB);
        pythagorasB.setVisibility(View.INVISIBLE);
        pythagorasLeft = (TextView)findViewById(R.id.pythagorasLeft);
        pythagorasLeft.setVisibility(View.INVISIBLE);
        pythagorasRight = (TextView)findViewById(R.id.pythagorasRight);
        pythagorasRight.setVisibility(View.INVISIBLE);
        pythagorasSideCalculation = (TextView)findViewById(R.id.pythagorasSideCalculation);
        pythagorasSideCalculation.setVisibility(View.INVISIBLE);
        pythagorasSideCalculationRoot = (TextView)findViewById(R.id.pythagorasSideCalculationRoot);
        pythagorasSideCalculationRoot.setVisibility(View.INVISIBLE);
        pythagorasHypotenuseCalculation = (TextView)findViewById(R.id.pythagorasHypotenuseCalculation);
        pythagorasHypotenuseCalculation.setVisibility(View.INVISIBLE);
        pythagorasHypotenuseCalculationRoot = (TextView)findViewById(R.id.pythagorasHypotenuseCalculationRoot);
        pythagorasHypotenuseCalculationRoot.setVisibility(View.INVISIBLE);
        // c2 end

        continue1 = (Button)findViewById(R.id.continue1);
        continue1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                continue1.setVisibility(view.GONE);
                continue2.setVisibility(View.VISIBLE);
                textView6.setVisibility(View.VISIBLE);
                textView7.setVisibility(View.VISIBLE);
                textView8.setVisibility(View.VISIBLE);
                textView9.setVisibility(View.VISIBLE);
                textView10.setVisibility(View.VISIBLE);
                statineExample.setVisibility(View.VISIBLE);
                izambineexample.setVisibility(View.VISIBLE);
                textView11.setVisibility(View.VISIBLE);
                textView12.setVisibility(View.VISIBLE);
                textView13.setVisibility(View.VISIBLE);
                textView14.setVisibility(View.VISIBLE);
            }
        });
        continue2 = (Button)findViewById(R.id.continue2);
        continue2.setVisibility(View.INVISIBLE);
        continue2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                continue2.setVisibility(view.GONE);
                pythagorasA.setVisibility(View.VISIBLE);
                pythagorasB.setVisibility(View.VISIBLE);
                pythagorasLeft.setVisibility(View.VISIBLE);
                pythagorasRight.setVisibility(View.VISIBLE);
                pythagorasSideCalculation.setVisibility(View.VISIBLE);
                pythagorasSideCalculationRoot.setVisibility(View.VISIBLE);
                pythagorasHypotenuseCalculation.setVisibility(View.VISIBLE);
                pythagorasHypotenuseCalculationRoot.setVisibility(View.VISIBLE);
            }
        });

        pythagorasA.setText("a = " + a);
        pythagorasB.setText("b = " + b);
        pythagorasSideCalculation = (TextView)findViewById(R.id.pythagorasSideCalculation);
        pythagorasSideCalculationRoot = (TextView)findViewById(R.id.pythagorasSideCalculationRoot);
        if(a > b)
        {
            pythagorasSideCalculation.setText("a²" + " = " + a + "*" + a + "-" + b + "*" + b + " = " + (a*a-b*b));
            pythagorasSideCalculationRoot.setText("√" + (a*a-b*b) + " = " + sqrt((a*a-b*b)));
        }

            else
            {
                pythagorasSideCalculation.setText("a²" + " = " + b + "*" + b + "-" + a + "*" + a + " = " + (b*b-a*a));
                pythagorasSideCalculationRoot.setText("√" + (b*b-a*a) + " = " + sqrt((b*b-a*a)));
            }
        pythagorasHypotenuseCalculation = (TextView)findViewById(R.id.pythagorasHypotenuseCalculation);
        pythagorasHypotenuseCalculationRoot = (TextView)findViewById(R.id.pythagorasHypotenuseCalculationRoot);
        pythagorasHypotenuseCalculation.setText("c²" + " = " + a + "*" + a + "+" + b + "*" + b + " = " + (a*a+b*b));
        pythagorasHypotenuseCalculationRoot.setText("√" + (b*b+a*a) + " = " + sqrt((b*b+a*a)));


    }

}
