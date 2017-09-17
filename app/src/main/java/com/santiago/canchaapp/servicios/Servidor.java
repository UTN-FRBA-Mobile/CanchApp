package com.santiago.canchaapp.servicios;

import com.santiago.canchaapp.dominio.Club;
import com.santiago.canchaapp.dominio.Reserva;

import java.util.List;

import static com.santiago.canchaapp.dominio.TipoPartido.FUTBOL5;
import static com.santiago.canchaapp.dominio.TipoPartido.FUTBOL7;
import static com.santiago.canchaapp.dominio.TipoPartido.TENIS;
import static java.util.Arrays.asList;

public class Servidor {

    private static Servidor servidor;

    public static Servidor instancia() {
        if (servidor == null) {
            servidor = new Servidor();
        }
        return servidor;
    }

    private List<Reserva> reservasPendientes = asList(
            new Reserva(new Club("Da Vinci", "Buenos Aires 1234"), FUTBOL5, "16/09/17", 21),
            new Reserva(new Club("Los Troncos", "Italia 2580"), TENIS, "06/10/17", 17),
            new Reserva(new Club("Los Troncos", "Italia 2580"), TENIS, "06/10/17", 18)
    );

    private List<Reserva> reservasAprobadas = asList(
                    new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "27/09/17", 16),
                    new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "01/10/17", 16),
                    new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "02/10/17", 19),
                    new Reserva(new Club("Los Troncos", "Italia 2580"), TENIS, "03/10/17", 15),
                    new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "03/10/17", 21),
                    new Reserva(new Club("Club Mitre", "Avellaneda 4433"), FUTBOL7, "04/10/17", 11),
                    new Reserva(new Club("Los Troncos", "Italia 2580"), TENIS, "04/10/17", 16),
                    new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "04/10/17", 22),
                    new Reserva(new Club("Club Mitre", "Avellaneda 4433"), FUTBOL7, "30/10/17", 14),
                    new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "1/11/17", 22)
            );

    private List<Reserva> reservasCanceladas = asList(
            new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "29/09/17", 20)
    );

    private List<Reserva> alquileresAprobados = asList(
                new Reserva(new Club("Da Vinci", "Buenos Aires 1234"), FUTBOL5, "16/09/17", 21),
                new Reserva(new Club("Los Troncos", "Italia 2580"), TENIS, "06/10/17", 17),
                new Reserva(new Club("Los Troncos", "Italia 2580"), TENIS, "06/10/17", 18)
        );

    private List<Reserva> alquileresPendientes  = asList(
                new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "27/09/17", 16),
                new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "01/10/17", 16),
                new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "02/10/17", 19),
                new Reserva(new Club("Los Troncos", "Italia 2580"), TENIS, "03/10/17", 15),
                new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "03/10/17", 21),
                new Reserva(new Club("Club Mitre", "Avellaneda 4433"), FUTBOL7, "04/10/17", 11),
                new Reserva(new Club("Los Troncos", "Italia 2580"), TENIS, "04/10/17", 16),
                new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "04/10/17", 22),
                new Reserva(new Club("Club Mitre", "Avellaneda 4433"), FUTBOL7, "30/10/17", 14),
                new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "1/11/17", 22)
        );

    private List<Reserva> alquileresCancelados = asList(
                new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "29/09/17", 20),
                new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL7, "29/09/19", 22)
        );

    public List<Reserva> getReservasPendientes() {
        return reservasPendientes;
    }

    public List<Reserva> getReservasAprobadas() {
        return reservasAprobadas;
    }

    public List<Reserva> getReservasCanceladas() {
        return reservasCanceladas;
    }

    public List<Reserva> getAlquileresAprobados() {
        return alquileresAprobados;
    }

    public List<Reserva> getAlquileresPendientes() {
        return alquileresPendientes;
    }

    public List<Reserva> getAlquileresCancelados() {
        return alquileresCancelados;
    }
}
