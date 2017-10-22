package com.santiago.canchaapp.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.santiago.canchaapp.R;
import com.santiago.canchaapp.dominio.Cancha;
import com.santiago.canchaapp.dominio.Club;
import com.santiago.canchaapp.dominio.DataBase;
import com.santiago.canchaapp.dominio.Horario;
import com.santiago.canchaapp.servicios.Servidor;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

public class DatosClubFragment extends Fragment {

    @BindView(R.id.nombre)
    public TextView textoNombre;

    @BindView(R.id.direccion)
    public TextView textoDireccion;

    @BindView(R.id.telefono)
    public TextView textoTelefono;

    @BindView(R.id.email)
    public TextView textoEmail;

    @BindView(R.id.horario)
    public TextView textoHorario;

    @BindView(R.id.mapa_club)
    public ImageView imagenMapa;

    private static String ARG_ID_CLUB = "idClub";

    public static DatosClubFragment nuevaInstancia(String idClub) {
        DatosClubFragment fragment = new DatosClubFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID_CLUB, idClub);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_datos_club, container, false);
        ButterKnife.bind(this, rootView);
        Bundle arguments = getArguments();
        getClub(arguments.getString(ARG_ID_CLUB));
        //cargarVista(inflater, rootView, club());
        /**/

        return rootView;
    }

    private void getClub(String idClub){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    Club club = dataSnapshot.getValue(Club.class);
                    cargarVista(club);
                }
                /*String email = dataSnapshot.child("email").getValue().toString();
                String nombre = dataSnapshot.child("nombre").getValue().toString();
                String lat = dataSnapshot.child("coordenadas").child("latitude").getValue().toString();
                String lon = dataSnapshot.child("coordenadas").child("longitude").getValue().toString();
                LatLng coordenadas = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
                String direccion = dataSnapshot.child("direccion").getValue().toString();
                String telefono = dataSnapshot.child("telefono").getValue().toString();
                String desde = dataSnapshot.child("rangoHorario").child("desde").getValue().toString();
                String hasta = dataSnapshot.child("rangoHorario").child("hasta").getValue().toString();
                Horario rangoHorario = new Horario(Integer.parseInt(desde), Integer.parseInt(hasta));
                String uuid = dataSnapshot.child("uuid").getValue().toString();
                Club club = new Club(UUID.fromString(uuid), nombre, direccion, coordenadas, email, telefono, rangoHorario, null);
                cargarVista(club);*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        DatabaseReference referenceClub = DataBase.getInstancia().getReferenceClub(idClub);
        referenceClub.addListenerForSingleValueEvent(valueEventListener);
    }

    private void cargarVista(Club club) {
        // Setea textos
        textoNombre.setText(club.getNombre());
        textoDireccion.setText(club.getDireccion());
        textoTelefono.setText("Teléfono: " + club.getTelefono());
        textoEmail.setText("Email: " + club.getEmail());
        textoHorario.setText(
                "Abierto de " + club.getRangoHorario().getDesde() +
                        " a " + club.getRangoHorario().getHasta() + "hs.");
    }

}
