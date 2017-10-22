package com.santiago.canchaapp.dominio;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by sleira on 22/10/17.
 */

public class Coordenada implements Serializable{
    private double lat;
    private double lon;

    public Coordenada() {}

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public Coordenada(LatLng latLng) {
        this.lat = latLng.latitude;
        this.lon = latLng.longitude;
    }

    public LatLng toLatLng(){
        return new LatLng(this.lat, this.lon);
    }
}
