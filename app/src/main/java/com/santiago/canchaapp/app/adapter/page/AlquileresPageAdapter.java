package com.santiago.canchaapp.app.adapter.page;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.santiago.canchaapp.app.fragment.ListaAlquileresFragment;
import com.santiago.canchaapp.app.fragment.ListaReservasFragment;
import com.santiago.canchaapp.app.otros.TipoReservas;

public class AlquileresPageAdapter extends FragmentPagerAdapter {

    public AlquileresPageAdapter(FragmentManager fm) { super(fm); }

    @Override
    public Fragment getItem(int position) { return ListaAlquileresFragment.nuevaInstancia(TipoReservas.enPosicion(position)); }

    @Override
    public int getCount() {
        return TipoReservas.values().length;
    }

    @Override
    public CharSequence getPageTitle(int position) { return TipoReservas.enPosicion(position).tituloAlquileres; }

}
