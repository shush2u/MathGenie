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
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.Math.sqrt;

public class pythagoras extends Activity {

    EditText editSideOne, editSideTwo;
    SwitchCompat mode;

    LinearLayout alertLayout;
    CardView alertCard;
    TextView alertText;

    CardView resultCard;
    LinearLayout resultLayout;
    TextView xText, xFinalText, xValue, xFinalValue;
    TextView xSideText, xSideNameText;

    Button submitButton;

    LinearLayout scrollForMoreLayout;
    CardView scrollForMoreCard;
    TextView scrollForMoreText;
    ImageView scrollForMoreArrow;

    ImageView pythagorasExplanationTriangle, pythagorasExplanationFormula;
    TextView pythagorasExplanationFormulaText, explanationWhatAreWeSearchingFor;
    TextView explanationXSquaredText, explanationXSquaredValue, explanationXText, explanationXValue;

    ScrollView pythagorasScrollView;

    double inputA, inputB;

    boolean alertCardBusy = false;
    boolean enableScrolling = false, scrollForMoreExpanded = false;
    boolean anyValidInputReceived = false, searchingForHypotenuse = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // this line removes the STATUS BAR AT THE TOP (not the action bar)
        setContentView(R.layout.pythagoras);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;
        int screenWidth = displayMetrics.widthPixels;

        LinearLayout firstPage = findViewById(R.id.firstPage);
        LinearLayout secondPage = findViewById(R.id.secondPage);
        ViewGroup.LayoutParams firstPageParams = firstPage.getLayoutParams();
        ViewGroup.LayoutParams secondPageParams = secondPage.getLayoutParams();
        firstPageParams.height = screenHeight;
        firstPageParams.width = screenWidth;
        secondPageParams.height = screenHeight;
        secondPageParams.width = screenWidth;
        firstPage.setLayoutParams(firstPageParams);
        firstPage.setLayoutParams(secondPageParams);

        editSideOne = (EditText)findViewById(R.id.editFirstSide);
        editSideTwo = (EditText)findViewById(R.id.editSecondSide);

        xSideText = (TextView)findViewById(R.id.xSideText);

        mode = (SwitchCompat)findViewById(R.id.modeSwitch);

        mode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked == true)
            {
                xSideText.setText("Side (a or b)");
                searchingForHypotenuse = false;
            }
            else
            {
                xSideText.setText("Hypotenuse (c)");
                searchingForHypotenuse = true;
            }
        });

        submitButton = (Button)findViewById(R.id.submitButton);

        alertLayout = (LinearLayout)findViewById(R.id.alertLayout);
        alertLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        alertCard = (CardView)findViewById(R.id.alertCard);
        alertText = (TextView)findViewById(R.id.alertText);

        alertCard.setVisibility(View.GONE);
        alertText.setVisibility(View.GONE);

        xText = (TextView)findViewById(R.id.xText);
        xValue = (TextView)findViewById(R.id.xValue);
        xFinalText = (TextView)findViewById(R.id.xFinalText);
        xFinalValue = (TextView)findViewById(R.id.xFinalValue);
        resultCard = (CardView)findViewById(R.id.resultCard);
        resultLayout = (LinearLayout)findViewById(R.id.resultLayout);
        resultLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        resultCard.setVisibility(View.INVISIBLE);
        xText.setVisibility(View.GONE);
        xValue.setVisibility(View.GONE);
        xFinalText.setVisibility(View.GONE);
        xFinalValue.setVisibility(View.GONE);

        scrollForMoreLayout = (LinearLayout)findViewById(R.id.scrollForMoreLayout);
        scrollForMoreLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        scrollForMoreCard = (CardView)findViewById(R.id.scrollForMoreCard);
        scrollForMoreText = (TextView) findViewById(R.id.scrollForMoreText);
        scrollForMoreArrow = (ImageView) findViewById(R.id.scrollForMoreArrow);

        scrollForMoreCard.setVisibility(View.INVISIBLE);
        scrollForMoreText.setVisibility(View.GONE);
        scrollForMoreArrow.setVisibility(View.GONE);

        pythagorasExplanationTriangle = (ImageView)findViewById(R.id.pythagorasExplanationTriangle);
        pythagorasExplanationFormula = (ImageView)findViewById(R.id.pythagorasExplanationFormula);
        pythagorasExplanationFormulaText = (TextView)findViewById(R.id.pythagorasExplanationFormulaText);

        explanationWhatAreWeSearchingFor = (TextView)findViewById(R.id.explanationWhatAreWeSearchingFor);
        explanationXSquaredText = (TextView)findViewById(R.id.explanationXSquaredText);
        explanationXSquaredValue = (TextView)findViewById(R.id.explanationXSquaredValue);
        explanationXText = (TextView)findViewById(R.id.explanationXText);
        explanationXValue = (TextView)findViewById(R.id.explanationXValue);

        pythagorasScrollView = (ScrollView)findViewById(R.id.pythagorasScrollView);
        pythagorasScrollView.setOnTouchListener(new View.OnTouchListener()
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

    public void hideKeyboard()
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(submitButton.getWindowToken(), 0);
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

    public void resultAndScrollCardVisibility(int visibility)
    {
        if(visibility == View.GONE)
            resultCard.setVisibility(View.INVISIBLE);
        else
            resultCard.setVisibility(View.VISIBLE);
        TransitionManager.beginDelayedTransition(resultLayout, new AutoTransition());
        // result card
        xText.setVisibility(visibility);
        xValue.setVisibility(visibility);
        xFinalText.setVisibility(visibility);
        xFinalValue.setVisibility(visibility);
        // Second page stuff

        if(visibility == View.VISIBLE && scrollForMoreExpanded == false) // fades in scrollForMoreCard, since its supposed to appear a little after the resultCard
        {
            int DELAY = 1500; // Delay time in milliseconds

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    enableScrolling = true;
                    int y = View.VISIBLE;
                    TransitionManager.beginDelayedTransition(scrollForMoreLayout, new AutoTransition());
                    scrollForMoreExpanded = true;
                    scrollForMoreCard.setVisibility(View.VISIBLE);
                    scrollForMoreText.setVisibility(y);
                    scrollForMoreArrow.setVisibility(y);
                }
            }, DELAY);
        }
        if(visibility == View.GONE && scrollForMoreExpanded == true) // fades in scrollForMoreCard, since its supposed to appear a little after the resultCard
        {
            enableScrolling = false;
            TransitionManager.beginDelayedTransition(scrollForMoreLayout, new AutoTransition());
            scrollForMoreExpanded = false;
            scrollForMoreCard.setVisibility(View.INVISIBLE);
            scrollForMoreText.setVisibility(visibility);
            scrollForMoreArrow.setVisibility(visibility);
        }
    }

    public void howResult(View view) {
        hideKeyboard();
        pythagorasScrollView.scrollTo(0, 0);
        double unknownSide, unknownSideRoot;
        if (!TextUtils.isEmpty(editSideOne.getText().toString()) && !TextUtils.isEmpty(editSideTwo.getText().toString())) {
            inputA = Double.parseDouble(editSideOne.getText().toString());
            inputB = Double.parseDouble(editSideTwo.getText().toString());
            anyValidInputReceived = true;
        } else {
            String text = "Enter in both fields!";
            fireAlert(text);
            inputA = 0;
            inputB = 0;
            anyValidInputReceived = false;
        }
        if(searchingForHypotenuse == true && anyValidInputReceived == true)
        {
            unknownSide = inputA*inputA+inputB*inputB;
            unknownSideRoot = sqrt(unknownSide);
            xText.setText("c² =");
            Log.d("SidesMan", unknownSide+"");
            xValue.setText(fmt(inputA) + "² + " + fmt(inputB) + "² = " + fmt(rnd8(unknownSide)));
            xFinalText.setText("c =");
            resultAndScrollCardVisibility(View.VISIBLE);
            xFinalValue.setText("√" + fmt(rnd8(unknownSide)) + " = " + fmt(rnd8(unknownSideRoot)));
            fillExplanation(true, unknownSide, unknownSideRoot);
        }
        else if(anyValidInputReceived == true)
        {
            if(inputA > inputB)
            {
                unknownSide = inputA*inputA-inputB*inputB;
                xValue.setText(fmt(inputA) + "² - " + fmt(inputB) + "² = " + fmt(rnd8(unknownSide)));
            }

            else
            {
                unknownSide = inputB*inputB-inputA*inputA;
                xValue.setText(fmt(inputB) + "² - " + fmt(inputA) + "² = " + fmt(rnd8(unknownSide)));
            }
            unknownSideRoot = sqrt(unknownSide);
            xText.setText("a² =");
            xFinalText.setText("a =");
            resultAndScrollCardVisibility(View.VISIBLE);
            xFinalValue.setText("√" + fmt(rnd8(unknownSide)) + " = " + fmt(rnd8(unknownSideRoot)));
            fillExplanation(false, unknownSide, unknownSideRoot);
        }

    }

    public void fillExplanation(boolean yesHypoNoSide, double unknownSide, double unknownSideRoot)
    {
        if(yesHypoNoSide == true)
        {
            pythagorasExplanationTriangle.setImageResource(R.drawable.pythagoras_triangle_hypotenuse);
            explanationWhatAreWeSearchingFor.setText("We're searching for the hypotenuse:");
            pythagorasExplanationFormula.setImageResource(R.drawable.pythagoras_formula_hypotenuse);
            pythagorasExplanationFormulaText.setText("i.e: The sum of both of the side squares of a right triangle is equal to the triangle's hypotenuse square.");
            explanationXSquaredText.setText("c² =");
            explanationXSquaredValue.setText(fmt(inputA) + "² + " + fmt(inputB) + "² = " + fmt(rnd8(unknownSide)));
            explanationXText.setText("c =");
            explanationXValue.setText("√" + fmt(rnd8(unknownSide)) + " = " + fmt(rnd8(unknownSideRoot)));
        }
        else
        {
            pythagorasExplanationTriangle.setImageResource(R.drawable.pythagoras_triangle_side);
            explanationWhatAreWeSearchingFor.setText("We're searching for one of the missing sides:");
            pythagorasExplanationFormula.setImageResource(R.drawable.pythagoras_formula_side);
            pythagorasExplanationFormulaText.setText("i.e: The subtracting the square of one of the sides from the hypotenuse squared gives the missing sides square.");
            explanationXSquaredText.setText("a² =");
            explanationXText.setText("a =");
            if(inputA > inputB)
            {
                explanationXSquaredValue.setText(fmt(inputA) + "² - " + fmt(inputB) + "² = " + fmt(rnd8(unknownSide)));
                explanationXValue.setText("√" + fmt(rnd8(unknownSide)) + " = " + fmt(rnd8(unknownSideRoot)));
            }
            else
            {
                explanationXSquaredValue.setText(fmt(inputB) + "² - " + fmt(inputA) + "² = " + fmt(rnd8(unknownSide)));
                explanationXValue.setText("√" + fmt(rnd8(unknownSide)) + " = " + fmt(rnd8(unknownSideRoot)));
            }

        }
    }

    public void help(View view) {
        AlertDialog.Builder credits =  new AlertDialog.Builder(this);
        credits.setTitle("Kaip naudoti:");
        credits.setMessage("Ši skaičiuoklė skirta surasti stačiojo trikampio nežinomą kraštinę. \n \n" + "Skaičiavimui reikia įvesti dvi žinomas kraštines, ir tada paspausti atitinkamą mygtuką ieškant trikampio įžambinės ar statinės.");
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