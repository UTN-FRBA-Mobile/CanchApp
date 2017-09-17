package com.santiago.canchaapp.app.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.dominio.Reserva;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class ReservaViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.reserva_club)
    public TextView textoClub;

    @BindView(R.id.reserva_direccion)
    public TextView textoDireccion;

    @BindView(R.id.reserva_hora)
    public TextView textoHora;

    @BindView(R.id.botones_reserva)
    public LinearLayout botonesAprobacion;

    public ReservaViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
    }

    public void cargarDatosEnVista(Reserva reserva, Boolean mostrarBotonesDeAprobacion) {
        textoClub.setText((reserva.getNombreClub() + " - " + reserva.getTipoPartido().nombre));
        textoDireccion.setText(reserva.getDireccion());
        textoHora.setText(reserva.getFecha() + " - " + reserva.getHora() + "hs");
        botonesAprobacion.setVisibility(mostrarBotonesDeAprobacion ? VISIBLE : INVISIBLE);
    }

}
