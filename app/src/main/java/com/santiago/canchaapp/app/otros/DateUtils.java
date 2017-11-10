package com.santiago.canchaapp.app.otros;

import com.santiago.canchaapp.dominio.Horario;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.util.Calendar.DATE;

public class DateUtils {

    private static Locale IDIOMA_ESP = new Locale("es", "ES");

    private static DateFormat FORMATO_SEMANA = new SimpleDateFormat("EEEE", IDIOMA_ESP);

    private static DateFormat FORMATO_DIA = new SimpleDateFormat("dd/MM", IDIOMA_ESP);

    private static DateFormat FORMATO_DIA_COMPLETO = new SimpleDateFormat("dd-MM-yyyy", IDIOMA_ESP);

    private static DateFormat FORMATO_DIA_COMPLETO_PARA_GUARDAR = new SimpleDateFormat("yyyy-MM-dd", IDIOMA_ESP);

    public static Date hoy() {
        return calendario().getTime();
    }

    public static Date hoyMasDias(int dias) {
        Calendar calendario = calendario();
        calendario.add(DATE, dias);
        return calendario.getTime();
    }

    public static String textoDia(Date dia) {
        return FORMATO_SEMANA.format(dia) + " " + FORMATO_DIA.format(dia);
    }

    public static String dateToString(Date dia) {
        return FORMATO_DIA_COMPLETO.format(dia);
    }

    public static String dateToStringtoSave(Date dia) {
        return FORMATO_DIA_COMPLETO_PARA_GUARDAR.format(dia);
    }

    private static Calendar calendario() {
        return Calendar.getInstance();
    }

    public static String textoHorario(Horario horario) {
        int desde = horario.getDesde();
        return ((desde < 10) ? "0" : "") + desde + ":00";
    }

    public static Date timestampToDate(Long timestamp) {
        Calendar cal = calendario();
        cal.setTimeInMillis(timestamp);
        return cal.getTime();
    }

    public static Long dateToTimestamp(Date date) {
        return date.getTime();
    }

    public static Date stringToDateToSave(String fecha) {
        try {
            return FORMATO_DIA_COMPLETO_PARA_GUARDAR.parse(fecha);
        } catch (ParseException e) {
            throw new RuntimeException("La fecha está mal cargada");
        }
    }

    public static Date stringToDate(String fecha) {
        try {
            return FORMATO_DIA_COMPLETO.parse(fecha);
        } catch (ParseException e) {
            throw new RuntimeException("La fecha está mal cargada");
        }
    }

    public static int hora(Date fecha) {
        Calendar cal = calendario();
        cal.setTime(fecha);
        return cal.get(Calendar.HOUR_OF_DAY);
    }

}
