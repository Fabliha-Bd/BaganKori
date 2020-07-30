package com.Fabliha.BaganKori.profile.registration.view;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.Fabliha.BaganKori.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ResetPasswordFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    private Button btnBack;
    private Button btnReset;
    private EditText etForgetEmail;

    private FrameLayout parentFrameLayout;


    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_reset_password, container, false);
        parentFrameLayout= getActivity().findViewById(R.id.registerFrameLayout);
        etForgetEmail= view.findViewById(R.id.etForgetEmail);
        btnReset= view.findViewById(R.id.btnResetPass);
        btnBack= view.findViewById(R.id.btnBack);
        firebaseAuth= FirebaseAuth.getInstance();

        etForgetEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnReset.setEnabled(false);
                btnReset.setTextColor(Color.argb(50,255,255,255));
                firebaseAuth.sendPasswordResetEmail(etForgetEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getActivity(),"Password Reset Request Successfull. Check your email! ",Toast.LENGTH_SHORT).show();
                        }else{
                            String error= task.getException().getMessage();
                            Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();

                        }
                        btnReset.setEnabled(true);
                        btnReset.setTextColor(Color.rgb(255,255,255));
                    }
                });
            }
        });

        return  view;
    }

    private void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction= getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
    private void checkInputs()
    {
        if(TextUtils.isEmpty(etForgetEmail.getText().toString()))
        {
            btnReset.setEnabled(false);
            btnReset.setTextColor(Color.argb(50,255,255,255));
        }else{
            btnReset.setEnabled(true);
            btnReset.setTextColor(Color.rgb(255,255,255));
        }
    }
}