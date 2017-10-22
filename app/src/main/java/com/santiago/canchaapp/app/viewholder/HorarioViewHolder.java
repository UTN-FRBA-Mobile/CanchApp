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
import com.santiago.canchaapp.dominio.Usuario;
import com.santiago.canchaapp.servicios.Sesion;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.santiago.canchaapp.app.otros.DateUtils.textoHorario;
import static com.santiago.canchaapp.dominio.EstadoReserva.APROBADA;
import static com.santiago.canchaapp.dominio.EstadoReserva.CANCELADA;
import static com.santiago.canchaapp.dominio.EstadoReserva.PENDIENTE;
import static java.util.UUID.fromString;

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

    private boolean esMiCancha;

    private final Date fecha;

    public HorarioViewHolder(View v, Cancha cancha, boolean esMiCancha, Date fecha) {
        super(v);
        this.view = v;
        this.cancha = cancha;
        this.esMiCancha = esMiCancha;
        this.fecha = fecha;
        ButterKnife.bind(this, v);
    }

    public void cargarDatosEnVista(SlotHorarioAlquiler slotHorarioAlquiler) {
        horario.setText(textoHorario(slotHorarioAlquiler.getHorario()));
        if (slotHorarioAlquiler.estaLibre()) {
            cargarHorarioLibre(slotHorarioAlquiler.getHorario());
        } else {
            cargarHorarioReservado(slotHorarioAlquiler.getAlquiler());
        }
    }

    private void cargarHorarioLibre(final Horario horario) {
        layoutHorarioLibre.setVisibility(VISIBLE);
        layoutHorarioReservado.setVisibility(GONE);
        botonReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Alquiler alquiler;
                if (esMiCancha) {
                    // Pedir nombre de persona para la cual se reserva
                    alquiler = new Alquiler(UUID.randomUUID(), fecha, horario, null, "<alguien>",
                            cancha.getNombre(), cancha.getTipoCancha(), APROBADA);
                } else {
                    // Tomar nombre de usuario de la persona
                    Usuario usuario = Sesion.getInstancia().getUsuario();
                    alquiler = new Alquiler(UUID.randomUUID(), fecha, horario, usuario.getUid(), usuario.getNombre(),
                            cancha.getNombre(), cancha.getTipoCancha(), PENDIENTE);
                }
                DataBase.getInstancia().insertAlquiler(
                        cancha.getDatosClub().getIdClub(), cancha.getUuid(), fecha, alquiler);
            }
        });
    }

    private void cargarHorarioReservado(Alquiler alquiler) {
        layoutHorarioReservado.setVisibility(VISIBLE);
        layoutHorarioLibre.setVisibility(GONE);
        if (alquiler.alquiladaPorUsuario()) {
            usuarioReserva.setText("por " + alquiler.getNombreUsuario());
        } else {
            usuarioReserva.setText("para " + alquiler.getNombreUsuario());
        }
        if (alquiler.getEstado() == PENDIENTE) {
            estadoReserva.setText(view.getResources().getString(R.string.txtHorarioPendienteAprobacion));
            // Sólo el dueño puede aprobar una reserva pendiente
            // El usuario la puede cancelar si es propia
            if (esMiCancha) {
                mostrarBotones(0.5f, botonAprobar, botonCancelar);
                setearListenerCancelacion(alquiler);
                setearListenerAprobacion(alquiler);
            } else if (esMiReserva(alquiler)) {
                mostrarBotones(0.5f, botonCancelar);
                setearListenerCancelacion(alquiler);
            }
        } else {
            estadoReserva.setText(view.getResources().getString(R.string.txtHorarioReservado));
            // Sólo se puede cancelar una reserva aprobada si la estoy viendo como usuario
            // y es mi propia reserva
            if (!esMiCancha && esMiReserva(alquiler)) {
                mostrarBotones(0.5f, botonCancelar);
                setearListenerCancelacion(alquiler);
            }
        }
    }

    private void setearListenerCancelacion(final Alquiler alquiler) {
        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBase.getInstancia().updateEstadoAlquiler(
                        cancha.getDatosClub().getIdClub(), cancha.getUuid(), fecha, alquiler.getUuid(), CANCELADA);
            }
        });
    }

    private void setearListenerAprobacion(final Alquiler alquiler) {
        botonAprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBase.getInstancia().updateEstadoAlquiler(
                        cancha.getDatosClub().getIdClub(), cancha.getUuid(), fecha, alquiler.getUuid(), APROBADA);
                ocultarBotones(botonAprobar, botonCancelar);
            }
        });
    }

    private void mostrarBotones(float tamanioLayout, Button... botones) {
        for(Button boton : botones) {
            boton.setVisibility(VISIBLE);
        }
        layoutTextoReserva.setLayoutParams(
                new LayoutParams(0, WRAP_CONTENT, tamanioLayout));
    }

    private void ocultarBotones(Button... botones) {
        for(Button boton : botones) {
            boton.setVisibility(GONE);
        }
    }

    private boolean esMiReserva(Alquiler alquiler) {
        return Objects.equals(Sesion.getInstancia().getUsuario().getUid(), alquiler.getIdUsuario());
    }

}
