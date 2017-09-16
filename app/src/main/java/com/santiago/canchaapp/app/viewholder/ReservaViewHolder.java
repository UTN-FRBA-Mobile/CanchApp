package com.santiago.canchaapp.app.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.dominio.Reserva;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReservaViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.reserva_club)
    public TextView textoClub;

    @BindView(R.id.reserva_direccion)
    public TextView textoDireccion;

    @BindView(R.id.reserva_hora)
    public TextView textoHora;

    public ReservaViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
    }

    public void cargarDatosEnVista(Reserva reserve) {
        textoClub.setText((reserve.getNombreClub() + " - " + reserve.getTipoPartido().nombre));
        textoDireccion.setText(reserve.getDireccion());
        textoHora.setText(reserve.getFecha() + " - " + reserve.getHora() + "hs");
    }

}
