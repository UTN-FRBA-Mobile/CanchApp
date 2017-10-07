package com.santiago.canchaapp.app.adapter.page;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.santiago.canchaapp.app.fragment.ListaHorariosFragment;

import static com.santiago.canchaapp.app.otros.DateUtils.hoyMasDias;

public class HorariosPageAdapter extends FragmentPagerAdapter {

    private static int CANTIDAD_DIAS = 7;

    public HorariosPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ListaHorariosFragment.nuevaInstancia(hoyMasDias(position), position);
    }

    @Override
    public int getCount() {
        return CANTIDAD_DIAS;
    }

}
