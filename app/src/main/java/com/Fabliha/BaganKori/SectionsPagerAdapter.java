package com.Fabliha.BaganKori;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.Fabliha.BaganKori.cart.CartFragment;
import com.Fabliha.BaganKori.home.HomeFragment;
import com.Fabliha.BaganKori.profile.ProfileFragment;
import com.Fabliha.BaganKori.shop.view.ShopFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.menu_home, R.string.menu_shop, R.string.menu_slideshow, R.string.menu_profile};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        Fragment fragment= null;
        switch(position)
        {
            case 0:
                fragment= HomeFragment.newInstance(0);
                break;
            case 1:
                fragment= ShopFragment.newInstance(1);
                break;
            case 2:
                fragment= CartFragment.newInstance(2);
                break;
            case 3:
                fragment= ProfileFragment.newInstance(3);
                break;

        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
        /*Drawable image = ContextCompat.getDrawable(MainActivity.this, images[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" " + titles[position]);
        ImageSpan imageSpan = new ImageSpan(image);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb; */
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 4;
    }
}