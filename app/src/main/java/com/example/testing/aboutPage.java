package com.example.testing;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.testing.databinding.AboutPageBinding;

public class aboutPage extends drawerBaseActivity {

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