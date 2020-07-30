package com.Fabliha.BaganKori.other.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.Fabliha.BaganKori.R;

import com.Fabliha.BaganKori.admin.AdminUploadActivity;
import com.Fabliha.BaganKori.cart.CartFragment;
import com.Fabliha.BaganKori.cart.model.CartItem;
import com.Fabliha.BaganKori.profile.ProfileFragment;
import com.Fabliha.BaganKori.shop.model.Upload;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

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

public class SeedsActivity extends AppCompatActivity {

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


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeds);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent(SeedsActivity.this, AdminUploadActivity.class);
                startActivity(intent);
            }
        });
        mDatabaseRefCart = FirebaseDatabase.getInstance().getReference("cartitem");
        Log.v("Cart", mDatabaseRefCart.getRepo().toString());
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();


        btnGoToCart = (FloatingActionButton) findViewById(R.id.btnGoToCart);
        btnGoToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firebaseUser==null)
                {
                    Intent intent= new Intent(SeedsActivity.this, ProfileFragment.class);
                    startActivity(intent);
                }
                else{
                    showCart ();
                }

            }
        });

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressCircle = findViewById(R.id.progress_circle);
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
                mAdapter = new SeedsImageAdapter(SeedsActivity.this, mUploads, itemsMap);
                mRecyclerView.setAdapter(mAdapter);
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SeedsActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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
                        Intent intent = new Intent(SeedsActivity.this, CartFragment.class);
                        finish();
                        Log.v("Cart", CartText);

                    } else {
                        String error = task.getException().getMessage();
                        Toast.makeText(SeedsActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }

}