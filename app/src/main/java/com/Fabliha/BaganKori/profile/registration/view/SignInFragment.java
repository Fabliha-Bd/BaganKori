package com.Fabliha.BaganKori.profile.registration.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.Fabliha.BaganKori.MainActivity;
import com.Fabliha.BaganKori.R;
import com.Fabliha.BaganKori.profile.ProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignInFragment extends Fragment {

    private FrameLayout parentFrameLayout;
    private TextView tvDontHaveAnAccount;
    private TextView forgetPassword;
    FirebaseAuth firebaseAuth;

    private EditText etEmail;
    private EditText etPassword;
    private Button btnSignIn;

    private ImageButton btnCross;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    private ProgressBar progressBar;

    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_sign_in, container, false);
        tvDontHaveAnAccount= view.findViewById(R.id.tvDontHaveAnAccount);
        parentFrameLayout= getActivity().findViewById(R.id.registerFrameLayout);
        etEmail= view.findViewById(R.id.etEmail);
        etPassword= view.findViewById(R.id.etPassword);
        btnSignIn= view.findViewById(R.id.btnSignIn);
        progressBar= view.findViewById(R.id.signInProgressBar);
        forgetPassword= view.findViewById(R.id.tvForgetPassword);
        firebaseAuth= FirebaseAuth.getInstance();
        btnCross= view.findViewById(R.id.btnCross);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvDontHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignUpFragment());
            }
        });
        btnCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new ProfileFragment());
            }
        });
        etEmail.addTextChangedListener(new TextWatcher() {
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
        etPassword.addTextChangedListener(new TextWatcher() {
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
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailAndPassword();
            }
        });
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new ResetPasswordFragment());
            }
        });

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
        if(!TextUtils.isEmpty(etEmail.getText()))
        {
            if(!TextUtils.isEmpty(etPassword.getText())){
                btnSignIn.setEnabled(true);
                btnSignIn.setTextColor(Color.rgb(255,255,255));
            }else{
                btnSignIn.setTextColor(Color.argb(50,255,255,255));
                btnSignIn.setEnabled(false);
            }

        }else{
            btnSignIn.setTextColor(Color.argb(50,255,255,255));
            btnSignIn.setEnabled(false);
        }
    }
    private  void checkEmailAndPassword(){
        if(etEmail.getText().toString().matches(emailPattern)){
            if(etPassword.length()>=8)
            {
                progressBar.setVisibility(View.VISIBLE);
                btnSignIn.setEnabled(false);
                btnSignIn.setTextColor(Color.argb(50,255,255,255));
                firebaseAuth.signInWithEmailAndPassword(etEmail.getText().toString(),etPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful())
                                {
                                    Intent intent= new Intent(getActivity(), MainActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();

                                }else{
                                    progressBar.setVisibility(View.INVISIBLE);
                                    btnSignIn.setEnabled(false);
                                    btnSignIn.setTextColor(Color.argb(50,255,255,255));
                                    String error= task.getException().getMessage();
                                    Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }else{
                Toast.makeText(getActivity(),"Incorrect Email or Password!!",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getActivity(),"Incorrect Email or Password!!",Toast.LENGTH_SHORT).show();

        }
    }
}