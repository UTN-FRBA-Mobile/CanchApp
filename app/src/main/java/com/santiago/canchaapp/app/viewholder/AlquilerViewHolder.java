package com.santiago.canchaapp.app.viewholder;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.otros.AccionesSobreReserva;
import com.santiago.canchaapp.app.otros.DateUtils;
import com.santiago.canchaapp.dominio.Alquiler;
import com.santiago.canchaapp.dominio.DataBase;
import com.santiago.canchaapp.dominio.EstadoReserva;
import com.santiago.canchaapp.dominio.Horario;
import com.santiago.canchaapp.dominio.Reserva;
import com.santiago.canchaapp.servicios.Preferencias;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.graphics.Typeface.*;
import static android.text.Spanned.*;
import static android.view.View.VISIBLE;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.santiago.canchaapp.app.otros.DateUtils.*;
import static com.santiago.canchaapp.app.otros.DateUtils.stringToDateToSave;
import static com.santiago.canchaapp.app.otros.TextUtils.estaVacio;
import static com.santiago.canchaapp.dominio.EstadoReserva.APROBADA;
import static com.santiago.canchaapp.dominio.EstadoReserva.CANCELADA;
import static com.santiago.canchaapp.dominio.Horario.*;

public class AlquilerViewHolder extends RecyclerView.ViewHolder {

    private final Activity activity;
    private final Context context;

    @BindView(R.id.alquiler_cancha)
    public TextView textoClub;
    @BindView(R.id.alquiler_nombreUsuario)
    public TextView textoNombreUsuario;
    @BindView(R.id.alquiler_hora)
    public TextView textoHora;
    @BindView(R.id.alquiler_motivo_cancelacion)
    public TextView textMotivoCancelacion;
    @BindView(R.id.boton_aprobar_alquiler)
    public ImageView botonAprobar;
    @BindView(R.id.boton_cancelar_alquiler)
    public ImageView botonCancelar;
    @BindView(R.id.texto_alquiler)
    public LinearLayout textoAlquiler;

    public AlquilerViewHolder(View v, Activity activity) {
        super(v);
        ButterKnife.bind(this, v);
        this.activity = activity;
        this.context = activity.getApplicationContext();
    }

    public void cargarDatosEnVista(Alquiler alquiler, AccionesSobreReserva acciones) {
        // Setear textos
        textoClub.setText(alquiler.getNombreCancha() + " (" + alquiler.getTipoCancha().nombre + ")");
        textoHora.setText(textoDia(stringToDate(alquiler.getFecha())) + ", " + horaDesde(alquiler.getHora()));
        textoNombreUsuario.setText(textoUsuario(alquiler));
        // Setear botones
        switch (acciones) {
            case SOLO_CANCELAR:
                mostrarBotones(botonCancelar);
                setearListenerCancelacion(alquiler);
                break;
            case TODAS: mostrarBotones(botonAprobar, botonCancelar);
                setearListenerCancelacion(alquiler);
                setearListenerAprobacion(alquiler);
                break;
        }
    }

    private SpannableStringBuilder textoUsuario(Alquiler alquiler) {
        SpannableString nombre = new SpannableString(alquiler.getNombreUsuario());
        nombre.setSpan(new StyleSpan(BOLD), 0, nombre.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableStringBuilder builder = new SpannableStringBuilder()
                .append((alquiler.esUsuarioRegistrado() ? "Por " : "Para "))
                .append(nombre);
        return builder;
    }

    private void mostrarBotones(ImageView... botones) {
        for(ImageView boton : botones) {
            boton.setVisibility(VISIBLE);
        }
        textoAlquiler.setLayoutParams(new LayoutParams(0, WRAP_CONTENT, 1));
    }

    private void setearListenerCancelacion(final Alquiler alquiler) {
        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accion(alquiler, CANCELADA);
            }
        });
    }

    private void setearListenerAprobacion(final Alquiler alquiler) {
        botonAprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accion(alquiler, APROBADA);
            }
        });
    }

    private void accion(Alquiler alquiler, final EstadoReserva nuevoEstado) {
        if(DataBase.getInstancia().isOnline(context)) {
            if (Preferencias.getInstancia().confirmacionHabilitada(activity)) {
                confirmarAccion(activity, alquiler, nuevoEstado);
            } else {
                actualizarAlquiler(alquiler, nuevoEstado);
            }
        } else {
            showToast(R.string.txtSinConexion);
        }
    }

    private void showToast(int idTxt) {
        Toast.makeText(context, idTxt, Toast.LENGTH_SHORT).show();
    }

    private void actualizarAlquiler(final Alquiler alquiler, final EstadoReserva nuevoEstado) {
        DataBase.getInstancia().updateEstadoReserva(alquiler.getIdUsuario(), alquiler.getIdReserva(), nuevoEstado);
        DataBase.getInstancia().updateEstadoAlquiler(alquiler.getIdClub(), alquiler.getIdCancha(), stringToDateToSave(alquiler.getFecha()), alquiler.getUuid(), nuevoEstado);
        DataBase.getInstancia().updateEstadoAlquilerPorClub(alquiler.getIdClub(), alquiler.getUuid(), nuevoEstado);
    }

    private void confirmarAccion(final Activity activity, final Alquiler alquiler, final EstadoReserva nuevoEstado) {
        final View viewDialogo = activity.getLayoutInflater().inflate(R.layout.dialogo_confirmar_reserva, null);
        // Set diálogo
        final AlertDialog dialog = new AlertDialog.Builder(activity)
                .setTitle("Confirmar acción")
                .setMessage("¿Estás seguro que querés " +
                        (nuevoEstado == APROBADA ? "aprobar" : "cancelar") + " este alquiler?" )
                .setView(viewDialogo)
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        actualizarAlquiler(alquiler, nuevoEstado);
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

}
