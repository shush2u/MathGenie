package com.example.testing;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.testing.databinding.PythagorasMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class pythagorasMain extends drawerBaseActivity {

    PythagorasMainBinding binding;

    boolean validSubmission = false;
    int currentFragment; // 1 calc, 2 expl.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = PythagorasMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        allocateActivityTitle("Pythagoras Calculator");

        replaceFragment(new pythagorasCalculator(), false);
        currentFragment = 1;

        navView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        navView.getMenu().getItem(1).setEnabled(false);

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()){

                case R.id.bottom_nav_calculator:
                    if(currentFragment != 1)
                    {
                        replaceFragment(new pythagorasCalculator(), true);
                        currentFragment = 1;
                    }
                    break;
                case R.id.bottom_nav_explanation:
                    if(validSubmission == true && currentFragment != 2)
                    {
                        replaceFragment(new pythagorasExplanation(), true);
                        currentFragment = 2;
                    }
                    break;
            }
            return true;
        });
    }

    Bundle bundle;

    private void replaceFragment(Fragment fragment, boolean animationNeeded)
    {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        bundle = new Bundle();
        setBundleData();

        fragment.setArguments(bundle);
        fragmentTransaction.addToBackStack(null);

        if(animationNeeded == true)
        {
            if(currentFragment == 1)
            {
                fragmentTransaction.setCustomAnimations(R.anim.fragment_enter_right_to_left, R.anim.fragment_exit_right_to_left);
            }
            else
            {
                fragmentTransaction.setCustomAnimations(R.anim.fragment_enter_left_to_right, R.anim.fragment_exit_left_to_right);
            }

            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        }

        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    double input1, input2, answerSquare;
    boolean hypotenuse;

    private void setBundleData()
    {
        bundle.putDouble("input1", input1);
        bundle.putDouble("input2", input2);
        bundle.putDouble("answerSquare", answerSquare);
        bundle.putBoolean("hypotenuse", hypotenuse);
        Log.i("Debug:", "MainSet: " + hypotenuse);
    }

    public void receiveData(double receivedInput1, double receivedInput2, boolean receivedHypotenuse, double receivedAnswerSquare)
    {
        input1 = receivedInput1;
        input2 = receivedInput2;
        hypotenuse = receivedHypotenuse;
        answerSquare = receivedAnswerSquare;
        validateSubmission(true);
        uniqueExplanation = true;
    }

    BottomNavigationView navView;

    public void validateSubmission(boolean valid)
    {
        if(valid == true)
        {
            validSubmission = true;
            navView.getMenu().getItem(1).setEnabled(true);
        }
        else
        {
            validSubmission = false;
            navView.getMenu().getItem(1).setEnabled(false);
        }
    }

    boolean uniqueExplanation = true;

    public void showSnackbar(String textToDisplay)
    {
        if(uniqueExplanation == true)
        {
            // Create snackbar and position it over bottomNav, change animation to fadein
            Snackbar scrollAlert = Snackbar.make(findViewById(R.id.rootLayout), textToDisplay, Snackbar.LENGTH_LONG);
            scrollAlert.setAnchorView(navView);
            scrollAlert.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE);

            // Center Text in snackbar
            View scrollAlertView = scrollAlert.getView();
            TextView scrollAlertText = (TextView) scrollAlertView.findViewById(com.google.android.material.R.id.snackbar_text);
            scrollAlertText.setGravity(Gravity.CENTER_HORIZONTAL);
            scrollAlertText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            // Show snackbar
            scrollAlert.show();
            uniqueExplanation = false;
        }
    }
}