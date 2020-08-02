package com.Fabliha.BaganKori.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.Fabliha.BaganKori.retrofit.ApiResponse;
import com.Fabliha.BaganKori.retrofit.IJsonPlaceHolderAPI;
import com.Fabliha.BaganKori.R;
import com.Fabliha.BaganKori.retrofit.ResponseItem;
import com.Fabliha.BaganKori.retrofit.RetrofitClientInstance;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    RecyclerView recyclerView;
    HomeImageAdapter mAdapter;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDatabaseRefCart;
    private FirebaseAuth firebaseAuth;
    private List<ResponseItem> mUploads;
    private ProgressBar mProgressCircle;


    public static HomeFragment newInstance(int index) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
       // final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        recyclerView= root.findViewById(R.id.homerv);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setNestedScrollingEnabled(false);
     //   mProgressCircle = root.findViewById(R.id.progress_circle);
        mUploads = new ArrayList<>();
        /*
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("seeds/");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    mUploads.add(upload);
                }
                mAdapter = new HomeImageAdapter(getActivity(), mUploads);
                recyclerView.setAdapter(mAdapter);
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        }); */

        IJsonPlaceHolderAPI jsonPlaceHolderAPI = RetrofitClientInstance.getRetrofitInstance().create(IJsonPlaceHolderAPI.class);
        Call<ApiResponse> call= jsonPlaceHolderAPI.getProducts("bW23cGaq");
        call.enqueue(new Callback <ApiResponse>() {

            @Override
            public void onResponse(Call<ApiResponse> call, Response <ApiResponse> response) {
                Log.d("Home",response.body().toString());

                if(!response.isSuccessful())
                {

                    Log.d("Home","Error: "+ response.message());
                    return;
                }

                mUploads= response.body().getResponse();
                Log.d("Home","respnse body: "+ mUploads.toString());
                mAdapter = new HomeImageAdapter(getActivity(), mUploads);
                recyclerView.setAdapter(mAdapter);
             //   mProgressCircle.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

                Log.d("Home","Error: Failure "+ t.getMessage());
                t.printStackTrace();

            }
        });


        return root;
    }
}