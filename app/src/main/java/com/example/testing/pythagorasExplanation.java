package com.example.testing;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.Math.sqrt;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link pythagorasExplanation#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class pythagorasExplanation extends Fragment {

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
    public static pythagorasExplanation newInstance(String param1, String param2) {
        pythagorasExplanation fragment = new pythagorasExplanation();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public pythagorasExplanation() {
        // Required empty public constructor
    }

    double input1, input2, answerSquare;
    boolean hypotenuse;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            input1 = bundle.getDouble("input1");
            input2 = bundle.getDouble("input2");
            hypotenuse = bundle.getBoolean("hypotenuse");
            answerSquare = bundle.getDouble("answerSquare");
            Log.i("Debug:", "ExplanationReceived: " + hypotenuse);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pythagoras_explanation, container, false);
    }

    //TextView testText;




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        assignIDs();
        assignTextAndValues();

    }

    TextView pythagorasExplanationSideOrHypotenuseText, pythagorasExplanationSideOrHypotenuseFormula;
    ImageView pythagorasExplanationSideOrHypotenuseImage;

    TextView pythagorasExplanationCalculation, pythagorasExplanationCalculationRoot, pythagorasExplanationCalculationFinal;

    private void assignIDs()
    {
        pythagorasExplanationSideOrHypotenuseText = (TextView) getView().findViewById(R.id.pythagorasExplanationSideOrHypotenuseText);
        pythagorasExplanationSideOrHypotenuseFormula = (TextView) getView().findViewById(R.id.pythagorasExplanationSideOrHypotenuseFormula);
        pythagorasExplanationSideOrHypotenuseImage = (ImageView) getView().findViewById(R.id.pythagorasExplanationSideOrHypotenuseImage);

        pythagorasExplanationCalculation = (TextView) getView().findViewById(R.id.pythagorasExplanationCalculation);
        pythagorasExplanationCalculationRoot = (TextView) getView().findViewById(R.id.pythagorasExplanationCalculationRoot);
        pythagorasExplanationCalculationFinal = (TextView) getView().findViewById(R.id.pythagorasExplanationCalculationFinal);
    }

    private void assignTextAndValues()
    {

        if(hypotenuse == true)
        {
            pythagorasExplanationSideOrHypotenuseText.setText("We can find the hypotenuse of a right triangle with this formula:");
            pythagorasExplanationSideOrHypotenuseFormula.setText("a² + b² = c²");
            pythagorasExplanationSideOrHypotenuseImage.setImageResource(R.drawable.pythagoras_triangle_hypotenuse);

            pythagorasExplanationCalculation.setText("c² = "+ clnFmt(rnd(input1)) + "² + " + clnFmt(rnd(input2)) + "² = " + fmt(rnd(input1*input1)) + " + " + clnFmt(rnd(input2*input2)) + " = " + fmt(rnd(answerSquare)));
            pythagorasExplanationCalculationRoot.setText("c = √" + fmt(rnd(answerSquare)) + " = " + fmt(rnd(sqrt(answerSquare))));
            pythagorasExplanationCalculationFinal.setText("c = " + fmt(rnd(sqrt(answerSquare))));
        }
        else
        {
            pythagorasExplanationSideOrHypotenuseText.setText("We can find fasdfasf hypotenuse of a right triangle with this formula:");
        }
    }

    private double rnd(double number) // round double to 5 yes i know its not 8 shut it
    {
        double roundedNumber = BigDecimal.valueOf(number).setScale(5, RoundingMode.HALF_UP).doubleValue();
        return roundedNumber;
    }



    private static String fmt(double number) // formats a double into a string to remove unnecessary fraction (.0, if applicable)
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

    private static String clnFmt(double number) // fmt that also adds parentheses around number if negative
    {
        String numberStr;
        if(number < 0)
        {
            numberStr = "(";
            numberStr += fmt(number);
            numberStr += ")";
        }
        else
        {
            numberStr = fmt(number);
        }
        return numberStr;
    }
}