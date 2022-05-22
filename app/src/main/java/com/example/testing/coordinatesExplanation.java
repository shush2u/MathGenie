package com.example.testing;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.Math.sqrt;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link coordinatesExplanation#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class coordinatesExplanation extends Fragment {

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
    public static coordinatesExplanation newInstance(String param1, String param2) {
        coordinatesExplanation fragment = new coordinatesExplanation();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public coordinatesExplanation() {
        // Required empty public constructor
    }

    double inputX1, inputX2, inputY1, inputY2, distanceSquared, middlePointX, middlePointY;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        Bundle bundle = this.getArguments();
        if (bundle != null)
        {
            inputX1 = bundle.getDouble("inputX1");
            inputX2 = bundle.getDouble("inputX2");
            inputY1 = bundle.getDouble("inputY1");
            inputY2 = bundle.getDouble("inputY2");
            distanceSquared = bundle.getDouble("distanceSquared");
            middlePointX = bundle.getDouble("middlePointX");
            middlePointY = bundle.getDouble("middlePointY");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coordinates_explanation, container, false);
    }

    //TextView testText;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        assignIDs();
        assignTextAndValues();

        ((coordinatesMain)getActivity()).showSnackbar("Scroll to see the rest!");

        /*ConstraintLayout mView = (ConstraintLayout) getView().findViewById(R.id.constraintLayout);
        mView.post(new Runnable() {
            @Override
            public void run() {
                int width = mView.getWidth();
                int height = mView.getHeight();
                Log.d("Debug:", height + "");
            }
        });*/

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

    TextView coordinatesExplanationA, coordinatesExplanationB, coordinatesExplanationC, coordinatesExplanationDistance;

    TextView explanationXCalculationNumerator, explanationXCalculationDenominator, explanationXCalculationAnswer;
    TextView explanationYCalculationNumerator, explanationYCalculationDenominator, explanationYCalculationAnswer;
    TextView explanationMiddlePointValue;

    private void assignIDs()
    {
        coordinatesExplanationA = (TextView) getView().findViewById(R.id.coordinatesExplanationA);
        coordinatesExplanationB = (TextView) getView().findViewById(R.id.coordinatesExplanationB);
        coordinatesExplanationC = (TextView) getView().findViewById(R.id.coordinatesExplanationC);
        coordinatesExplanationDistance = (TextView) getView().findViewById(R.id.coordinatesExplanationDistance);

        explanationXCalculationNumerator = (TextView) getView().findViewById(R.id.explanationXCalculationNumerator);
        explanationXCalculationAnswer = (TextView) getView().findViewById(R.id.explanationXCalculationAnswer);
        explanationYCalculationNumerator = (TextView) getView().findViewById(R.id.explanationYCalculationNumerator);
        explanationYCalculationAnswer = (TextView) getView().findViewById(R.id.explanationYCalculationAnswer);
        explanationMiddlePointValue = (TextView) getView().findViewById(R.id.explanationMiddlePointValue);
    }

    private void assignTextAndValues()
    {
        coordinatesExplanationA.setText("a = " + fmt(rnd(inputX2)) + " - " + clnFmt(rnd(inputX1)) + " = " + fmt(rnd(inputX2 - inputX1)));
        coordinatesExplanationB.setText("b = " + fmt(rnd(inputY2)) + " - " + clnFmt(rnd(inputY1)) + " = " + fmt(rnd(inputY2 - inputY1)));
        coordinatesExplanationC.setText("c² = " + clnFmt(rnd(inputX2 - inputX1)) + "² + " + clnFmt(rnd(inputY2 - inputY1)) + "² = " + fmt(rnd(distanceSquared)));
        coordinatesExplanationDistance.setText("d = c = √" + fmt(rnd(distanceSquared)) + " = " + fmt(rnd(sqrt(distanceSquared))));

        explanationXCalculationNumerator.setText(fmt(rnd(inputX1)) + " + " + clnFmt(rnd(inputX2)));
        explanationXCalculationAnswer.setText(fmt(rnd(middlePointX)));
        explanationYCalculationNumerator.setText(fmt(rnd(inputY1)) + " + " + clnFmt(rnd(inputY2)));
        explanationYCalculationAnswer.setText(fmt(rnd(middlePointY)));

        explanationMiddlePointValue.setText("(" + fmt(rnd(middlePointX)) + "; " + fmt(rnd(middlePointY)) + ")");
    }

    private double rnd(double number) // round double to 5
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