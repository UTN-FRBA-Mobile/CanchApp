package com.santiago.canchaapp.app.adapter.page;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.santiago.canchaapp.app.fragment.CanchasFragment;
import com.santiago.canchaapp.app.fragment.DatosCanchaFragment;
import com.santiago.canchaapp.app.fragment.DatosClubFragment;
import com.santiago.canchaapp.app.fragment.HorariosCanchaFragment;

public class ClubPageAdapter extends FragmentPagerAdapter {

    private boolean esMiClub;

    public ClubPageAdapter(FragmentManager fm, Boolean esMiClub) {
        super(fm);
        this.esMiClub = esMiClub;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return DatosClubFragment.nuevaInstancia();
            case 1: return CanchasFragment.nuevaInstancia();
        }
        throw new RuntimeException("Tab de Club inesperado");
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return "Club";
            case 1: return "Canchas";
        }
        throw new RuntimeException("Tab de Club inesperado");
    }

}
