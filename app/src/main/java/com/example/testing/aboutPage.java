package com.example.testing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.testing.databinding.AboutPageBinding;
import com.example.testing.databinding.QuadraticMainBinding;

public class aboutPage extends DrawerBaseActivity {

    AboutPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  // this line removes the STATUS BAR AT THE TOP (not the action bar)
        binding = AboutPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        allocateActivityTitle("About");

    }
}