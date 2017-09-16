package com.santiago.canchaapp.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.santiago.canchaapp.app.fragment.ListaReservasFragment;
import com.santiago.canchaapp.app.otros.TipoReservas;

public class ReservasPageAdapter extends FragmentPagerAdapter {

    public ReservasPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ListaReservasFragment.newInstance(TipoReservas.enPosicion(position));
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
