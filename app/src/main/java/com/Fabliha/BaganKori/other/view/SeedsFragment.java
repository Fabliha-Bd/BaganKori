package com.Fabliha.BaganKori.other.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.Fabliha.BaganKori.R;
import com.Fabliha.BaganKori.admin.AdminUploadActivity;
import com.Fabliha.BaganKori.cart.CartFragment;
import com.Fabliha.BaganKori.cart.model.CartItem;
import com.Fabliha.BaganKori.profile.ProfileFragment;
import com.Fabliha.BaganKori.shop.model.Upload;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SeedsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private SeedsImageAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDatabaseRefCart;
    private FirebaseAuth firebaseAuth;
    private List<Upload> mUploads;
    private HashMap<String, CartItem> itemsMap;
    private FloatingActionButton btnGoToCart;
    private String CartText = "";
    private RelativeLayout relativeLayout;

    public SeedsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_seeds, container, false);
        relativeLayout= view.findViewById(R.id.relativeLayout);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent(getActivity(), AdminUploadActivity.class);
                startActivity(intent);
            }
        });
        mDatabaseRefCart = FirebaseDatabase.getInstance().getReference("cartitem");
        Log.v("Cart", mDatabaseRefCart.getRepo().toString());
        firebaseAuth = FirebaseAuth.getInstance();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        btnGoToCart = view.findViewById(R.id.btnGoToCart);
        btnGoToCart.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(firebaseUser==null)
                {
                    Intent intent= new Intent(getActivity(), ProfileFragment.class);
                    startActivity(intent);
                }
                else{
                    showCart ();
                }

            }
        });

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProgressCircle = view.findViewById(R.id.progress_circle);
        mUploads = new ArrayList<>();
        // items= new ArrayList<>();
        itemsMap = new HashMap<String, CartItem>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("seeds/");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    mUploads.add(upload);
                }
                mAdapter = new SeedsImageAdapter(getActivity(), mUploads, itemsMap);
                mRecyclerView.setAdapter(mAdapter);
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void showCart()
    {
        final String uploadIdParent = firebaseAuth.getCurrentUser().getUid();

        for (Map.Entry<String, CartItem> entry : itemsMap.entrySet()) {
            CartText += (entry.getKey() + " = " + entry.getValue() + "\n");
            CartItem cartItem = new CartItem(entry.getValue().getName(),
                    entry.getValue().getPrice(), entry.getValue().getType(), entry.getValue().getQuantity());

                   /* CartBuilder cartItem = new CartBuilder.Builder()
                            .setName(entry.getValue().getName())
                            .setPrice(entry.getValue().getPrice())
                            .setType(entry.getValue().getType())
                            .setQuantity(entry.getValue().getQuantity())
                            .build();*/
            //time
            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String presentDateTime = myDateObj.format(myFormatObj);
            Log.v("Cart", "Present Date Time " + presentDateTime);
            //key
            String uploadId = mDatabaseRefCart.push().getKey();
            mDatabaseRefCart.child(uploadIdParent).child(presentDateTime).child(uploadId).setValue(cartItem).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.v("Cart", "Cart Data Added");
                        Intent intent = new Intent(getActivity(), CartFragment.class);
                        Log.v("Cart", CartText);

                    } else {
                        String error = task.getException().getMessage();
                        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slideout_from_right);
        fragmentTransaction.replace(relativeLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

}
