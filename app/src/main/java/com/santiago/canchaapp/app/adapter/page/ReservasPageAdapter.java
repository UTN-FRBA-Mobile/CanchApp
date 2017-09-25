package com.santiago.canchaapp.app.adapter.page;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.santiago.canchaapp.app.fragment.ListaReservasFragment;
import com.santiago.canchaapp.app.otros.TipoReservas;

public class ReservasPageAdapter extends FragmentPagerAdapter {

    private Boolean sonAlquileres;

    public ReservasPageAdapter(FragmentManager fm, Boolean sonAlquileres) {
        super(fm);
        this.sonAlquileres = sonAlquileres;
    }

    @Override
    public Fragment getItem(int position) {
        return ListaReservasFragment.nuevaInstancia(TipoReservas.enPosicion(position), sonAlquileres);
    }

    @Override
    public int getCount() {
        return TipoReservas.values().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TipoReservas.enPosicion(position).titulo;
    }

}
