package com.Fabliha.garden2.ui.profile.registration.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

public class SignUpFragmentViewModel extends ViewModel {
    FirebaseAuth firebaseAuth;
    MutableLiveData<String> signUpResultData;

    /*public MutableLiveData UserSignUp(String email, String password)
    {
        if(signUpResultData==null)
        {
            signUpResultData= new MutableLiveData<>();
        }
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {

                        }else{
                            String error= task.getException().getMessage();
                            signUpResultData.setValue(error);


                        }
                    }
                });
        return signUpResultData;

    }*/
}
