package com.example.testing;

import android.animation.LayoutTransition;
import android.content.Context;
import android.os.Bundle;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import static java.lang.Math.sqrt;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link coordinatesCalculator#newInstance} factory method to
 * create an instance of this fragment.
 */
public class coordinatesCalculator extends Fragment {

    EditText inputX1Text, inputX2Text, inputY1Text, inputY2Text;
    Button submitButton;

    LinearLayout alertLayout;
    CardView alertCard;
    TextView alertText;
    boolean alertCardBusy = false;

    LinearLayout resultLayout;
    CardView resultCard;
    TextView distanceValue, middlePointValueText, middlePointValueX, middlePointValueY;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public coordinatesCalculator() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment coordinatesCalculator.
     */
    // TODO: Rename and change types and number of parameters
    public static coordinatesCalculator newInstance(String param1, String param2) {
        coordinatesCalculator fragment = new coordinatesCalculator();
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
        return inflater.inflate(R.layout.fragment_coordinates_calculator, container, false);
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

    private void sendData(double x1, double x2, double y1, double y2, double d2, double midX, double midY)
    {
        ((coordinatesMain)getActivity()).receiveData(x1, x2, y1, y2, d2, midX, midY);
    }

    private void invalidSubmission()
    {
        ((coordinatesMain)getActivity()).validateSubmission(false);
    }

    private void assignIDs()
    {
        inputX1Text = (EditText) getView().findViewById(R.id.editInputX1);
        inputX2Text = (EditText) getView().findViewById(R.id.editInputX2);
        inputY1Text = (EditText) getView().findViewById(R.id.editInputY1);
        inputY2Text = (EditText) getView().findViewById(R.id.editInputY2);
        submitButton = (Button) getView().findViewById(R.id.submitButton);

        alertLayout = (LinearLayout) getView().findViewById(R.id.alertLayout);
        alertCard = (CardView) getView().findViewById(R.id.alertCard);
        alertText = (TextView) getView().findViewById(R.id.alertText);

        alertLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        alertCard.setVisibility(View.INVISIBLE);
        alertText.setVisibility(View.GONE);

        resultLayout = (LinearLayout) getView().findViewById(R.id.resultLayout);
        resultCard = (CardView) getView().findViewById(R.id.resultCard);
        distanceValue = (TextView) getView().findViewById(R.id.distanceValue);
        middlePointValueText = (TextView) getView().findViewById(R.id.middlePointValueText);
        middlePointValueX = (TextView) getView().findViewById(R.id.middlePointValueX);
        middlePointValueY = (TextView) getView().findViewById(R.id.middlePointValueY);

        resultLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        resultCard.setVisibility(View.INVISIBLE);
        distanceValue.setVisibility(View.GONE);
        middlePointValueText.setVisibility(View.GONE);
        middlePointValueX.setVisibility(View.GONE);
        middlePointValueY.setVisibility(View.GONE);
    }

    private double rnd(double number) // round double to 5
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

    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(submitButton.getWindowToken(), 0);
    }

    private void resultCardVisibility(int visibility)
    {
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

        distanceValue.setVisibility(visibility);
        middlePointValueText.setVisibility(visibility);
        middlePointValueX.setVisibility(visibility);
        middlePointValueY.setVisibility(visibility);
    }

    private void showResult()
    {
        hideKeyboard();
        if(validInput() == true)
        {
            double inputX1 = Double.parseDouble(inputX1Text.getText().toString());
            double inputX2 = Double.parseDouble(inputX2Text.getText().toString());
            double inputY1 = Double.parseDouble(inputY1Text.getText().toString());
            double inputY2 = Double.parseDouble(inputY2Text.getText().toString());
            if(inputX1 == inputX2 && inputY1 == inputY2)
            {
                resultCardVisibility(View.GONE);
                fireAlert("The two points are the same!");
            }
            else
            {
                double distance = ((inputX2-inputX1)*(inputX2-inputX1)) + ((inputY2-inputY1)*(inputY2-inputY1));
                double x = (inputX1+inputX2)/2;
                double y = (inputY1+inputY2)/2;

                distanceValue.setText("d = " + "√" + fmt(rnd(distance)) + " = " + fmt(rnd(sqrt(distance))));
                middlePointValueX.setText("x = " + fmt(rnd(x)));
                middlePointValueY.setText("y = " + fmt(rnd(y)));

                resultCardVisibility(View.VISIBLE);
                alertCard.setVisibility(View.INVISIBLE);
                alertText.setVisibility(View.GONE);
                sendData(inputX1, inputX2, inputY1, inputY2, distance, x, y);
            }
        }
        else
        {
            resultCardVisibility(View.GONE);

            boolean x1, x2, y1, y2;
            x1 = checkIfEntered(inputX1Text);
            x2 = checkIfEntered(inputX2Text);
            y1 = checkIfEntered(inputY1Text);
            y2 = checkIfEntered(inputY2Text);

            String text = "Enter ";
            if(x1 == false && x2 == false && y1 == false && y2 == false)
            {
                text += "all of the point coordinates!";
            }
            else
            {
                text += missingInputs(x1, x2, y1, y2);
            }
            fireAlert(text);
            invalidSubmission();
        }

    }

    private boolean validInput()
    {
        if (TextUtils.isEmpty(inputX1Text.getText().toString()) || TextUtils.isEmpty(inputX2Text.getText().toString()) || TextUtils.isEmpty(inputY1Text.getText().toString()) || TextUtils.isEmpty(inputY2Text.getText().toString()))
        {
            return false;
        }
        else if(inputX1Text.getText().toString().equals("-") || inputX2Text.getText().toString().equals("-") || inputY1Text.getText().toString().equals("-") || inputY2Text.getText().toString().equals("-"))
        {
            return false;
        }
        else if(inputX1Text.getText().toString().equals(".") || inputX2Text.getText().toString().equals(".") || inputY1Text.getText().toString().equals(".") || inputY2Text.getText().toString().equals("."))
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

    private String missingInputs(boolean x1, boolean x2, boolean y1, boolean y2)
    {
        String output = "";

        if(x1 == false)
        {
            output += "x₁, ";
        }
        if(x2 == false)
        {
            output += "x₂, ";
        }
        if(y1 == false)
        {
            output += "y₁, ";
        }
        if(y2 == false)
        {
            output += "y₂, ";
        }

        output = output.substring(0, output.length() - 2);
        output += "!";

        return output;
    }
}




