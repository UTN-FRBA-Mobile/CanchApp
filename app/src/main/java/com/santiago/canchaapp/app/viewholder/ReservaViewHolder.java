package com.santiago.canchaapp.app.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.otros.AccionesSobreReserva;
import com.santiago.canchaapp.dominio.Horario;
import com.santiago.canchaapp.dominio.Reserva;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.VISIBLE;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.santiago.canchaapp.app.otros.TextUtils.estaVacio;
import static com.santiago.canchaapp.dominio.Horario.*;

public class ReservaViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.reserva_club)
    public TextView textoClub;

    @BindView(R.id.reserva_direccion)
    public TextView textoDireccion;

    @BindView(R.id.reserva_hora)
    public TextView textoHora;

    @BindView(R.id.reserva_motivo_cancelacion)
    public TextView textMotivoCancelacion;

    @BindView(R.id.boton_aprobar_reserva)
    public Button botonAprobar;

    @BindView(R.id.boton_cancelar_reserva)
    public Button botonCancelar;

    @BindView(R.id.texto_reserva)
    public LinearLayout textoReserva;

    public ReservaViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
    }

    public void cargarDatosEnVista(Reserva reserva, AccionesSobreReserva acciones) {
        textoClub.setText(reserva.getTipoCancha().nombre + " - " + reserva.getNombreClub());
        textoDireccion.setText(reserva.getDireccionClub());
        textoHora.setText(reserva.getFecha() + ", " + horaDesde(reserva.getHora()));
        switch (acciones) {
            case SOLO_CANCELAR: mostrarBotones(1.25f, botonCancelar); break;
            case TODAS: mostrarBotones(0.5f, botonAprobar, botonCancelar); break;
        }
    }

    private void mostrarBotones(float tamanioLayout, Button... botones) {
        for(Button boton : botones) {
            boton.setVisibility(VISIBLE);
        }
        textoReserva.setLayoutParams(
                new LayoutParams(0, WRAP_CONTENT, tamanioLayout));
    }

}
