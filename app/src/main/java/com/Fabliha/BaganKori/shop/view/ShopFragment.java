package com.Fabliha.BaganKori.shop.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.Fabliha.BaganKori.R;
import com.Fabliha.BaganKori.home.HomeImageAdapter;
import com.Fabliha.BaganKori.shop.model.Upload;
import com.Fabliha.BaganKori.other.view.SeedsActivity;
import com.Fabliha.BaganKori.shop.viewmodel.ShopViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShopFragment extends Fragment {

    ShopViewModel shopViewModel;
    //  ProductType productType;
    private ImageButton btn_bansai;
    private ImageButton btn_decoPlants;
    private ImageButton btn_fruitPlants;
    private ImageButton btn_flowerPlants;
    private ImageButton btn_pots;
    private ImageButton btn_seed;
    private ImageButton btn_soil;
    private ImageButton btn_tools;
    private ImageButton btn_other;
    private TextView selectedCategoryName;

    private CardView i2;
    private ScrollView scrollView;
    private RecyclerView mRecyclerView;
    private HomeImageAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDatabaseRefCart;
    private FirebaseAuth firebaseAuth;
    private List<Upload> mUploads;

    public static ShopFragment newInstance(int index) {
        ShopFragment fragment = new ShopFragment();
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shopViewModel =
                ViewModelProviders.of(this).get(ShopViewModel.class);

        View root = inflater.inflate(R.layout.fragment_shop3, container, false);
        scrollView = root.findViewById(R.id.scrollView);
        btn_other = (ImageButton) root.findViewById(R.id.btn_others);
        btn_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SeedsActivity.class);
                startActivity(intent);
                //setFragment(new SeedsFragment());
            }
        });
        btn_bansai = (ImageButton) root.findViewById(R.id.btn_bansai);
        btn_decoPlants = (ImageButton) root.findViewById(R.id.btn_decoPlants);
        btn_flowerPlants = (ImageButton) root.findViewById(R.id.btn_flowerPlants);
        btn_fruitPlants = (ImageButton) root.findViewById(R.id.btn_fruitPlants);
        btn_pots = (ImageButton) root.findViewById(R.id.btn_pots);
        btn_soil = (ImageButton) root.findViewById(R.id.btn_soil);
        btn_tools = (ImageButton) root.findViewById(R.id.btn_tools);
        btn_seed = (ImageButton) root.findViewById(R.id.btn_seeds);
        mRecyclerView = root.findViewById(R.id.rvProduct);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mProgressCircle = root.findViewById(R.id.progress_circle);
        selectedCategoryName = root.findViewById(R.id.tvSelectedCategory);
        mUploads = new ArrayList<>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("seeds/");
        // showProducts(productType);
        btn_bansai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopViewModel.setProductType("bansai");
                shopViewModel.setCategorytext("Bansai");
            }
        });
        shopViewModel.getProductType().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showProducts(s);
            }
        });
        shopViewModel.getCategorytext().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                selectedCategoryName.setText(s);
            }
        });

        btn_decoPlants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  productType="decorativeplant";
                shopViewModel.setProductType("decorativeplant");
                shopViewModel.setCategorytext("Decorative Plants");

            }
        });

        btn_flowerPlants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // productType="flowerplant";
                shopViewModel.setProductType("flowerplant");
                shopViewModel.setCategorytext("Flower Plants");
            }
        });
        btn_fruitPlants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  productType="fruitplant";
                shopViewModel.setProductType("fruitplant");
                shopViewModel.setCategorytext("Fruit Plants");
            }
        });
        btn_pots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  productType="pots";
                shopViewModel.setProductType("pot");
                shopViewModel.setCategorytext("Pots");
            }
        });
        btn_soil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   productType="soil";
                shopViewModel.setProductType("soil");
                shopViewModel.setCategorytext("Soil and Fertilizers");
            }
        });
        btn_tools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  productType="tools";
                shopViewModel.setProductType("tool");
                shopViewModel.setCategorytext("Tools");
            }
        });
        btn_seed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   productType="others";
                shopViewModel.setProductType("seeds");
                shopViewModel.setCategorytext("Seeds");
            }
        });

        final TextView textView = root.findViewById(R.id.text_shop);
        shopViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }

    public void showProducts(String ptype) {
        mUploads.clear();
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUploads.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    Log.d("Product", upload.toString());
                    String type = upload.getType().toString();
                    Log.d("ProductType", "type= " + type);
                    if (ptype.equals(type)) {
                        mUploads.add(upload);
                    }
                }
                mAdapter = new HomeImageAdapter(getActivity(), mUploads);
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

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slideout_from_right);
        fragmentTransaction.replace(scrollView.getId(), fragment);
        fragmentTransaction.commit();
    }
}