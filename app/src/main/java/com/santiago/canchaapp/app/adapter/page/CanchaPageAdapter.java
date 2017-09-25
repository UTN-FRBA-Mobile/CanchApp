package com.santiago.canchaapp.app.adapter.page;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.santiago.canchaapp.app.fragment.DatosCanchaFragment;
import com.santiago.canchaapp.app.fragment.ReservasCanchaFragment;

public class CanchaPageAdapter extends FragmentPagerAdapter {

    public CanchaPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1: return DatosCanchaFragment.nuevaInstancia();
            case 2: return ReservasCanchaFragment.nuevaInstancia();
        }
        throw new RuntimeException("Tab de Cancha inesperada");
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 1: return "Datos";
            case 2: return "Reservas";
        }
        throw new RuntimeException("Tab de Cancha inesperada");
    }

}
