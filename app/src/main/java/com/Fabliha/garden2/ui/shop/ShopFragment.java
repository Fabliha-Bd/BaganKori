package com.Fabliha.garden2.ui.shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.Fabliha.garden2.R;
import com.Fabliha.garden2.ui.cart.CartFragment;
import com.Fabliha.garden2.ui.shop.seeds.view.SeedsActivity;
import com.Fabliha.garden2.ui.shop.seeds.view.SeedsFragment;

public class ShopFragment extends Fragment {

    ShopViewModel shopViewModel;
    private ImageButton btn_seed;
    private ScrollView scrollView;


    public static ShopFragment newInstance(int index) {
        ShopFragment fragment = new ShopFragment();
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shopViewModel =
                ViewModelProviders.of(this).get(ShopViewModel.class);

        View root = inflater.inflate(R.layout.fragment_shop, container, false);
        scrollView= root.findViewById(R.id.scrollView);
        btn_seed = (ImageButton) root.findViewById(R.id.imageButton);
        btn_seed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SeedsActivity.class);
                startActivity(intent);
                //setFragment(new SeedsFragment());
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

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slideout_from_right);
        fragmentTransaction.replace(scrollView.getId(), fragment);
        fragmentTransaction.commit();
    }
}