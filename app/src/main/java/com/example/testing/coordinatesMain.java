package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.testing.databinding.CoordinatesMainBinding;
import com.example.testing.databinding.QuadraticMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class coordinatesMain extends drawerBaseActivity {

    CoordinatesMainBinding binding;

    boolean validSubmission = false;
    int currentFragment; // 1 calc, 2 expl.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = CoordinatesMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        allocateActivityTitle("Coordinates Calculator");

        replaceFragment(new coordinatesCalculator(), false);
        currentFragment = 1;

        navView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        navView.getMenu().getItem(1).setEnabled(false);

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()){

                case R.id.bottom_nav_calculator:
                    if(currentFragment != 1)
                    {
                        replaceFragment(new coordinatesCalculator(), true);
                        currentFragment = 1;
                    }
                    break;
                case R.id.bottom_nav_explanation:
                    if(validSubmission == true && currentFragment != 2)
                    {
                        replaceFragment(new coordinatesExplanation(), true);
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

    double inputX1, inputX2, inputY1, inputY2, distanceSquared, middlePointX, middlePointY;

    private void setBundleData()
    {
        bundle.putDouble("inputX1", inputX1);
        bundle.putDouble("inputX2", inputX2);
        bundle.putDouble("inputY1", inputY1);
        bundle.putDouble("inputY2", inputY2);
        bundle.putDouble("distanceSquared", distanceSquared);
        bundle.putDouble("middlePointX", middlePointX);
        bundle.putDouble("middlePointY", middlePointY);
    }

    public void receiveData(double x1, double x2, double y1, double y2, double d2, double midX, double midY)
    {
        inputX1 = x1;
        inputX2 = x2;
        inputY1 = y1;
        inputY2 = y2;
        distanceSquared = d2;
        middlePointX = midX;
        middlePointY = midY;
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