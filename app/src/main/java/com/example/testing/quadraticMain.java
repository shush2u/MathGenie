package com.example.testing;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.testing.databinding.QuadraticMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class quadraticMain extends drawerBaseActivity {

    QuadraticMainBinding binding;

    boolean validSubmission = false;
    int currentFragment; // 1 calc, 2 expl.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = QuadraticMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        allocateActivityTitle("Quadratic Calculator");

        replaceFragment(new quadraticCalculator(), false);
        currentFragment = 1;

        navView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        navView.getMenu().getItem(1).setEnabled(false);

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()){

                case R.id.bottom_nav_calculator:
                    if(currentFragment != 1)
                    {
                        replaceFragment(new quadraticCalculator(), true);
                        currentFragment = 1;
                    }
                    break;
                case R.id.bottom_nav_explanation:
                    if(validSubmission == true && currentFragment != 2)
                    {
                        replaceFragment(new quadraticExplanation(), true);
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

    double inputA, inputB, inputC, discriminator;

    private void setBundleData()
    {
        bundle.putDouble("inputA", inputA);
        bundle.putDouble("inputB", inputB);
        bundle.putDouble("inputC", inputC);
        bundle.putDouble("discriminator", discriminator);
    }

    public void receiveData(double receivedInputA, double receivedInputB, double receivedInputC, double receivedDiscriminator)
    {
        inputA = receivedInputA;
        inputB = receivedInputB;
        inputC = receivedInputC;
        discriminator = receivedDiscriminator;
        validateSubmission(true);
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
}