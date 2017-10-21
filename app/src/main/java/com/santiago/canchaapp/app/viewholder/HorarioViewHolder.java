package com.santiago.canchaapp.app.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.dominio.Alquiler;
import com.santiago.canchaapp.dominio.Cancha;
import com.santiago.canchaapp.dominio.DataBase;
import com.santiago.canchaapp.dominio.Horario;
import com.santiago.canchaapp.dominio.Reserva;
import com.santiago.canchaapp.dominio.SlotHorarioAlquiler;

import java.util.Date;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.VISIBLE;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.santiago.canchaapp.app.otros.DateUtils.textoHorario;
import static com.santiago.canchaapp.dominio.EstadoReserva.APROBADA;
import static com.santiago.canchaapp.dominio.EstadoReserva.PENDIENTE;

public class HorarioViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.horario)
    public TextView horario;

    // Horario reservado

    @BindView(R.id.layout_horario_reservado)
    public LinearLayout layoutHorarioReservado;

    @BindView(R.id.estado_reserva)
    public TextView estadoReserva;

    @BindView(R.id.usuario_reserva)
    public TextView usuarioReserva;

    @BindView(R.id.boton_aprobar_reserva)
    public Button botonAprobar;

    @BindView(R.id.boton_cancelar_reserva)
    public Button botonCancelar;

    @BindView(R.id.layout_texto_reserva)
    public LinearLayout layoutTextoReserva;

    // Horario Libre

    @BindView(R.id.layout_horario_libre)
    public LinearLayout layoutHorarioLibre;

    @BindView(R.id.boton_reservar)
    public Button botonReservar;

    private View view;

    private Cancha cancha;

    public HorarioViewHolder(View v, Cancha cancha) {
        super(v);
        this.view = v;
        this.cancha = cancha;
        ButterKnife.bind(this, v);
    }

    public void cargarDatosEnVista(SlotHorarioAlquiler slotHorarioAlquiler, Date fecha) {
        horario.setText(textoHorario(slotHorarioAlquiler.getHorario()));
        if (slotHorarioAlquiler.estaLibre()) {
            cargarHorarioLibre(slotHorarioAlquiler.getHorario(), fecha);
        } else {
            cargarHorarioReservado(slotHorarioAlquiler.getAlquiler());
        }
    }

    private void cargarHorarioLibre(final Horario horario, final Date fecha) {
        layoutHorarioLibre.setVisibility(VISIBLE);
        botonReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Alquiler alquiler = new Alquiler(UUID.randomUUID(), fecha, horario, null, "Carlitos",
                        cancha.getNombre(), cancha.getTipoCancha(), APROBADA);
                DataBase.getInstancia().insertAlquiler(
                        cancha.getDatosClub().getIdClub(), cancha.getUuid(), fecha, alquiler);
            }
        });
    }

    private void cargarHorarioReservado(Alquiler alquiler) {
        layoutHorarioReservado.setVisibility(VISIBLE);
        usuarioReserva.setText("por " + alquiler.getNombreUsuario());
        if (alquiler.getEstado() == PENDIENTE) {
            estadoReserva.setText(view.getResources().getString(R.string.txtHorarioPendienteAprobacion));
            mostrarBotones(0.5f, botonAprobar, botonCancelar);
        } else {
            estadoReserva.setText(view.getResources().getString(R.string.txtHorarioReservado));
            mostrarBotones(0.5f, botonCancelar);
        }
    }

    private void mostrarBotones(float tamanioLayout, Button... botones) {
        for(Button boton : botones) {
            boton.setVisibility(VISIBLE);
        }
        layoutTextoReserva.setLayoutParams(
                new LayoutParams(0, WRAP_CONTENT, tamanioLayout));
    }

}
