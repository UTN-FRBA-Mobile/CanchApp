package com.santiago.canchaapp.app.adapter.page;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.santiago.canchaapp.app.fragment.CanchasFragment;
import com.santiago.canchaapp.app.fragment.DatosCanchaFragment;
import com.santiago.canchaapp.app.fragment.DatosClubFragment;
import com.santiago.canchaapp.app.fragment.HorariosCanchaFragment;
import com.santiago.canchaapp.dominio.Club;

import java.io.Serializable;

public class ClubPageAdapter extends FragmentPagerAdapter {

    private boolean esMiClub;

    private Club club;

    public ClubPageAdapter(FragmentManager fm, Boolean esMiClub, Serializable unClub) {
        super(fm);
        this.esMiClub = esMiClub;
        this.club = (Club) unClub;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return DatosClubFragment.nuevaInstancia(club);
            case 1: return CanchasFragment.nuevaInstancia(club, esMiClub);
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
