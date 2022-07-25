package com.ufam.smartaquarium;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ufam.smartaquarium.fragments.AtuadoresFragment;
import com.ufam.smartaquarium.fragments.SensoresFragment;

public class MyViewPageAdapter extends FragmentStateAdapter {
    public MyViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new SensoresFragment();
            case 1:
                return new AtuadoresFragment();
            default:
                return new SensoresFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
