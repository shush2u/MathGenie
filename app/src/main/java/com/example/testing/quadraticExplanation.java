package com.example.testing;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.Math.exp;
import static java.lang.Math.sqrt;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link quadraticExplanation#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class quadraticExplanation extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment quadraticExplanation.
     */
    // TODO: Rename and change types and number of parameters
    public static quadraticExplanation newInstance(String param1, String param2) {
        quadraticExplanation fragment = new quadraticExplanation();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public quadraticExplanation() {
        // Required empty public constructor
    }

    double inputA, inputB, inputC, discriminator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            inputA = bundle.getDouble("inputA");
            inputB = bundle.getDouble("inputB");
            inputC = bundle.getDouble("inputC");
            discriminator = bundle.getDouble("discriminator");
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quadratic_explanation, container, false);
    }

    //TextView testText;




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        assignIDs();
        assignTextAndValues();

    }

    TextView explanationDiscriminatorCalculation;

    TextView explanationX1Text;
    TextView explanationX1CalculationNumerator;
    TextView explanationX1CalculationDenominator;
    TextView explanationX1CalculationAnswer;
    TextView explanationX1EqualSign;

    TextView explanationX2Text;
    TextView explanationX2CalculationNumerator;
    TextView explanationX2CalculationDenominator;
    TextView explanationX2CalculationAnswer;
    TextView explanationX2EqualSign;

    private void assignIDs()
    {
        explanationDiscriminatorCalculation = (TextView) getView().findViewById(R.id.explanationDiscriminatorCalculation);

        explanationX1Text = (TextView) getView().findViewById(R.id.explanationX1Text);
        explanationX1CalculationNumerator = (TextView) getView().findViewById(R.id.explanationX1CalculationNumerator);
        explanationX1CalculationDenominator = (TextView) getView().findViewById(R.id.explanationX1CalculationDenominator);
        explanationX1CalculationAnswer = (TextView) getView().findViewById(R.id.explanationX1CalculationAnswer);
        explanationX1EqualSign = (TextView) getView().findViewById(R.id.explanationX1EqualSign);

        explanationX2Text = (TextView) getView().findViewById(R.id.explanationX2Text);
        explanationX2CalculationNumerator = (TextView) getView().findViewById(R.id.explanationX2CalculationNumerator);
        explanationX2CalculationDenominator = (TextView) getView().findViewById(R.id.explanationX2CalculationDenominator);
        explanationX2CalculationAnswer = (TextView) getView().findViewById(R.id.explanationX2CalculationAnswer);
        explanationX2EqualSign = (TextView) getView().findViewById(R.id.explanationX2EqualSign);
    }

    private void assignTextAndValues()
    {
        if ((inputB * inputB) - (4 * inputA * inputC) > -1)
        {
            double discriminatorRoot = sqrt(discriminator);
            double x1 = (-inputB - discriminatorRoot) / (2 * inputA);
            double x2 = (-inputB + discriminatorRoot) / (2 * inputA);
            explanationDiscriminatorCalculation.setText("D = " + fmt(rnd8(inputB)) + "²" + " - 4 * " + fmt(rnd8(inputA)) + " * " + fmt(rnd8(inputC)) +" = " + fmt(rnd8((inputB*inputB))) + " - " + fmt(rnd8((4*inputA*inputC))) + " = " + fmt(rnd8(discriminator)));
            explanationX1CalculationNumerator.setText("-" + fmt(rnd8(inputB)) + " + " + "√" + fmt(rnd8(discriminator)));
            explanationX1CalculationDenominator.setText("2 * " + fmt(rnd8(inputA)));
            explanationX1CalculationAnswer.setText("" + fmt(rnd8(x1)));
            explanationX2CalculationNumerator.setText("-" + fmt(rnd8(inputB)) + " - " + "√" + fmt(rnd8(discriminator)));
            explanationX2CalculationDenominator.setText("2 * " + fmt(rnd8(inputA)));
            explanationX2CalculationAnswer.setText(""+ fmt(rnd8(x2)));
        }
        else if ((inputB * inputB) - (4 * inputA * inputC) < -1)
        {
            explanationDiscriminatorCalculation.setText("D = " + fmt(rnd8(inputB)) + "²" + " - 4 * " + fmt(rnd8(inputA)) + " * " + fmt(rnd8(inputC)) +" = " + fmt(rnd8((inputB*inputB))) + " - " + fmt(rnd8((4*inputA*inputC))) + " = " + fmt(rnd8(discriminator)));
            hideFullAnswer();
        }
    }

    private void hideFullAnswer()
    {
        explanationX1Text.setText("D = " + fmt(rnd8(discriminator)));
        explanationX1CalculationAnswer.setText("");
        explanationX1CalculationNumerator.setText("");
        explanationX1CalculationDenominator.setText("");
        explanationX1EqualSign.setText("");

        explanationX2Text.setText("If the discriminator is negative, there are no answers to the equation!");
        explanationX2EqualSign.setText("");
        explanationX2CalculationAnswer.setText("");
        explanationX2CalculationNumerator.setText("");
        explanationX2CalculationDenominator.setText("");
    }

    public double rnd8(double number) // round double to 8
    {
        double roundedNumber = BigDecimal.valueOf(number).setScale(8, RoundingMode.HALF_UP).doubleValue();
        return roundedNumber;
    }



    public static String fmt(double number) // formats a double into a string to remove unnecessary fraction (.0, if applicable)
    {
        String numberToReturn;
        if (number == (int) number) // 6, 5, 48, etc
        {
            numberToReturn = String.format("%d", (int) number);
        }
        else // 6.28, 5.8486
        {
            numberToReturn = String.format("%s", number);
        }
        return numberToReturn;
    }
}