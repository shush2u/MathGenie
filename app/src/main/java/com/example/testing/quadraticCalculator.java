package com.example.testing;

import android.animation.LayoutTransition;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.TextUtils;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


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

    CardView resultCard;
    TextView x1Value, x2Value, discriminatorValue;

    LinearLayout alertLayout;
    CardView alertCard;
    TextView alertText;

    LinearLayout resultLayout;
    Button submitButton;

    boolean alertCardBusy = false;

    double inputA, inputB, inputC;
    EditText inputAText, inputBText, inputCText;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quadratic_calculator, container, false);
    }

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

        disableBackButton(view, savedInstanceState);
    }

    private void disableBackButton(View view, @Nullable Bundle savedInstanceState) // does what the name says :P, credit to "Tejas Mehta" on StackOverflow
    {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();

        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        //Toast.makeText(getActivity(), "Back Pressed", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void sendData(double sendInputA, double sendInputB, double sendInputC)
    {
        double discriminator = sendInputB * sendInputB - 4 * sendInputA * sendInputC;
        ((quadraticMain)getActivity()).receiveData(sendInputA, sendInputB, sendInputC, discriminator);
    }

    private void invalidSubmission()
    {
        ((quadraticMain)getActivity()).validateSubmission(false);
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

        submitButton = (Button) getView().findViewById(R.id.submitButton);
    }

    private double rnd8(double number) // round double to 5
    {
        double roundedNumber = BigDecimal.valueOf(number).setScale(5, RoundingMode.HALF_UP).doubleValue();
        return roundedNumber;
    }

    private static String fmt(double number) // formats a double into a string to remove unnecessary fraction (.0, if applicable)
    {
        if (number == (int) number)
            return String.format("%d", (int) number);
        else
            return String.format("%s", number);
    }

    private void fireAlert(String textToDisplay)
    {
        alertText.setText(textToDisplay);
        if(alertCard.getVisibility() != View.VISIBLE)
        {
            alertCard.setVisibility(View.VISIBLE);
            TransitionManager.beginDelayedTransition(resultLayout, new AutoTransition());

            alertText.setVisibility(View.VISIBLE);
        }
    }

    private void resetAnswerText()
    {
        discriminatorValue.setText("√D =");
        x1Value.setText("x₁ =");
        x2Value.setText("x₂ =");
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(submitButton.getWindowToken(), 0);
    }

    private void resultCardVisibility(int visibility) {
        if (visibility == View.GONE)
            resultCard.setVisibility(View.INVISIBLE);
        else
            resultCard.setVisibility(View.VISIBLE);

        if(alertCard.getVisibility() == View.VISIBLE && visibility == View.VISIBLE)
        {
            alertCard.setVisibility(View.INVISIBLE);
            alertText.setVisibility(View.GONE);
        }

        TransitionManager.beginDelayedTransition(resultLayout, new AutoTransition());

        discriminatorValue.setVisibility(visibility);
        x1Value.setVisibility(visibility);
        x2Value.setVisibility(visibility);
    }

    private void showResult()
    {
        resetAnswerText();
        hideKeyboard();
        boolean anyValidInputReceived;
        if (validInput() == true)
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
            resultCardVisibility(View.GONE);

            boolean a, b, c;
            a = checkIfEntered(inputAText);
            b = checkIfEntered(inputBText);
            c = checkIfEntered(inputCText);

            String text = "Enter ";

            if(a == false && b == false && c == false)
            {
                text += "a, b and c!";
            }
            else if(a == true && b == true && c == true)
            {
                assignMissingInputs(a, b, c);
            }

            if (inputA == 0 || inputB == 0 || inputC == 0)
            {
                text = "a, b or c cannot be 0!";
            }
            else
            {
                text += missingInputs(a, b, c);
            }

            fireAlert(text);
        }
        else if ((inputB * inputB) - (4 * inputA * inputC) > -1 && anyValidInputReceived == true)
        {
            resultCardVisibility(View.VISIBLE);
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
            resultCardVisibility(View.VISIBLE);
            double discriminator = inputB * inputB - 4 * inputA * inputC;
            discriminatorValue.setText("√D = " + fmt(rnd8(discriminator)));
            x1Value.setText("D < 0");
            x2Value.setText("No answers");
        }
        else
        {
            resultCardVisibility(View.VISIBLE);
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

    private boolean validInput()
    {
        if (TextUtils.isEmpty(inputAText.getText().toString()) || TextUtils.isEmpty(inputBText.getText().toString()) || TextUtils.isEmpty(inputCText.getText().toString()))
        {
            return false;
        }
        else if(inputAText.getText().toString().equals("-") || inputBText.getText().toString().equals("-") || inputCText.getText().toString().equals("-"))
        {
            return false;
        }
        else if(inputAText.getText().toString().equals(".") || inputBText.getText().toString().equals(".") || inputCText.getText().toString().equals("."))
        {
            return false;
        }
        else if(inputAText.getText().toString().equals("0") || inputBText.getText().toString().equals("0") || inputCText.getText().toString().equals("0"))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    private boolean checkIfEntered(EditText editText)
    {
        if(TextUtils.isEmpty(editText.getText().toString()) || editText.getText().toString().equals("-") || editText.getText().toString().equals("."))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    private String missingInputs(boolean a, boolean b, boolean c)
    {
        String output = "";

        if(a == false)
        {
            output += "a, ";
        }
        if(b == false)
        {
            output += "b, ";
        }
        if(c == false)
        {
            output += "c, ";
        }

        output = output.substring(0, output.length() - 2);
        output += "!";

        return output;
    }
    private void assignMissingInputs(boolean a, boolean b, boolean c)
    {

        inputA = Double.parseDouble(inputAText.getText().toString());

        inputB = Double.parseDouble(inputBText.getText().toString());

        inputC = Double.parseDouble(inputCText.getText().toString());

    }


}




