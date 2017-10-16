package com.santiago.canchaapp.app.adapter.page;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.santiago.canchaapp.app.fragment.BuscarCanchasListaFragment;
import com.santiago.canchaapp.app.fragment.BuscarCanchasMapaFragment;


public class BuscarCanchasPageAdapter extends FragmentPagerAdapter {

    private boolean esMiClub;

    public BuscarCanchasPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return BuscarCanchasListaFragment.nuevaInstancia(); //BuscarCanchasMapaFragment.nuevaInstancia();
            case 1: return BuscarCanchasListaFragment.nuevaInstancia();
        }
        throw new RuntimeException("Tab de Buscar Canchas inesperado");
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return "Mapa";
            case 1: return "Todos los clubes";
        }
        throw new RuntimeException("Tab de Buscar Canchas inesperado");
    }

}