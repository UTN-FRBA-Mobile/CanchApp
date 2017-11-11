package com.santiago.canchaapp.app.viewholder;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import static android.graphics.Typeface.BOLD;
import static android.support.v7.app.AlertDialog.BUTTON_POSITIVE;
import static android.support.v7.app.AlertDialog.Builder;
import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.santiago.canchaapp.app.otros.DateUtils.textoHorario;
import static com.santiago.canchaapp.dominio.EstadoReserva.APROBADA;
import static com.santiago.canchaapp.dominio.EstadoReserva.CANCELADA;
import static com.santiago.canchaapp.dominio.EstadoReserva.PENDIENTE;

public class HorarioViewHolder extends RecyclerView.ViewHolder {

    private View view;
    private Cancha cancha;
    private Club club;
    private boolean esMiCancha;
    private final Date fecha;
    private final Activity activity;
    private final Context context;

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
    public ImageView botonAprobar;
    @BindView(R.id.boton_cancelar_reserva)
    public ImageView botonCancelar;
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



    public HorarioViewHolder(Activity activity, View v, Cancha cancha, Club club, boolean esMiCancha, Date fecha) {
        super(v);
        this.activity = activity;
        this.context = activity.getApplicationContext();
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
        layoutHorarioReservado.setVisibility(GONE);
        layoutHorarioLibre.setVisibility(VISIBLE);
        botonReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(DataBase.getInstancia().isOnline(context)) {
                    if (esMiCancha) {
                        abrirAlertaReservaDuenio(activity, horario);
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
                else {
                    Toast.makeText(context, R.string.txtSinConexion, Toast.LENGTH_SHORT);
                }

            }
        });
    }

    // Pedir nombre de persona para la cual se reserva
    private void abrirAlertaReservaDuenio(Activity activity, final Horario horario) {
        final View viewDialogo = activity.getLayoutInflater().inflate(R.layout.dialogo_ingresar_nombre, null);
        final AlertDialog dialog = new Builder(activity)
                .setTitle("Nueva reserva")
                .setMessage("Ingresá el nombre de quien reserva")
                .setView(viewDialogo)
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
        Button positiveButton = dialog.getButton(BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText textInput = viewDialogo.findViewById(R.id.txtNombre);
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

    private void confirmarAccion(final Activity activity, final Alquiler alquiler, final EstadoReserva nuevoEstado) {
        final View viewDialogo = activity.getLayoutInflater().inflate(R.layout.dialogo_confirmar_reserva, null);
        final AlertDialog dialog = new Builder(activity)
                .setTitle("Confirmar acción")
                .setMessage("¿Estás seguro que querés " +
                        (nuevoEstado == APROBADA ? "aprobar" : "cancelar") + " esta reserva?" )
                .setView(viewDialogo)
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        actualizarReserva(alquiler, nuevoEstado);
                        CheckBox checkBox = viewDialogo.findViewById(R.id.checkboxNoMostrar);
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
        layoutHorarioLibre.setVisibility(GONE);
        layoutHorarioReservado.setVisibility(VISIBLE);

        // Setear textos
        if (esMiCancha) {
            usuarioReserva.setText(textoUsuarioComoDuenio(alquiler));
        } else if (esMiReserva(alquiler)) {
            usuarioReserva.setText("Por mi");
        } else {
            usuarioReserva.setVisibility(GONE);
        }

        // Setear botones
        if (alquiler.getEstado() == PENDIENTE) {
            estadoReserva.setText(view.getResources().getString(R.string.txtHorarioPendienteAprobacion));
            // Sólo el dueño puede aprobar una reserva pendiente
            // El usuario la puede cancelar si es propia
            if (esMiCancha) {
                mostrarBotones(botonAprobar, botonCancelar);
                setearListenerCancelacion(alquiler);
                setearListenerAprobacion(alquiler);
            } else if (esMiReserva(alquiler)) {
                mostrarBotones(botonCancelar);
                noMostrarBotones(botonAprobar);
                setearListenerCancelacion(alquiler);
            } else {
                ocultarBotones(botonAprobar, botonCancelar);
            }
        } else {
            estadoReserva.setText(view.getResources().getString(R.string.txtHorarioReservado));
            // Sólo se puede cancelar una reserva aprobada si la estoy viendo como usuario
            // y es mi propia reserva
            if (!esMiCancha && esMiReserva(alquiler)) {
                mostrarBotones(botonCancelar);
                noMostrarBotones(botonAprobar);
                setearListenerCancelacion(alquiler);
            } else {
                ocultarBotones(botonAprobar, botonCancelar);
            }
        }
    }

    private SpannableStringBuilder textoUsuarioComoDuenio(Alquiler alquiler) {
        SpannableString nombre = new SpannableString(alquiler.getNombreUsuario());
        nombre.setSpan(new StyleSpan(BOLD), 0, nombre.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableStringBuilder builder = new SpannableStringBuilder()
                .append((alquiler.esUsuarioRegistrado() ? "Por " : "Para "))
                .append(nombre);
        return builder;
    }

    private void setearListenerCancelacion(final Alquiler alquiler) {
        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (esMiCancha && Preferencias.getInstancia().confirmacionHabilitada(activity)) {
                    confirmarAccion(activity, alquiler, CANCELADA);
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
                    confirmarAccion(activity, alquiler, APROBADA);
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

    private void mostrarBotones(View... botones) {
        for(View boton : botones) {
            boton.setVisibility(VISIBLE);
        }
    }

    private void ocultarBotones(View... botones) {
        for(View boton : botones) {
            boton.setVisibility(GONE);
        }
    }

    private void noMostrarBotones(View... botones) {
        for(View boton : botones) {
            boton.setVisibility(View.INVISIBLE);
        }
    }

    private void deshabilitarBotones(View... botones) {
        for(View boton : botones) {
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
