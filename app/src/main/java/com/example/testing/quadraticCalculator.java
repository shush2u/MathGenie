package com.example.testing;

import android.animation.LayoutTransition;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.TextUtils;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.testing.databinding.QuadraticBinding;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import static java.lang.Math.sqrt;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link quadraticCalculator#newInstance} factory method to
 * create an instance of this fragment.
 */
public class quadraticCalculator extends Fragment {

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

    TextView calculatorReceiveTest;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public quadraticCalculator() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment quadraticCalculator.
     */
    // TODO: Rename and change types and number of parameters
    public static quadraticCalculator newInstance(String param1, String param2) {
        quadraticCalculator fragment = new quadraticCalculator();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            //testTextString = bundle.getString("test");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quadratic_calculator, container, false);
    }

    MainActivity mainActivity;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        assignIDs();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                showResult();
            }
        });
    }

    private void sendData(double sendInputA, double sendInputB, double sendInputC)
    {
        double discriminator = sendInputB * sendInputB - 4 * sendInputA * sendInputC;
        ((MainActivity)getActivity()).receiveData(sendInputA, sendInputB, sendInputC, discriminator);
    }

    private void invalidSubmission()
    {
        ((MainActivity)getActivity()).validateSubmission(false);
    }

    private void assignIDs()
    {
        inputAText = (EditText) getView().findViewById(R.id.editAInput);
        inputBText = (EditText) getView().findViewById(R.id.editBInput);
        inputCText = (EditText) getView().findViewById(R.id.editCInput);

        alertLayout = (LinearLayout) getView().findViewById(R.id.alertLayout);
        alertLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        alertCard = (CardView) getView().findViewById(R.id.alertCard);
        alertText = (TextView) getView().findViewById(R.id.alertText);

        alertCard.setVisibility(View.GONE);
        alertText.setVisibility(View.GONE);

        discriminatorValue = (TextView) getView().findViewById(R.id.discriminatorValue);
        x1Value = (TextView) getView().findViewById(R.id.x1Value);
        x2Value = (TextView) getView().findViewById(R.id.x2Value);
        resultLayout = (LinearLayout) getView().findViewById(R.id.result);
        resultLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        resultCard = (CardView) getView().findViewById(R.id.resultCard);

        resultCard.setVisibility(View.INVISIBLE);
        discriminatorValue.setVisibility(View.GONE);
        x1Value.setVisibility(View.GONE);
        x2Value.setVisibility(View.GONE);

        /*
        scrollForMoreLayout = (LinearLayout) getView().findViewById(R.id.scrollForMoreLayout);
        scrollForMoreLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        scrollForMoreCard = (CardView) getView().findViewById(R.id.scrollForMoreCard);
        scrollForMoreText = (TextView) getView().findViewById(R.id.scrollForMoreText);
        scrollForMoreArrow = (ImageView) getView().findViewById(R.id.scrollForMoreArrow);


        scrollForMoreCard.setVisibility(View.INVISIBLE);
        scrollForMoreText.setVisibility(View.GONE);
        scrollForMoreArrow.setVisibility(View.GONE);*/

        submitButton = (Button) getView().findViewById(R.id.submitButton);
    }

    /*public void giveInfo(View view) {
        AlertDialog.Builder credits =  new AlertDialog.Builder(this);
        credits.setTitle("Quadratic Calculator Guide:");
        credits.setMessage("Enter the required information below.");
        credits.setNeutralButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        credits.create().show();
    }*/

    private double rnd8(double number) // round double to 5
    {
        double roundedNumber = BigDecimal.valueOf(number).setScale(5, RoundingMode.HALF_UP).doubleValue();
        return roundedNumber;
    }

    public static String fmt(double number) // formats a double into a string to remove unnecessary fraction (.0, if applicable)
    {
        if (number == (int) number)
            return String.format("%d", (int) number);
        else
            return String.format("%s", number);
    }

    public void fireAlert(String textToDisplay)
    {
        if (alertCardBusy == false)
        {
            if(resultCard.getVisibility() == View.VISIBLE)
            {
                resultAndScrollCardVisibility(View.GONE);
            }
            alertText.setText(textToDisplay);
            alertCardBusy = true;
            int DELAY = 500; // Delay time in milliseconds
            new Handler().postDelayed(new Runnable() {
                public void run()
                {
                    alertCard.setVisibility(View.VISIBLE);
                    int v = (alertText.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;
                    TransitionManager.beginDelayedTransition(resultLayout, new AutoTransition());
                    alertText.setVisibility(v);
                    int DELAY = 3500; // Delay time in milliseconds
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run()
                        {
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
        discriminatorValue.setText("√D =");
        x1Value.setText("x₁ =");
        x2Value.setText("x₂ =");
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(submitButton.getWindowToken(), 0);
    }

    public void resultAndScrollCardVisibility(int visibility) {
        if (visibility == View.GONE)
            resultCard.setVisibility(View.INVISIBLE);
        else
            resultCard.setVisibility(View.VISIBLE);

        TransitionManager.beginDelayedTransition(resultLayout, new AutoTransition());

        // result card
        discriminatorValue.setVisibility(visibility);
        x1Value.setVisibility(visibility);
        x2Value.setVisibility(visibility);
        /*
        if (visibility == View.VISIBLE && scrollForMoreExpanded == false) // fades in scrollForMoreCard, since its supposed to appear a little after the resultCard
        {
            int DELAY = 1500; // Delay time in milliseconds

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(scrollForMoreCard.getVisibility() != View.VISIBLE)
                    {
                        enableScrolling = true;
                        int y = View.VISIBLE;
                        TransitionManager.beginDelayedTransition(scrollForMoreLayout, new AutoTransition());
                        scrollForMoreExpanded = true;
                        scrollForMoreCard.setVisibility(View.VISIBLE);
                        scrollForMoreText.setVisibility(y);
                        scrollForMoreArrow.setVisibility(y);
                    }
                }
            }, DELAY);
        }
        if (visibility == View.GONE && scrollForMoreExpanded == true) // fades out scrollForMoreCard
        {
            enableScrolling = false;
            TransitionManager.beginDelayedTransition(scrollForMoreLayout, new AutoTransition());
            scrollForMoreExpanded = false;
            scrollForMoreCard.setVisibility(View.INVISIBLE);
            scrollForMoreText.setVisibility(visibility);
            scrollForMoreArrow.setVisibility(visibility);
        }*/
    }

    private void showResult()
    {
        resetAnswerText();
        hideKeyboard();
        boolean anyValidInputReceived;
        if (!TextUtils.isEmpty(inputAText.getText().toString()) && !TextUtils.isEmpty(inputBText.getText().toString()) && !TextUtils.isEmpty(inputCText.getText().toString()))
        {
            anyValidInputReceived = true;
            inputA = Double.parseDouble(inputAText.getText().toString());
            inputB = Double.parseDouble(inputBText.getText().toString());
            inputC = Double.parseDouble(inputCText.getText().toString());
        }
        else
        {
            anyValidInputReceived = false;
        }
        if (anyValidInputReceived == false)
        {

            String text = "Enter a, b and c!";
            if (TextUtils.isEmpty(inputAText.getText().toString()) && TextUtils.isEmpty(inputBText.getText().toString()) && TextUtils.isEmpty(inputCText.getText().toString()))
                text = "Enter a, b and c!";
            else if (TextUtils.isEmpty(inputAText.getText().toString()) && TextUtils.isEmpty(inputBText.getText().toString()))
                text = "Enter a and b!";
            else if (TextUtils.isEmpty(inputBText.getText().toString()) && TextUtils.isEmpty(inputCText.getText().toString()))
                text = "Enter b and c!";
            else if (TextUtils.isEmpty(inputAText.getText().toString()) && TextUtils.isEmpty(inputCText.getText().toString()))
                text = "Enter a and C!";
            else if (TextUtils.isEmpty(inputAText.getText().toString()))
                text = "Enter a!";
            else if (TextUtils.isEmpty(inputBText.getText().toString()))
                text = "Enter b!";
            else if (TextUtils.isEmpty(inputCText.getText().toString()))
                text = "Enter c!";
            fireAlert(text);
        }
        else if (inputA == 0 || inputB == 0 || inputC == 0)
        {
            String text = "Inputs cannot be 0!";
            if (inputA == 0 && inputB == 0 && inputC == 0)
                text = "Inputs a, b and c are 0!";
            else if (inputA == 0 && inputB == 0)
                text = "Inputs a and b are 0";
            else if (inputB == 0 && inputC == 0)
                text = "Inputs b and c are 0";
            else if (inputA == 0 && inputC == 0)
                text = "Inputs a and c are 0";
            else if (inputA == 0)
                text = "Input a is 0!";
            else if (inputB == 0)
                text = "Input b is 0!";
            else if (inputC == 0)
                text = "Input c is 0!";
            fireAlert(text);
        }
        else if ((inputB * inputB) - (4 * inputA * inputC) > -1 && anyValidInputReceived == true)
        {
            resultAndScrollCardVisibility(View.VISIBLE);
            double discriminator = inputB * inputB - 4 * inputA * inputC;
            double discriminatorRoot = sqrt(discriminator);
            double x1 = (-inputB - discriminatorRoot) / (2 * inputA);
            double x2 = (-inputB + discriminatorRoot) / (2 * inputA);

            discriminatorValue.setText("√D = " + "√" + fmt(rnd8(discriminator)) + " = " + fmt(rnd8(sqrt(discriminator))) + "");
            x1Value.setText("x₁ = " + fmt(rnd8(x1)));
            x2Value.setText("x₂ = " + fmt(rnd8(x2)));
        }
        else if ((inputB * inputB) - (4 * inputA * inputC) < -1)
        {
            resultAndScrollCardVisibility(View.VISIBLE);
            double discriminator = inputB * inputB - 4 * inputA * inputC;
            discriminatorValue.setText("√D = " + fmt(rnd8(discriminator)));
            x1Value.setText("D < 0");
            x2Value.setText("No answers");
        }
        else
        {
            resultAndScrollCardVisibility(View.VISIBLE);
            anyValidInputReceived = false;
            discriminatorValue.setText("Shouldnt be here");
        }
        if(anyValidInputReceived == true)
        {
            alertCard.setVisibility(View.INVISIBLE);
            alertText.setVisibility(View.GONE);
            sendData(inputA, inputB, inputC);
        }
        else
        {
            invalidSubmission();
        }

    }
}




