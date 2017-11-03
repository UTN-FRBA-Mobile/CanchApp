package com.santiago.canchaapp.app.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.otros.AccionesSobreReserva;
import com.santiago.canchaapp.app.otros.DateUtils;
import com.santiago.canchaapp.dominio.Alquiler;
import com.santiago.canchaapp.dominio.DataBase;
import com.santiago.canchaapp.dominio.Horario;
import com.santiago.canchaapp.dominio.Reserva;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.VISIBLE;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.santiago.canchaapp.app.otros.DateUtils.stringToDateToSave;
import static com.santiago.canchaapp.app.otros.TextUtils.estaVacio;
import static com.santiago.canchaapp.dominio.EstadoReserva.APROBADA;
import static com.santiago.canchaapp.dominio.EstadoReserva.CANCELADA;
import static com.santiago.canchaapp.dominio.Horario.*;

public class AlquilerViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.alquiler_club)
    public TextView textoClub;

    @BindView(R.id.alquiler_nombreUsuario)
    public TextView textoNombreUsuario;

    @BindView(R.id.alquiler_hora)
    public TextView textoHora;

    @BindView(R.id.alquiler_motivo_cancelacion)
    public TextView textMotivoCancelacion;

    @BindView(R.id.boton_aprobar_alquiler)
    public Button botonAprobar;

    @BindView(R.id.boton_cancelar_alquiler)
    public Button botonCancelar;

    @BindView(R.id.texto_alquiler)
    public LinearLayout textoAlquiler;

    public AlquilerViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
    }

    public void cargarDatosEnVista(Alquiler alquiler, AccionesSobreReserva acciones) {
        // Setear textos
        textoClub.setText(alquiler.getNombreCancha() + " - " + alquiler.getTipoCancha());
        textoNombreUsuario.setText(alquiler.getNombreUsuario());
        textoHora.setText(alquiler.getFecha() + ", " + horaDesde(alquiler.getHora()));
        // Setear botones
        switch (acciones) {
            case SOLO_CANCELAR:
                mostrarBotones(1.25f, botonCancelar);
                setearListenerCancelacion(alquiler);
                break;
            case TODAS: mostrarBotones(0.5f, botonAprobar, botonCancelar);
                setearListenerCancelacion(alquiler);
                setearListenerAprobacion(alquiler);
                break;
        }
    }

    private void mostrarBotones(float tamanioLayout, Button... botones) {
        for(Button boton : botones) {
            boton.setVisibility(VISIBLE);
        }
        textoAlquiler.setLayoutParams(
                new LayoutParams(0, WRAP_CONTENT, tamanioLayout));
    }

    private void setearListenerCancelacion(final Alquiler alquiler) {
        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBase.getInstancia().updateEstadoReserva(alquiler.getIdUsuario(), alquiler.getIdReserva(), CANCELADA);
                DataBase.getInstancia().updateEstadoAlquiler(alquiler.getIdClub(), alquiler.getIdCancha(), stringToDateToSave(alquiler.getFecha()), alquiler.getUuid(), CANCELADA);
                DataBase.getInstancia().updateEstadoAlquilerPorClub(alquiler.getIdClub(), alquiler.getUuid(), CANCELADA);

            }
        });
    }

    private void setearListenerAprobacion(final Alquiler alquiler) {
        botonAprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBase.getInstancia().updateEstadoReserva(alquiler.getIdUsuario(), alquiler.getIdReserva(), APROBADA);
                DataBase.getInstancia().updateEstadoAlquiler(alquiler.getIdClub(), alquiler.getIdCancha(), stringToDateToSave(alquiler.getFecha()), alquiler.getUuid(), APROBADA);
                DataBase.getInstancia().updateEstadoAlquilerPorClub(alquiler.getIdClub(), alquiler.getUuid(), APROBADA);

            }
        });
    }

}
