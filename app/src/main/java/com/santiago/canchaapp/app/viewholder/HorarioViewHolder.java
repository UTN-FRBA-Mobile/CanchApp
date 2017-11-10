package com.santiago.canchaapp.app.viewholder;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.otros.TextUtils;
import com.santiago.canchaapp.dominio.Alquiler;
import com.santiago.canchaapp.dominio.Cancha;
import com.santiago.canchaapp.dominio.Club;
import com.santiago.canchaapp.dominio.DataBase;
import com.santiago.canchaapp.dominio.EstadoReserva;
import com.santiago.canchaapp.dominio.Horario;
import com.santiago.canchaapp.dominio.Reserva;
import com.santiago.canchaapp.dominio.SlotHorarioAlquiler;
import com.santiago.canchaapp.dominio.Usuario;
import com.santiago.canchaapp.servicios.Preferencias;
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

    // Bloqueo (hora pasada)

    @BindView(R.id.bloqueo)
    public LinearLayout layoutBloqueo;

    private View view;

    private Cancha cancha;

    private Club club;

    private boolean esMiCancha;

    private final Date fecha;

    private final Activity activity;

    public HorarioViewHolder(Activity activity, View v, Cancha cancha, Club club, boolean esMiCancha, Date fecha) {
        super(v);
        this.activity = activity;
        this.view = v;
        this.cancha = cancha;
        this.club = club;
        this.esMiCancha = esMiCancha;
        this.fecha = fecha;
        ButterKnife.bind(this, v);
    }

    public void cargarDatosEnVista(SlotHorarioAlquiler slotHorarioAlquiler, boolean primerDia, int horaActual) {
        horario.setText(textoHorario(slotHorarioAlquiler.getHorario()));
        if (slotHorarioAlquiler.estaLibre()) {
            cargarHorarioLibre(slotHorarioAlquiler.getHorario());
        } else {
            cargarHorarioReservado(slotHorarioAlquiler.getAlquiler());
        }
        if (primerDia && horaActual >= slotHorarioAlquiler.getHorario().getDesde()) {
            layoutBloqueo.setVisibility(VISIBLE);
            deshabilitarBotones(botonAprobar, botonCancelar, botonReservar);
        }
    }

    private void cargarHorarioLibre(final Horario horario) {
        layoutHorarioLibre.setVisibility(VISIBLE);
        layoutHorarioReservado.setVisibility(GONE);
        botonReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (esMiCancha) {
                    abrirAlertaReservaDuenio(view.getContext(), horario);
                } else {
                    // Tomar nombre de usuario de la persona
                    Usuario usuario = Sesion.getInstancia().getUsuario();
                    UUID idAlquiler = UUID.randomUUID();
                    UUID idReserva = UUID.randomUUID();
                    Alquiler alquiler = new Alquiler(idAlquiler, fecha, horario, usuario.getUid(), usuario.getNombre(),
                            cancha, PENDIENTE, idReserva);
                    // Además se debe insertar una reserva para luego consultar en mis reservas
                    Reserva reserva = new Reserva(idReserva, usuario, cancha, club, fecha, horario, PENDIENTE, idAlquiler);
                    insertarEnFirebase(usuario, alquiler, reserva);
                }

            }
        });
    }

    // Pedir nombre de persona para la cual se reserva
    private void abrirAlertaReservaDuenio(Context context, final Horario horario) {
        final EditText textInput = new EditText(context);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Nueva reserva")
                .setMessage("Ingresa nombre de quien reserva")
                .setView(textInput)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Nada
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Nada
                    }
                })
                .create();
        dialog.show();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (TextUtils.estaVacio(textInput.getText().toString())) {
                    Toast.makeText(view.getContext(), R.string.txtCompletarNombre, Toast.LENGTH_LONG).show();
                }
                else{
                    Alquiler alquiler = new Alquiler(UUID.randomUUID(), fecha, horario, null, textInput.getText().toString(),
                            cancha, APROBADA, null);
                    insertarEnFirebase(null, alquiler, null);
                    dialog.dismiss();
                }
            }
        });
    }

    private void confirmarAccion(Context context, final Alquiler alquiler, final EstadoReserva nuevoEstado) {
        final CheckBox checkBox = new CheckBox(context);
        checkBox.setText("No volver a mostrar");
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Confirmar acción")
                .setMessage("¿Estás seguro que deseas " +
                        (nuevoEstado == APROBADA ? "aprobar" : "cancelar") + " esta reserva?" )
                .setView(checkBox)
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        actualizarReserva(alquiler, nuevoEstado);
                        if (checkBox.isChecked()) {
                            Preferencias.getInstancia().deshabilitarConfirmacionReservas(activity);
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Nada
                    }
                })
                .create();
        dialog.show();
    }

    private void cargarHorarioReservado(Alquiler alquiler) {
        layoutHorarioReservado.setVisibility(VISIBLE);
        layoutHorarioLibre.setVisibility(GONE);

        // Setear textos
        if (esMiCancha) {
            if (alquiler.alquiladaPorUsuario()) {
                usuarioReserva.setText("por " + alquiler.getNombreUsuario());
            } else {
                usuarioReserva.setText("para " + alquiler.getNombreUsuario());
            }
        } else if (esMiReserva(alquiler)) {
            usuarioReserva.setText("por mi");
        } else {
            usuarioReserva.setVisibility(GONE);
        }

        // Setear botones
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
                if (esMiCancha && Preferencias.getInstancia().confirmacionHabilitada(activity)) {
                    confirmarAccion(view.getContext(), alquiler, CANCELADA);
                } else {
                    actualizarReserva(alquiler, CANCELADA);
                }
            }
        });
    }

    private void setearListenerAprobacion(final Alquiler alquiler) {
        botonAprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (esMiCancha && Preferencias.getInstancia().confirmacionHabilitada(activity)) {
                    confirmarAccion(view.getContext(), alquiler, APROBADA);
                } else {
                    actualizarReserva(alquiler, APROBADA);
                }
            }
        });
    }

    private void actualizarReserva(final Alquiler alquiler, EstadoReserva nuevoEstado) {
        actualizarEnFirebase(alquiler, nuevoEstado);
        ocultarBotones(botonAprobar, botonCancelar);
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

    private void deshabilitarBotones(Button... botones) {
        for(Button boton : botones) {
            boton.setOnClickListener(null);
        }
    }

    private void insertarEnFirebase(Usuario usuario, Alquiler alquiler, Reserva reserva) {
        DataBase.getInstancia().insertAlquiler(cancha.getIdClub(), cancha.getUuid(), fecha, alquiler);
        DataBase.getInstancia().insertAlquilerPorClub(cancha.getIdClub(), alquiler);
        if (reserva != null) {
            DataBase.getInstancia().insertReserva(usuario.getUid(), reserva);
        }
    }

    private void actualizarEnFirebase(Alquiler alquiler, EstadoReserva nuevoEstado) {
        DataBase.getInstancia().updateEstadoAlquiler(cancha.getIdClub(), cancha.getUuid(), fecha, alquiler.getUuid(), nuevoEstado);
        DataBase.getInstancia().updateEstadoAlquilerPorClub(cancha.getIdClub(), alquiler.getUuid(), nuevoEstado);
        if (alquiler.alquiladaPorUsuario()) {
            DataBase.getInstancia().updateEstadoReserva(alquiler.getIdUsuario(), alquiler.getIdReserva(), nuevoEstado);
        }
    }

    private boolean esMiReserva(Alquiler alquiler) {
        return Objects.equals(Sesion.getInstancia().getUsuario().getUid(), alquiler.getIdUsuario());
    }

}
