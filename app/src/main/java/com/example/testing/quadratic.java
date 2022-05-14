package com.example.testing;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.transition.AutoTransition;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormatSymbols;
import java.util.Timer;
import java.util.TimerTask;

import com.example.testing.databinding.QuadraticBinding;


import static java.lang.Math.sqrt;

// MAKE A FUNCTION DAT CAN RECOGNIZE WHETHER A ROOT IS RATIONAL OR NAH BY CHECKING THE NEAREST RATIONAL ROOT (https://bytes.com/topic/c/answers/957966-check-rational-irrational-numbers-after-we-take-square-root)
// fractions will work by making then intergers by multiplying all of the numbers by 10 until all are intergers (eg. 1.44 = 12 to 144 = 12)
public class quadratic extends DrawerBaseActivity {

    QuadraticBinding quadraticBinding;

    CardView resultCard, scrollForMoreCard, explanationCard1, explanationCard2, explanationCard3, explanationCard4;
    TextView x1Text, x1Value, x2Text, x2Value, discriminatorText, discriminatorValue;

    LinearLayout alertLayout;
    CardView alertCard;
    TextView alertText;

    TextView scrollForMoreText;
    ImageView scrollForMoreArrow;
    TextView explanationDiscriminatorCalculationTxt;
    TextView explanationX1Text, explanationX1CalculationNumerator, explanationX1CalculationDenominator, explanationX1CalculationAnswer, explanationX1EqualSign;
    TextView explanationX2Text, explanationX2CalculationNumerator, explanationX2CalculationDenominator, explanationX2CalculationAnswer, explanationX2EqualSign;
    LinearLayout resultLayout, scrollForMoreLayout;
    Button submitButton;

    ScrollView quadraticScrollView;

    boolean alertCardBusy = false, scrollForMoreExpanded = false;
    boolean enableScrolling = false;

    double inputA, inputB, inputC;
    EditText inputAText, inputBText, inputCText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        quadraticBinding = quadraticBinding.inflate(getLayoutInflater());
        setContentView(quadraticBinding.getRoot());
        allocateActivityTitle("Quadratic Calculator");

        inputAText = (EditText)findViewById(R.id.editAInput);
        inputBText = (EditText)findViewById(R.id.editBInput);
        inputCText = (EditText)findViewById(R.id.editCInput);

        alertLayout = (LinearLayout)findViewById(R.id.alertLayout);
        alertLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        alertCard = (CardView)findViewById(R.id.alertCard);
        alertText = (TextView)findViewById(R.id.alertText);

        alertCard.setVisibility(View.GONE);
        alertText.setVisibility(View.GONE);

        discriminatorText = (TextView)findViewById(R.id.discriminatorText);
        discriminatorValue = (TextView)findViewById(R.id.discriminatorValue);
        x1Text = (TextView) findViewById(R.id.x1Text);
        x1Value = (TextView) findViewById(R.id.x1Value);
        x2Text = (TextView) findViewById(R.id.x2Text);
        x2Value = (TextView) findViewById(R.id.x2Value);
        resultLayout = (LinearLayout)findViewById(R.id.result);
        resultLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        resultCard = (CardView)findViewById(R.id.resultCard);

        resultCard.setVisibility(View.INVISIBLE);
        discriminatorText.setVisibility(View.GONE);
        discriminatorValue.setVisibility(View.GONE);
        x1Text.setVisibility(View.GONE);
        x1Value.setVisibility(View.GONE);
        x2Text.setVisibility(View.GONE);
        x2Value.setVisibility(View.GONE);

        scrollForMoreLayout = (LinearLayout)findViewById(R.id.scrollForMoreLayout);
        scrollForMoreLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        scrollForMoreCard = (CardView)findViewById(R.id.scrollForMoreCard);
        scrollForMoreText = (TextView) findViewById(R.id.scrollForMoreText);
        scrollForMoreArrow = (ImageView) findViewById(R.id.scrollForMoreArrow);

        scrollForMoreCard.setVisibility(View.INVISIBLE);
        scrollForMoreText.setVisibility(View.GONE);
        scrollForMoreArrow.setVisibility(View.GONE);

/*
        explanationCard1 = (CardView)findViewById(R.id.explanationCard1);
        explanationCard2 = (CardView)findViewById(R.id.explanationCard2);
        explanationCard3 = (CardView)findViewById(R.id.explanationCard3);
        explanationCard4 = (CardView)findViewById(R.id.explanationCard4);

        explanationDiscriminatorCalculationTxt = (TextView)findViewById(R.id.explanationDiscriminatorCalculationTxt);

        explanationX1Text = (TextView)findViewById(R.id.explanationX1Text);
        explanationX1CalculationNumerator = (TextView)findViewById(R.id.explanationX1CalculationNumerator);
        explanationX1CalculationDenominator = (TextView)findViewById(R.id.explanationX1CalculationDenominator);
        explanationX1EqualSign = (TextView)findViewById(R.id.explanationX1EqualSign);
        explanationX1CalculationAnswer = (TextView)findViewById(R.id.explanationX1CalculationAnswer);

        explanationX2Text = (TextView)findViewById(R.id.explanationX2Text);
        explanationX2CalculationNumerator = (TextView)findViewById(R.id.explanationX2CalculationNumerator);
        explanationX2CalculationDenominator = (TextView)findViewById(R.id.explanationX2CalculationDenominator);
        explanationX2EqualSign = (TextView)findViewById(R.id.explanationX2EqualSign);
        explanationX2CalculationAnswer = (TextView)findViewById(R.id.explanationX2CalculationAnswer);

        explanationCard1.setVisibility(View.GONE);
        explanationCard2.setVisibility(View.GONE);
        explanationCard3.setVisibility(View.GONE);
        explanationCard4.setVisibility(View.GONE);*/

        submitButton = (Button)findViewById(R.id.submitButton);

        quadraticScrollView = (ScrollView)findViewById(R.id.quadraticScrollView);
        quadraticScrollView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return !enableScrolling;
            }
        });
    }

    public void backToLandingPage(View view) {
        finishActivity(0);
        finish();
    }

    public void giveInfo(View view) {
        AlertDialog.Builder credits =  new AlertDialog.Builder(this);
        credits.setTitle("Quadratic Calculator Guide:");
        credits.setMessage("Enter the required information below.");
        credits.setNeutralButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        credits.create().show();
    }

    public double rnd8(double number) // round double to 8
    {
        double roundedNumber = BigDecimal.valueOf(number).setScale(8, RoundingMode.HALF_UP).doubleValue();
        return roundedNumber;
    }

    public static String fmt(double number) // formats a double into a string to remove unnecessary fraction (.0, if applicable)
    {
        if(number == (int) number)
            return String.format("%d",(int)number);
        else
            return String.format("%s",number);
    }

    public void fireAlert(String textToDisplay)
    {
        if(alertCardBusy == false)
        {
            resultAndScrollCardVisibility(View.GONE);
            alertText.setText(textToDisplay);
            alertCardBusy = true;
            int DELAY = 500; // Delay time in milliseconds
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    alertCard.setVisibility(View.VISIBLE);
                    int v = (alertText.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;
                    TransitionManager.beginDelayedTransition(resultLayout, new AutoTransition());
                    alertText.setVisibility(v);
                    int DELAY = 3500; // Delay time in milliseconds
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            int y = (alertText.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;
                            TransitionManager.beginDelayedTransition(scrollForMoreLayout, new AutoTransition());
                            alertText.setVisibility(y);
                            alertCard.setVisibility(View.GONE);
                            alertCardBusy = false;
                        }
                    }, DELAY);
                }
            }, DELAY);
        }
    }

    public void resetAnswerText()
    {
        discriminatorText.setText("D =");
        x1Text.setText("x₁ =");
        x1Text.setTextSize(20);
        x2Text.setText("x₂ =");
        x1Value.setText("");
        x2Value.setText("");
        /*
        explanationX1Text.setText("x₁ =");
        explanationX1CalculationNumerator.setText("");
        explanationX1CalculationDenominator.setText("");
        explanationX1EqualSign.setText("=");
        explanationX1CalculationAnswer.setText("");

        explanationX2Text.setText("x₂ =");
        explanationX2CalculationNumerator.setText("");
        explanationX2CalculationDenominator.setText("");
        explanationX2EqualSign.setText("=");
        explanationX2CalculationAnswer.setText("");*/
    }

    public void hideKeyboard()
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(submitButton.getWindowToken(), 0);
    }

    public void resultAndScrollCardVisibility(int visibility)
    {
        if(visibility == View.GONE)
            resultCard.setVisibility(View.INVISIBLE);
        else
            resultCard.setVisibility(View.VISIBLE);
        TransitionManager.beginDelayedTransition(resultLayout, new AutoTransition());
        // result card
        discriminatorText.setVisibility(visibility);
        discriminatorValue.setVisibility(visibility);
        x1Text.setVisibility(visibility);
        x1Value.setVisibility(visibility);
        x2Text.setVisibility(visibility);
        x2Value.setVisibility(visibility);
        // Second page stuff
        if(visibility == View.VISIBLE && scrollForMoreExpanded == false) // fades in scrollForMoreCard, since its supposed to appear a little after the resultCard
        {

            int DELAY = 1500; // Delay time in milliseconds

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    enableScrolling = true;
                    quadraticScrollView.scrollTo(0, 0);
                    int y = View.VISIBLE;
                    TransitionManager.beginDelayedTransition(scrollForMoreLayout, new AutoTransition());
                    scrollForMoreExpanded = true;
                    scrollForMoreCard.setVisibility(View.VISIBLE);
                    scrollForMoreText.setVisibility(y);
                    scrollForMoreArrow.setVisibility(y);
                    /*
                    explanationCard1.setVisibility(y);
                    explanationCard2.setVisibility(y);
                    explanationCard3.setVisibility(y);
                    explanationCard4.setVisibility(y);*/
                }
            }, DELAY);
        }
        if(visibility == View.GONE && scrollForMoreExpanded == true) // fades out scrollForMoreCard
        {
            enableScrolling = false;
            quadraticScrollView.scrollTo(0, 0);
            TransitionManager.beginDelayedTransition(scrollForMoreLayout, new AutoTransition());
            scrollForMoreExpanded = false;
            scrollForMoreCard.setVisibility(View.INVISIBLE);
            scrollForMoreText.setVisibility(visibility);
            scrollForMoreArrow.setVisibility(visibility);
            /*
            explanationCard1.setVisibility(visibility);
            explanationCard2.setVisibility(visibility);
            explanationCard3.setVisibility(visibility);
            explanationCard4.setVisibility(visibility);*/
        }
    }

    /*public void showResult(View view)
    {
        resetAnswerText();
        hideKeyboard();

        boolean anyValidInputReceived;
        int retractOrExpand = 2;
        if (!TextUtils.isEmpty(inputAText.getText().toString()) && !TextUtils.isEmpty(inputBText.getText().toString()) && !TextUtils.isEmpty(inputCText.getText().toString()))
        {
            anyValidInputReceived = true;
            inputA = Double.parseDouble(inputAText.getText().toString());
            inputB = Double.parseDouble(inputBText.getText().toString());
            inputC = Double.parseDouble(inputCText.getText().toString());
        } else
        {
            anyValidInputReceived = false;
        }
        if(anyValidInputReceived == false)
        {
            String text = "Enter a, b and c!";
            if(TextUtils.isEmpty(inputAText.getText().toString()) && TextUtils.isEmpty(inputBText.getText().toString()) && TextUtils.isEmpty(inputCText.getText().toString()))
                text = "Enter a, b and c!";
            else if(TextUtils.isEmpty(inputAText.getText().toString()) && TextUtils.isEmpty(inputBText.getText().toString()))
                text = "Enter a and b!";
            else if(TextUtils.isEmpty(inputBText.getText().toString()) && TextUtils.isEmpty(inputCText.getText().toString()))
                text = "Enter b and c!";
            else if(TextUtils.isEmpty(inputAText.getText().toString()) && TextUtils.isEmpty(inputCText.getText().toString()))
                text = "Enter a and C!";
            else if(TextUtils.isEmpty(inputAText.getText().toString()))
                text = "Enter a!";
            else if(TextUtils.isEmpty(inputBText.getText().toString()))
                text = "Enter b!";
            else if(TextUtils.isEmpty(inputCText.getText().toString()))
                text = "Enter c!";
            fireAlert(text);
        }
        else if(inputA == 0 || inputB == 0 || inputC == 0)
        {
            String text = "Inputs cannot be 0!";
            if(inputA == 0 && inputB == 0 && inputC == 0)
                text = "Inputs a, b and c are 0!";
            else if(inputA == 0 && inputB == 0)
                text = "Inputs a and b are 0";
            else if(inputB == 0 && inputC == 0)
                text = "Inputs b and c are 0";
            else if(inputA == 0 && inputC == 0)
                text = "Inputs a and c are 0";
            else if(inputA == 0)
                text = "Input a is 0!";
            else if(inputB == 0)
                text = "Input b is 0!";
            else if(inputC == 0)
                text = "Input c is 0!";
            fireAlert(text);
        }
        else if ((inputB*inputB) - (4 * inputA * inputC) > -1 && anyValidInputReceived == true)
        {
            resultAndScrollCardVisibility(View.VISIBLE);
            double discriminator = inputB * inputB - 4 * inputA * inputC;
            double discriminatorRoot = sqrt(discriminator);
            double x1 = (-inputB - discriminatorRoot) / (2 * inputA);
            double x2 = (-inputB + discriminatorRoot) / (2 * inputA);

            discriminatorText.setText("D =");
            discriminatorValue.setText("√" + fmt(rnd8(discriminator)) + " = " + fmt(rnd8(sqrt(discriminator))) + "");
            x1Value.setText("" + fmt(rnd8(x1)));
            x2Value.setText("" + fmt(rnd8(x2)));
            /*
            explanationDiscriminatorCalculationTxt.setText("D = " + fmt(inputB) + "²" + " - 4 * " + fmt(inputA) + " * " + fmt(inputC) +" = " + fmt((inputB*inputB)) + " - " + fmt((4*inputA*inputC)) + " = " + fmt(rnd8(discriminator)));
            explanationX1CalculationNumerator.setText("-" + fmt(inputB) + " + " + "√" + fmt(discriminator));
            explanationX1CalculationDenominator.setText("2 * " + fmt(inputA));
            explanationX1CalculationAnswer.setText("" + fmt(x1));
            explanationX2CalculationNumerator.setText("-" + fmt(inputB) + " - " + "√" + fmt(discriminator));
            explanationX2CalculationDenominator.setText("2 * " + fmt(inputA));
            explanationX2CalculationAnswer.setText(""+ fmt(x2));*//*
        }
        else if ((inputB*inputB) - (4 * inputA * inputC) < -1)
        {
            resultAndScrollCardVisibility(View.VISIBLE);
            double discriminator = inputB * inputB - 4 * inputA * inputC;
            discriminatorText.setText("D = " + discriminator);
            discriminatorValue.setText("");
            x1Text.setText("D < 0");
            x2Text.setText("No answers");
            /*
            explanationDiscriminatorCalculationTxt.setText("D = " + fmt(inputB) + "²" + " - 4 * " + fmt(inputA) + " * " + fmt(inputC) +" = " + fmt((inputB*inputB)) + " - " + fmt((4*inputA*inputC)) + " = " + fmt(rnd8(discriminator)));
            explanationX1Text.setText("D = " + fmt(rnd8(discriminator)) + " < 0");
            explanationX1EqualSign.setText("");
            explanationX2Text.setText("You can't take the root of a negative number!");
            explanationX2EqualSign.setText("");*//*
        }
        else
        {
            resultAndScrollCardVisibility(View.VISIBLE);
            anyValidInputReceived = false;
            discriminatorValue.setText("Shouldnt be here");
        }
    }*/
}
