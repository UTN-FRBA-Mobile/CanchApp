package com.santiago.canchaapp.app.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.adapter.ClubesAdapter;
import com.santiago.canchaapp.dominio.Club;
import com.santiago.canchaapp.dominio.DataBase;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClubViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.club_nombre)
    public TextView textoNombre;

    @BindView(R.id.club_direccion)
    public TextView textoDireccion;

    @BindView(R.id.club_horario)
    public TextView textoHorario;

    @BindView(R.id.club_cantidad_de_canchas)
    public TextView textoCantidadDeCanchas;

    @BindView(R.id.item_contenido_club)
    public LinearLayout contenido;

    private View view;

    private ClubesAdapter adapter;

    public ClubViewHolder(View v, ClubesAdapter adapter) {
        super(v);
        ButterKnife.bind(this, v);
        this.view = v;
        this.adapter = adapter;
    }

    public void cargarDatosEnVista(Club club) {
        // Setea textos
        textoNombre.setText(club.getNombre());
        textoDireccion.setText(club.getDireccion());
        textoHorario.setText(
                "Abierto de " + club.getRangoHorario().getDesde() +
                " a " + club.getRangoHorario().getHasta() + "hs.");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    Map<String, Object> canchas = (HashMap<String,Object>) dataSnapshot.getValue();

                    String textoCanchas;
                    if (canchas.size() == 1)
                        textoCanchas = " cancha";
                    else
                        textoCanchas = " canchas";

                    textoCantidadDeCanchas.setText(canchas.size() + textoCanchas);}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        DatabaseReference refCanchas = DataBase.getInstancia().getReferenceCanchasClub(club.getUuid());
        refCanchas.addListenerForSingleValueEvent(valueEventListener);

        // Setea boton
        contenido.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        adapter.onClick(v, getAdapterPosition());
    }

}
