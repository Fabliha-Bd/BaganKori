package com.Fabliha.BaganKori.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Fabliha.BaganKori.R;
import com.Fabliha.BaganKori.profile.registration.view.SignInFragment;
import com.Fabliha.BaganKori.profile.registration.view.SignUpFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    FirebaseAuth firebaseAuth;
    TextView hello,tvAlreadyHaveAnAccount;
    FrameLayout frameLayout;
    Button btnProfileSignUp;
    LinearLayout linearLayout;

    public static ProfileFragment newInstance(int index) {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView textView = root.findViewById(R.id.text_profile);
        profileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();
        hello= root.findViewById(R.id.hello);
        btnProfileSignUp= root.findViewById(R.id.btnProfileSignUp);
        frameLayout= root.findViewById(R.id.registerFrameLayout);
        linearLayout=root.findViewById(R.id.linearLayout);
        tvAlreadyHaveAnAccount=root.findViewById(R.id.tvAlreadyHaveAnAccount);
        btnProfileSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignUpFragment());
            }
        });
        tvAlreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());
            }
        });

        if(firebaseUser==null)
        {
            linearLayout.setVisibility(root.VISIBLE);

        }else{
            hello.setVisibility(View.VISIBLE);
        }
        return root;
    }
    private void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction= getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }



}