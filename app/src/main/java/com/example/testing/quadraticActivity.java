package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.testing.databinding.ActivityQuadraticBinding;

public class quadraticActivity extends DrawerBaseActivity {

    ActivityQuadraticBinding activityQuadraticBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityQuadraticBinding = activityQuadraticBinding.inflate(getLayoutInflater());
        setContentView(activityQuadraticBinding.getRoot());
        allocateActivityTitle("Quadratic");
    }
}