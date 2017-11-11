package com.santiago.canchaapp.app.viewholder;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.otros.AccionesSobreReserva;
import com.santiago.canchaapp.dominio.DataBase;
import com.santiago.canchaapp.dominio.Reserva;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.VISIBLE;
import static android.view.View.combineMeasuredStates;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.santiago.canchaapp.app.otros.DateUtils.stringToDate;
import static com.santiago.canchaapp.app.otros.DateUtils.stringToDateToSave;
import static com.santiago.canchaapp.app.otros.DateUtils.textoDia;
import static com.santiago.canchaapp.dominio.EstadoReserva.CANCELADA;
import static com.santiago.canchaapp.dominio.Horario.*;

public class ReservaViewHolder extends RecyclerView.ViewHolder {

    private final Context context;

    @BindView(R.id.reserva_club)
    public TextView textoClub;
    @BindView(R.id.reserva_direccion)
    public TextView textoDireccion;
    @BindView(R.id.reserva_hora)
    public TextView textoHora;
    @BindView(R.id.reserva_motivo_cancelacion)
    public TextView textMotivoCancelacion;
    @BindView(R.id.boton_cancelar_reserva)
    public ImageView botonCancelar;
    @BindView(R.id.texto_reserva)
    public LinearLayout textoReserva;

    public ReservaViewHolder(View v, Activity activity) {
        super(v);
        ButterKnife.bind(this, v);
        this.context = activity.getApplicationContext();

    }

    public void cargarDatosEnVista(Reserva reserva, AccionesSobreReserva acciones) {
        // Setear textos
        textoClub.setText(reserva.getNombreCancha() + ", " + reserva.getNombreClub() + " (" +
                reserva.getTipoCancha().nombre + ")");
        textoHora.setText(textoDia(stringToDateToSave(reserva.getFecha())) + ", " + horaDesde(reserva.getHora()));
        textoDireccion.setText(reserva.getDireccionClub());
        // Setear botones
        switch (acciones) {
            case SOLO_CANCELAR:
                mostrarBotones(botonCancelar);
                setearListenerCancelacion(reserva);
                break;
        }
    }

    private void mostrarBotones(ImageView... botones) {
        for(ImageView boton : botones) {
            boton.setVisibility(VISIBLE);
        }
        textoReserva.setLayoutParams(new LayoutParams(0, WRAP_CONTENT, 1));
    }

    private void setearListenerCancelacion(final Reserva reserva) {
        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(DataBase.getInstancia().isOnline(context)) {
                    DataBase.getInstancia().updateEstadoReserva(reserva.getIdUsuario(), reserva.getUuid(), CANCELADA);
                    DataBase.getInstancia().updateEstadoAlquiler(reserva.getIdClub(), reserva.getIdCancha(), stringToDateToSave(reserva.getFecha()), reserva.getIdAlquiler(), CANCELADA);
                    DataBase.getInstancia().updateEstadoAlquilerPorClub(reserva.getIdClub(), reserva.getIdAlquiler(), CANCELADA);
                } else {
                    Toast.makeText(context, R.string.txtSinConexion, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
