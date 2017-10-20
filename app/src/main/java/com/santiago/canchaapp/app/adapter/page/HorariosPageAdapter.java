package com.santiago.canchaapp.app.adapter.page;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.santiago.canchaapp.app.fragment.ListaHorariosFragment;
import com.santiago.canchaapp.dominio.Cancha;
import com.santiago.canchaapp.dominio.Horario;

import static com.santiago.canchaapp.app.otros.DateUtils.hoyMasDias;

public class HorariosPageAdapter extends FragmentPagerAdapter {

    private static int CANTIDAD_DIAS = 8; // 1 semana (incluyendo el primer y último día parcialmente

    private Horario horarioCancha;

    private Cancha cancha;

    public HorariosPageAdapter(FragmentManager fm, Cancha cancha) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ListaHorariosFragment.nuevaInstancia(cancha, hoyMasDias(position), position);
    }

    @Override
    public int getCount() {
        return CANTIDAD_DIAS;
    }

}
