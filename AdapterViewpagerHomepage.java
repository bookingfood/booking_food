package com.example.booky.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.booky.View.Fragments.AngiFragment;
import com.example.booky.View.Fragments.OdauFragment;

public class AdapterViewpagerHomepage extends FragmentStatePagerAdapter {
    AngiFragment AngiFragment;
    OdauFragment OdauFragment;
    public AdapterViewpagerHomepage(FragmentManager fm){
        super(fm);
        AngiFragment = new AngiFragment();
        OdauFragment = new OdauFragment();
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return OdauFragment;
            case 1: return AngiFragment;
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
