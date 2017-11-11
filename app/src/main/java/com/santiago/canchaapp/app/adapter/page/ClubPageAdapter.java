package com.santiago.canchaapp.app.adapter.page;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.santiago.canchaapp.app.fragment.CanchasFragment;
import com.santiago.canchaapp.app.fragment.DatosClubFragment;

public class ClubPageAdapter extends FragmentPagerAdapter {

    private String idClub;
    private Boolean esMiClub;

    public ClubPageAdapter(FragmentManager fm, String idClub, Boolean esMiClub) {
        super(fm);
        this.idClub = idClub;
        this.esMiClub = esMiClub;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return DatosClubFragment.nuevaInstancia(idClub);
            case 1: return CanchasFragment.nuevaInstancia(idClub, esMiClub);
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
