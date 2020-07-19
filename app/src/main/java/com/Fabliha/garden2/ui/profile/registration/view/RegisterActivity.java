package com.Fabliha.garden2.ui.profile.registration.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.Fabliha.garden2.R;


public class RegisterActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        frameLayout= findViewById(R.id.registerFrameLayout);
        setFragment(new SignInFragment());
    }
    private void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }

}