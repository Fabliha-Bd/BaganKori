package com.Fabliha.BaganKori.profile.registration.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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
import com.Fabliha.BaganKori.profile.registration.viewModel.SignUpFragmentViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class SignUpFragment extends Fragment {

    SignUpFragmentViewModel signUpFragmentViewModel;

    private FrameLayout parentFrameLayout;
    private TextView tvAlreadyHaveAnAccount;
    private EditText etSignUpEmail;
    private EditText etSignUpFullName;
    private EditText etSignUpPassword;
    private EditText etSignUpConfPassword;
    private Button btnSignUp;
    private ProgressBar progressBar;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    private String email,fullname,password,confpassword;
    private ImageButton btnCross;

    public SignUpFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        signUpFragmentViewModel= new ViewModelProvider(this).get(SignUpFragmentViewModel.class);
        tvAlreadyHaveAnAccount = view.findViewById(R.id.tvAlreadyHaveAnAccount);
        parentFrameLayout = getActivity().findViewById(R.id.registerFrameLayout);
        etSignUpEmail= view.findViewById(R.id.etSignUpEmail);
        etSignUpFullName= view.findViewById(R.id.etSignUpFullName);
        etSignUpPassword= view.findViewById(R.id.etSignUpPassword);
        etSignUpConfPassword= view.findViewById(R.id.etSignUpConfPassword);
        btnSignUp= view.findViewById(R.id.btnSignUp);
        progressBar= view.findViewById(R.id.signUpProgressBar);
        btnCross=view.findViewById(R.id.btnCross);

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvAlreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());
            }
        });
        btnCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new ProfileFragment());
            }
        });

        etSignUpEmail.addTextChangedListener(new TextWatcher() {
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
        etSignUpFullName.addTextChangedListener(new TextWatcher() {
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
        etSignUpPassword.addTextChangedListener(new TextWatcher() {
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
        etSignUpConfPassword.addTextChangedListener(new TextWatcher() {
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
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailAndPassword();
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slideout_from_right);
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }
    private void checkInputs()
    {
        Log.v("SignUp","email "+ etSignUpEmail.getText().toString()+" password "+ etSignUpPassword.getText().toString());

        if(!TextUtils.isEmpty(etSignUpEmail.getText()))
        {
            if(!TextUtils.isEmpty(etSignUpFullName.getText())){
                if(!TextUtils.isEmpty(etSignUpPassword.getText()) && etSignUpPassword.length()>=8){
                    if(!TextUtils.isEmpty(etSignUpConfPassword.getText()) ){

                        btnSignUp.setEnabled(true);
                        btnSignUp.setTextColor(Color.rgb(255,255,255));
                    }else {
                        btnSignUp.setTextColor(Color.argb(50,255,255,255));
                        btnSignUp.setEnabled(false);
                    }

                }else{
                    btnSignUp.setTextColor(Color.argb(50,255,255,255));
                    btnSignUp.setEnabled(false);

                }
            }else{
                btnSignUp.setTextColor(Color.argb(50,255,255,255));
                btnSignUp.setEnabled(false);

            }

        }else{
            btnSignUp.setTextColor(Color.argb(50,255,255,255));
            btnSignUp.setEnabled(false);

        }
    }
    private void checkEmailAndPassword()
    {
        Log.v("SignUp","email "+ etSignUpEmail.getText().toString()+" password "+ etSignUpPassword.getText());
        if(etSignUpEmail.getText().toString().matches(emailPattern))
        {
            if(etSignUpPassword.getText().toString().equals(etSignUpConfPassword.getText().toString())){

                progressBar.setVisibility(View.VISIBLE);
                btnSignUp.setEnabled(false);
                UserSignUp(etSignUpEmail.getText().toString(),etSignUpPassword.getText().toString());


            }else {
                Log.v("SignUp","password "+ etSignUpPassword.getText().toString()+" confpassword "+ etSignUpConfPassword.getText().toString());

                etSignUpPassword.setError("Password doesn't matched!!");

            }
        }else{
            etSignUpEmail.setError("Invalid Email!!");

        }
    }
    private void UserSignUp(String email,String password)
    {

        btnSignUp.setEnabled(false);
        btnSignUp.setTextColor(Color.argb(50,255,255,255));

        Log.v("SignUp","email "+ email+" password "+ password);
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Map<Object,String> userdata= new HashMap<>();
                            userdata.put("fullname",etSignUpFullName.getText().toString());
                            firebaseFirestore.collection("USERS")
                                    .add(userdata)
                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {

                                            if(task.isSuccessful())
                                            {
                                                Intent intent= new Intent(getActivity(), MainActivity.class);
                                                startActivity(intent);
                                                getActivity().finish();
                                            }else{
                                                progressBar.setVisibility(View.INVISIBLE);
                                                String error= task.getException().getMessage();
                                                Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });


                        }else{
                            progressBar.setVisibility(View.INVISIBLE);
                            String error= task.getException().getMessage();
                            Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}