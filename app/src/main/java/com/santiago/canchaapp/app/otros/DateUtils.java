package com.santiago.canchaapp.app.otros;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.util.Calendar.DATE;

public class DateUtils {

    private static Locale IDIOMA_ESP = new Locale("es", "ES");

    private static DateFormat FORMATO_SEMANA = new SimpleDateFormat("EEEE", IDIOMA_ESP);

    private static DateFormat FORMATO_DIA = new SimpleDateFormat("dd/MM", IDIOMA_ESP);

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

    private static Calendar calendario() {
        return Calendar.getInstance();
    }

}
