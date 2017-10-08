package com.santiago.canchaapp.app.adapter.page;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.santiago.canchaapp.app.fragment.DatosCanchaFragment;
import com.santiago.canchaapp.app.fragment.HorariosCanchaFragment;

public class CanchaPageAdapter extends FragmentPagerAdapter {

    public CanchaPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return DatosCanchaFragment.nuevaInstancia();
            case 1: return HorariosCanchaFragment.nuevaInstancia();
        }
        throw new RuntimeException("Tab de Cancha inesperado");
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return "Datos";
            case 1: return "Horarios";
        }
        throw new RuntimeException("Tab de Cancha inesperado");
    }

}
