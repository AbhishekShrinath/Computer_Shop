package com.abhishekshrinath.computershop;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class Sign_Signup_Adapter extends FragmentPagerAdapter
{

    private Context context;
    int totaltab=2;
    private String[] tabTitles = new String[]{"Login", "SignUp"};

    public Sign_Signup_Adapter(@NonNull FragmentManager fm,Context context, int totaltab)
    {
        super(fm);
        this.context=context;
        this.totaltab=totaltab;
    }
    @Override
    public CharSequence getPageTitle(int position)
    {
        return tabTitles[position];
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                signin tab0=new signin();
                return tab0;

            case 1:
                signup tab1=new signup();
                return tab1;
        }
        return null;

    }

    @Override
    public int getCount()
    {
        return tabTitles.length;
    }
}
