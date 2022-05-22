package com.example.testing;

import android.animation.LayoutTransition;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
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
public class pythagorasCalculator extends Fragment {

    CardView resultCard;
    TextView answerValue, answerValueSquared;

    LinearLayout alertLayout;
    CardView alertCard;
    TextView alertText;

    LinearLayout resultLayout;
    Button submitButton;

    boolean alertCardBusy = false;

    ImageView pythagorasTriangleImage;
    SwitchCompat hypotenuseSwitch;
    TextView hypotenuseSwitchText, editInput1Text, editInput2Text;
    EditText input1Text, input2Text;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public pythagorasCalculator() {
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
    public static pythagorasCalculator newInstance(String param1, String param2) {
        pythagorasCalculator fragment = new pythagorasCalculator();
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
        return inflater.inflate(R.layout.fragment_pythagoras_calculator, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        assignIDs();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(hypotenuseSwitch.isChecked())
                {
                    showResult(true);
                }
                else
                {
                    showResult(false);
                }
            }
        });

        hypotenuseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true)
                {
                    hypotenuseSwitchText.setText("Yes");
                    switchInputTexts("a", "b");
                    input2Text.setHint("b");
                    pythagorasTriangleImage.setImageResource(R.drawable.pythagoras_triangle_hypotenuse);
                }
                else
                {
                    hypotenuseSwitchText.setText("No");
                    switchInputTexts("a", "c");
                    input2Text.setHint("c");
                    pythagorasTriangleImage.setImageResource(R.drawable.pythagoras_triangle_side);
                }
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

    private void sendData(double sendInput1, double sendInput2, boolean sendHypotenuse, double sendAnswerSquare)
    {
        ((pythagorasMain)getActivity()).receiveData(sendInput1, sendInput2, sendHypotenuse, sendAnswerSquare);
        Log.i("Debug:", "CalculatorSent: " + sendHypotenuse);
    }

    private void invalidSubmission()
    {
        ((pythagorasMain)getActivity()).validateSubmission(false);
    }

    private void assignIDs()
    {
        pythagorasTriangleImage = (ImageView) getView().findViewById(R.id.pythagorasTriangleImage);

        input1Text = (EditText) getView().findViewById(R.id.editInput1);
        input2Text = (EditText) getView().findViewById(R.id.editInput2);
        hypotenuseSwitch = (SwitchCompat) getView().findViewById(R.id.hypotenuseSwitch);
        hypotenuseSwitch.setChecked(true);
        hypotenuseSwitchText = (TextView) getView().findViewById(R.id.hypotenuseSwitchText);
        editInput1Text = (TextView) getView().findViewById(R.id.editInput1Text);
        editInput2Text = (TextView) getView().findViewById(R.id.editInput2Text);

        alertLayout = (LinearLayout) getView().findViewById(R.id.alertLayout);
        alertLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        alertCard = (CardView) getView().findViewById(R.id.alertCard);
        alertText = (TextView) getView().findViewById(R.id.alertText);

        alertCard.setVisibility(View.GONE);
        alertText.setVisibility(View.GONE);

        answerValue = (TextView) getView().findViewById(R.id.answerValue);
        answerValueSquared = (TextView) getView().findViewById(R.id.answerValueSquared);
        resultLayout = (LinearLayout) getView().findViewById(R.id.result);
        resultLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        resultCard = (CardView) getView().findViewById(R.id.resultCard);

        resultCard.setVisibility(View.INVISIBLE);
        answerValue.setVisibility(View.GONE);
        answerValueSquared.setVisibility(View.GONE);

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

    private void resetAnswerText(char letter)
    {
        answerValueSquared.setText(letter + "² =");
        answerValue.setText(letter + " =");
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

        answerValueSquared.setVisibility(visibility);
        answerValue.setVisibility(visibility);
    }

    private void switchInputTexts(String first, String second)
    {
        editInput1Text.setText(first + " =");
        input1Text.setHint(first);
        editInput2Text.setText(second + " =");
        input2Text.setHint(second);
    }

    private void showResult(boolean searchingForHypotenuse)
    {
        if(searchingForHypotenuse == true)
        {
            resetAnswerText('c');
        }
        else
        {
            resetAnswerText('b');
        }

        hideKeyboard();

        boolean anyValidInputReceived;

        double input1, input2;

        if (validInput() == true)
        {
            anyValidInputReceived = true;
            input1 = Double.parseDouble(input1Text.getText().toString());
            input2 = Double.parseDouble(input2Text.getText().toString());
        }
        else
        {
            anyValidInputReceived = false;
            input1 = 0;
            input2 = 0;
        }
        if(anyValidInputReceived != true)
        {
            resultCardVisibility(View.GONE);

            boolean first, second;
            first = checkIfEntered(input1Text);
            second = checkIfEntered(input2Text);

            String text = "Enter ";

            if(first == false && second == false)
            {
                if(searchingForHypotenuse == true)
                {
                    text += "a and b!";
                }
                else
                {
                    text += "a and c!";
                }
            }
            else
            {
                text += missingInputs(first, second, searchingForHypotenuse);
            }

            invalidSubmission();
            fireAlert(text);
        }
        else if(searchingForHypotenuse == false && input1 == input2)
        {
            resultCardVisibility(View.GONE);
            invalidSubmission();
            fireAlert("The hypotenuse cannot be the same length as one of the sides!");
        }
        else
        {
            alertCard.setVisibility(View.INVISIBLE);
            alertText.setVisibility(View.GONE);
            resultCardVisibility(View.VISIBLE);

            double answerSquare;

            if(searchingForHypotenuse == true)
            {
                answerSquare = input1*input1 + input2*input2;
                switchInputTexts("a", "b");
            }
            else
            {
                if(input1 > input2)
                {
                    answerSquare = input1*input1 - input2*input2;
                    switchInputTexts("c", "a");
                }
                else
                {
                    answerSquare = input2*input2 - input1*input1;
                    switchInputTexts("a", "c");
                }
            }
            char letter;
            if(searchingForHypotenuse == true)
            {
                letter = 'c';
            }
            else
            {
                letter = 'b';
            }
            answerValueSquared.setText(letter + "² = " + fmt(rnd8(answerSquare)));
            answerValue.setText(letter + " = " + fmt(rnd8(sqrt(answerSquare))));

            sendData(input1, input2, searchingForHypotenuse, answerSquare);
        }
    }

    private boolean validInput()
    {
        if (TextUtils.isEmpty(input1Text.getText().toString()) || TextUtils.isEmpty(input2Text.getText().toString()))
        {
            return false;
        }
        else if(input1Text.getText().toString().equals("-") || input2Text.getText().toString().equals("-"))
        {
            return false;
        }
        else if(input1Text.getText().toString().equals(".") || input2Text.getText().toString().equals("."))
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

    private String missingInputs(boolean first, boolean second, boolean hypotenuse)
    {
        String output = "";

        if(first == false)
        {
            output += "a, ";
        }
        if(second == false)
        {
            if(hypotenuse == true)
            {
                output += "b, ";
            }
            else
            {
                output += "c, ";
            }
        }

        output = output.substring(0, output.length() - 2);
        output += "!";

        return output;
    }
}




