package com.santiago.canchaapp.app.adapter.page;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.google.android.gms.maps.model.LatLng;
import com.santiago.canchaapp.app.fragment.BuscarCanchasListaFragment;
import com.santiago.canchaapp.app.fragment.BuscarCanchasMapaFragment;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.support.v4.content.ContextCompat.checkSelfPermission;


public class BuscarCanchasPageAdapter extends FragmentPagerAdapter {

    private boolean esMiClub;

    private LatLng locacion;

    public BuscarCanchasPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return BuscarCanchasMapaFragment.nuevaInstancia();
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